package com.bingo.router.processor;


import com.bingo.router.Const;
import com.bingo.router.Loader;
import com.bingo.router.RouteInfo;
import com.bingo.router.Utils;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;
import com.bingo.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.bingo.router.annotations.Route", "com.bingo.router.annotations.Parameter"})
@SupportedOptions({"moduleName"})
public class RouteProcessor extends BaseProcessor {

    public static final String ASSET_PATH = "router.doc";

//    private Writer mDocWriter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
//        try {
//            FileObject fileObject = mFiler.createResource(StandardLocation.SOURCE_OUTPUT, ASSET_PATH, getModuleName() + ".json");
//            mDocWriter = fileObject.openWriter();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<String, List<TypeElement>> routesMap = new HashMap<>();
        Set<? extends Element> routes = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element : routes) {
            if (element instanceof TypeElement && element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;

                mMessager.printMessage(Diagnostic.Kind.WARNING, "in building::::::"+typeElement.getQualifiedName());

                Route route = typeElement.getAnnotation(Route.class);
                String path = getPath(route);
                if (countStr(path, '/') < 2) {
                    continue;
                }
                String group = path.substring(1).split("/")[0];
                if (!routesMap.containsKey(group)) {
                    List<TypeElement> elements = new LinkedList<>();
                    routesMap.put(group, elements);
                }
                routesMap.get(group).add(typeElement);

                mMessager.printMessage(Diagnostic.Kind.WARNING, "in building::::::group===="+group);
                mMessager.printMessage(Diagnostic.Kind.WARNING, "in building::::::path===="+path);

            }
        }
        Map<TypeElement, String> autowiredMap = new HashMap<>();
        Set<? extends Element> parameter = roundEnvironment.getElementsAnnotatedWith(Parameter.class);
        for (Element element : parameter) {
            if (element instanceof VariableElement && element.getEnclosingElement().getKind() == ElementKind.CLASS && element.getKind() == ElementKind.FIELD) {
                VariableElement vElement = (VariableElement) element;
                //这是获取owner 可能是fragment也可能是activity，或者其他
                TypeElement tElement = (TypeElement) vElement.getEnclosingElement();
                PackageElement pElement = (PackageElement) tElement.getEnclosingElement();
                if (autowiredMap.get(tElement) == null) {
                    String className = pElement.getQualifiedName().toString() + "." + tElement.getSimpleName() + Const.BINDER_CLASS_NAME;
                    autowiredMap.put(tElement, className);
                }
            }
        }

        createGroup(routesMap, autowiredMap);

        return false;
    }

    private String getPath(Route route) {
        String path = route.value();
        if (path.isEmpty()) {
            try {
                //查看相关内容
                //https://www.cnblogs.com/fuckingaway/p/6703021.html
                //https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
                route.pathClass();
            } catch (MirroredTypeException mte) {
                TypeMirror value = mte.getTypeMirror();
                String valueClass = value.toString();
                TypeElement typeE = mElementUtils.getTypeElement(valueClass);
                path = Utils.pathByPathClass(typeE.getAnnotation(PathClass.class));
            }
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }

    private void createGroup(Map<String, List<TypeElement>> routesMap, Map<TypeElement, String> autowiredMap) {
        if (routesMap.isEmpty()) {
            return;
        }
        String pkgName = Const.LOADER_PKG + Const.DOT + getModuleName();
        ParameterizedTypeName name = ParameterizedTypeName.get(Loader.class, String.class, RouteInfo.class);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(ClassName.get(Map.class), TypeName.get(String.class), name), "loaders");

        int pos = 0;
        for (String key : routesMap.keySet()) {
            String className = upperFirstLatter(key) + Const.ROUTER_LOADER_CLASS_NAME;
            createRoute(pkgName, className, routesMap.get(key), autowiredMap);
            builder.addStatement("$T loader$L =new $T()", ClassName.get(pkgName, className), pos, ClassName.get(pkgName, className));
            builder.addStatement("loaders.put($S,loader$L)", key, pos);
            pos++;
        }

        TypeSpec typeSpec = TypeSpec.classBuilder(Const.GROUP_LOADER_CLASS_NAME)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Loader.class), TypeName.get(String.class), name))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(pkgName, typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createRoute(String pkgName, String className, List<TypeElement> list, Map<TypeElement, String> autowiredMap) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, RouteInfo.class), "routers");


        for (int i = 0; i < list.size(); i++) {
            TypeElement typeElement = list.get(i);

            mMessager.printMessage(Diagnostic.Kind.WARNING, typeElement.getQualifiedName());

            Route route = typeElement.getAnnotation(Route.class);
            String path = getPath(route);

            int type = 0;
            if (isActivity(typeElement)) {
                type = RouteInfo.TYPE_ACTIVITY;
            } else if (isBroadcastReceiver(typeElement)) {
                type = RouteInfo.TYPE_BROADCAST_RECEIVER;
            } else if (isFragment(typeElement)) {
                type = RouteInfo.TYPE_FRAGMENT;
            }

            builder.addStatement("$T route$L =new $T(" + type + ",$S,$T.class)", TypeName.get(RouteInfo.class), i, TypeName.get(RouteInfo.class), path, ClassName.get(typeElement));
            if (autowiredMap.get(typeElement) != null) {
                String clazz = autowiredMap.get(typeElement);
                int lastIndex = clazz.lastIndexOf(".");
                String pn = clazz.substring(0, lastIndex);
                String cn = clazz.substring(lastIndex + 1);
                builder.addStatement("route$L.setAutowiredBinder(new $T())", i, ClassName.get(pn, cn));
            }

            if (route.interceptors().length != 0) {
                StringBuilder interceptorKeys = new StringBuilder("new String[]{");
                for (int j = 0; j < route.interceptors().length; j++) {
                    interceptorKeys.append("\"" + route.interceptors()[j] + "\"");
                    if (j != route.interceptors().length - 1) {
                        interceptorKeys.append(",");
                    }
                }
                interceptorKeys.append("}");

                builder.addStatement("route$L.setInterceptorKeys($L)", i, interceptorKeys.toString());
            }

            builder.addStatement("routers.put($S,route$L)", path, i);
        }

        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addSuperinterface(ParameterizedTypeName.get(Loader.class, String.class, RouteInfo.class))
                .addMethod(builder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(pkgName, typeSpec).build();

        try {

            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int countStr(String str1, char str2) {
        int counter = 0;
        char[] cs = str1.toCharArray();
        for (char c : cs) {
            if (c == str2) {
                counter++;
            }
        }

        return counter;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * @param routeClass
     * @throws Exception
     */
//    private void writeFile(Map<String, List<TypeElement>> routeClass) throws Exception {
////        typeElement.getQualifiedName().toString();
//
//        String content = new Gson().toJson(routeClass);
//        mDocWriter.write(content);
//        mDocWriter.close();
//    }

}
