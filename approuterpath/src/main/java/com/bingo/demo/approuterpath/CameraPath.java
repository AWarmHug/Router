package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

public interface CameraPath {

    @PathClass("/camera/camera1")
    public interface Camera1Path {

    }

    @PathClass("/camera/camera2")
    public interface Camera2Path {

    }

}
