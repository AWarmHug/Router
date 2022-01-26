package com.bingo.router;

import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;

public class Utils {

    public static String pathByPathClass(Class<?> pathClass) {
        return pathByPathClass(pathClass.getAnnotation(PathClass.class));
    }

    public static String pathByPathClass(PathClass pathClass) {
        return pathClass.value();
    }

    public static String getPath(Route route) {
        String path = route.value();
        if (path.isEmpty()) {
            path = Utils.pathByPathClass(route.pathClass());
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
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

}
