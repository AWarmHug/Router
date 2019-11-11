package com.bingo.router.annotations.model;

import com.bingo.router.annotations.PathClass;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String pathByPathClass(Class<?> pathClass) {
        return pathByPathClass(pathClass.getAnnotation(PathClass.class));
    }

    public static String pathByPathClass(PathClass pathClass){
        return pathClass.value();
    }

}
