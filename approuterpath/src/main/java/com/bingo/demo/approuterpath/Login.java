package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

@PathClass("login")
public interface Login {
    @PathClass("login/logina")
    public interface Logina {

    }

    @PathClass("login/reg")
    public interface Reg {

    }
}
