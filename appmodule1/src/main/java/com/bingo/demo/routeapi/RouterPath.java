package com.bingo.demo.routeapi;

import com.bingo.router.annotations.PathClass;

public class RouterPath {

    @PathClass("login")
    public class Login {
        @PathClass("/logina")
        public class Logina {

        }
        @PathClass("/reg")
        public class Reg {

        }
    }

}
