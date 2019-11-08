package com.bingo.router.annotations.model;

import com.bingo.router.annotations.PathClass;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String pathByPathClass(Class<?> pathClass) {
        String packageName = pathClass.getPackage().getName();
        String simpleName = pathClass.getName().replace(packageName + ".", "");
        String[] classSimpleNames = simpleName.split("\\$");

        List<String> classNames = new ArrayList<>();

        for (int i = 0; i < classSimpleNames.length; i++) {
            if (i == 0) {
                classNames.add(packageName + "." + classSimpleNames[i]);
            } else {
                classNames.add(classNames.get(i - 1) + "$" + classSimpleNames[i]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String p : classNames) {
            try {
                PathClass pathClass1 = Class.forName(p).getAnnotation(PathClass.class);
                if (pathClass1 != null) {
                    sb.append(pathClass1.value());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
