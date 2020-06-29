package com.bingo.router.internal.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatcherCenter {
    public static final List<Matcher> sMatcher = new ArrayList<>();

    static {
        sMatcher.add(new BrowserMatcher());
        sMatcher.add(new ImplicitMatcher());
        sMatcher.add(new ClassMatcher());
    }


    public static void register(Matcher matcher) {
        sMatcher.add(matcher);
    }

}
