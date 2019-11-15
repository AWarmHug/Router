package com.bingo.router.processor;

import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;
import com.bingo.router.annotations.model.Utils;
import com.bingo.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.bingo.router.annotations.Route"})
@SupportedOptions({"moduleName"})
public class RouteApiProcessor extends BaseProcessor {
    private TypeMirror TYPE_REQUEST;
    private TypeMirror TYPE_IROUTE;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routeApis = roundEnvironment.getElementsAnnotatedWith(Route.class);
        List<ExecutableElement> elements = new ArrayList<>();
        for (Element element : routeApis) {
            if (element instanceof ExecutableElement && element.getKind() == ElementKind.METHOD) {
                System.out.println(element);
                elements.add((ExecutableElement) element);
            }
        }

        Map<TypeElement, List<MethodSpec>> map = new HashMap<>();

        for (ExecutableElement element : elements) {
            List<ParameterSpec> parameterSpecs = new ArrayList<>();
            for (VariableElement variableElement : element.getParameters()) {
                parameterSpecs.add(ParameterSpec.get(variableElement));
            }

            MethodSpec.Builder builder = MethodSpec.methodBuilder(element.getSimpleName().toString())
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.get(element.getReturnType()))
                    .addParameters(parameterSpecs);

            /**
             * Router.newRequest("test/detail")
             *                         .putInt("type", 1)
             *                         .build()
             *                         .start(MainActivity.this);
             */
            Route route = element.getAnnotation(Route.class);

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
                    TypeElement typeElement = mElementUtils.getTypeElement(valueClass);
                    path = Utils.pathByPathClass(typeElement.getAnnotation(PathClass.class));
                }
            }
            builder.addCode("return $T.newRequest($S)\n", TypeName.get(mElementUtils.getTypeElement("com.bingo.router.Router").asType()), path);
            for (VariableElement variableElement : element.getParameters()) {

                Parameter parameter = variableElement.getAnnotation(Parameter.class);
                String name;
                if (parameter != null && !parameter.value().isEmpty()) {
                    name = parameter.value();
                } else {
                    name = variableElement.getSimpleName().toString();
                }

                if (mTypes.isSameType(variableElement.asType(), mElementUtils.getTypeElement(String.class.getName()).asType())) {
                    builder.addCode(".putString($S,$L)\n", name, name);
                } else if (variableElement.asType().getKind().isPrimitive()) {
                    switch (variableElement.asType().getKind()) {
                        case INT:
                            builder.addCode(".putInt($S,$L)\n", name, variableElement.getSimpleName());
                            break;
                        case BYTE:
                            builder.addCode(".putByte($S,$L)\n", name, variableElement.getSimpleName());
                            break;
                        case CHAR:
                            builder.addCode(".putChar($S,$L)\n", name, variableElement.getSimpleName());
                            break;
                        case LONG:
                            builder.addCode(".putLong($S,$L)\n", name, variableElement.getSimpleName());
                            break;
                    }
                }
                TYPE_REQUEST = mElementUtils.getTypeElement("com.bingo.router.Request").asType();
                TYPE_IROUTE = mElementUtils.getTypeElement("com.bingo.router.IRoute").asType();


                TypeMirror returnType = element.getReturnType();
                if (mTypes.isSameType(returnType, TYPE_REQUEST)) {
                    builder.addStatement("");
                } else if (mTypes.isSameType(returnType, TYPE_IROUTE)) {
                    builder.addStatement(".build()");
                } else if (returnType.getKind() == TypeKind.VOID) {

                }
            }


            TypeElement tElement = (TypeElement) element.getEnclosingElement();
            List<MethodSpec> methodSpecs = map.get(tElement);
            if (methodSpecs == null) {
                methodSpecs = new ArrayList<>();
                map.put(tElement, methodSpecs);
            }
            methodSpecs.add(builder.build());
        }

        map.forEach(new BiConsumer<TypeElement, List<MethodSpec>>() {
            @Override
            public void accept(TypeElement typeElement, List<MethodSpec> methodSpecs) {

                String pkgName = mElementUtils.getPackageOf(typeElement).toString();
                String typeName = typeElement.getQualifiedName().toString().replace(pkgName + ".", "").replace(".", "$") + "Impl";


                TypeSpec typeSpec = TypeSpec.classBuilder(typeName)
                        .addSuperinterface(TypeName.get(typeElement.asType()))
                        .addModifiers(Modifier.PUBLIC)
                        .addMethods(methodSpecs)
                        .build();
                JavaFile javaFile = JavaFile.builder(pkgName, typeSpec).build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return false;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
