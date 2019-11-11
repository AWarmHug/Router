package com.bingo.demo.routerpath;

import com.bingo.router.annotations.PathClass;

public class RouterPath {

    @PathClass("/login")
    public class Login {
        @PathClass("login/logina")
        public class Logina {

        }

        @PathClass("login/reg")
        public class Reg {

        }
    }

    @PathClass("apphybrid")
    public class PathHybrid {
        @PathClass("apphybrid/web")
        public class Web{

        }
    }

}
