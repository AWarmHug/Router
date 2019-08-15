package com.warm

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.io.Files
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.JarClassPath
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader

import java.util.jar.JarFile
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class RTransform extends Transform {

    String routerJarPath;

    Set<String> routerLoaders = new HashSet<>();
    Set<String> binderLoaders = new HashSet<>();

    Project project

    RTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "RTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println "Track-Plugin-----transform开始------"
        def outputProvider = transformInvocation.outputProvider

        def inputs = transformInvocation.inputs

        outputProvider.deleteAll()

        ClassPool pool = ClassPool.getDefault()
        pool.appendClassPath(project.android.bootClasspath[0].toString())

        Map<String, String> dirMap = new HashMap<>();
        Map<String, String> jarMap = new HashMap<>();

        inputs.each { TransformInput input ->
            input.directoryInputs.each {
                pool.appendClassPath(it.file.absolutePath)
                println(it.file.absolutePath)

                // 获取output目录
                File dest = outputProvider.getContentLocation(it.name,
                        it.getContentTypes(), it.getScopes(),
                        Format.DIRECTORY)

                FileUtils.copyDirectory(it.file, dest)

                dirMap.put(it.file.absolutePath, dest.absolutePath)


            }

            input.jarInputs.each {
                def classPath = new JarClassPath(it.file.absolutePath)
                pool.appendClassPath(classPath)


                println(it.file.absolutePath)


                // 重命名输出文件
                String jarName = it.name
                String md5Name = DigestUtils.md5Hex(it.file.absolutePath);
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4);
                }
                //生成输出路径
                File dest = outputProvider.getContentLocation(jarName + md5Name,
                        it.getContentTypes(), it.getScopes(), Format.JAR);
                if (it.file.isFile()) {
                    FileUtils.copyFile(it.file, dest)
                }

                jarMap.put(it.file.absolutePath, dest.absolutePath)

            }

        }


        dirMap.each {
            File dir = new File(it.key)
            if (dir.isDirectory()) {
                dir.eachFileRecurse {
                    String filePath = it.absolutePath
                    if (it.isFile() && filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('R.class') && !filePath.contains('BuildConfig.class')) {
                        ClassReader reader = new ClassReader(new FileInputStream(it))
                        CtClass ctClass = pool.get(Utils.getClassName(reader.className))
                        if (ctClass.name.startsWith(Config.LOADER_PKG) && ctClass.name.endsWith(Config.ROUTER_LOADER_CLASS_NAME) && ctClass.interfaces[0].name == "com.warm.router.annotations.model.Loader") {
                            println(ctClass.name)
                            routerLoaders.add(ctClass.name)
                        }

                        if (ctClass.name.startsWith(Config.LOADER_PKG) && ctClass.name.endsWith(Config.BINDER_LOADER_CLASS_NAME) && ctClass.interfaces[0].name == "com.warm.router.annotations.model.Loader") {
                            println(ctClass.name)
                            binderLoaders.add(ctClass.name)
                        }
                    }
                }
            }
        }

        jarMap.each {

            def inJarFile = new JarFile(it.key)

            def enumeration = inJarFile.entries();

            while (enumeration.hasMoreElements()) {
                def jarEntry = enumeration.nextElement()
                if (jarEntry == null) {
                    continue
                }
                def entryName = jarEntry.name
                if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                    ClassReader reader = new ClassReader(inJarFile.getInputStream(jarEntry))
                    CtClass ctClass = pool.get(Utils.getClassName(reader.className))

                    if (ctClass.name == "com.warm.router.Router") {
                        routerJarPath = it.key
                        continue
                    }

                    if (ctClass.name.startsWith(Config.LOADER_PKG) && ctClass.name.endsWith(Config.ROUTER_LOADER_CLASS_NAME) && ctClass.interfaces[0].name == "com.warm.router.annotations.model.Loader") {
                        println(ctClass.name)
                        routerLoaders.add(ctClass.name)
                    }

                    if (ctClass.name.startsWith(Config.LOADER_PKG) && ctClass.name.endsWith(Config.BINDER_LOADER_CLASS_NAME) && ctClass.interfaces[0].name == "com.warm.router.annotations.model.Loader") {
                        println(ctClass.name)
                        binderLoaders.add(ctClass.name)
                    }


                }
            }
        }

        inject(pool, jarMap)
    }

    /**
     * 往Router类中注入初始化代码
     * static{*     new BinderLoader().load(mMap);
     *}*/
    void inject(ClassPool pool, Map<String, String> jarMap) {

        String dest = jarMap.get(routerJarPath)

        Files.createParentDirs(new File(dest))

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest));

        def inJarFile = new JarFile(routerJarPath)

        def enumeration = inJarFile.entries();

        while (enumeration.hasMoreElements()) {
            def jarEntry = enumeration.nextElement()
            if (jarEntry == null) {
                continue
            }
            def entryName = jarEntry.name

            zos.putNextEntry(new ZipEntry(entryName))

            if (!jarEntry.isDirectory()) {

                ClassReader reader = new ClassReader(inJarFile.getInputStream(jarEntry))
                CtClass ctClass = pool.get(Utils.getClassName(reader.className))

                if (ctClass.name == "com.warm.router.Router") {

                    CtMethod ctMethod = ctClass.getDeclaredMethod("init")
                    StringBuilder sb = new StringBuilder();
                    sb.append("{")
                    routerLoaders.each {
                        sb.append("new ${it}().load(mRouteInfoMap);")
                    }

                    binderLoaders.each {
                        sb.append("new ${it}().load(mBinderInfoMap);")
                    }
                    sb.append("}")

                    ctMethod.setBody(sb.toString())
                    IOUtils.write(ctClass.toBytecode(), zos)
                    ctClass.detach()
                } else {
                    IOUtils.copy(inJarFile.getInputStream(jarEntry), zos)
                }

            } else {
                IOUtils.copy(inJarFile.getInputStream(jarEntry), zos)
            }
        }
        zos.close()
    }
}