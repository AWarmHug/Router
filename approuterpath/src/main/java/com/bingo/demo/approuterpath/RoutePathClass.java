package com.bingo.demo.approuterpath;

import com.bingo.router.Request;
import com.bingo.router.Router;

import java.io.Serializable;

public class RoutePathClass {
    public static final String KEY_NAME = "name";


    public <T extends Serializable> Request createRequest(T t) {
        return Router.newRequest(this.getClass()).putSerializable(KEY_NAME, t);
    }

}
