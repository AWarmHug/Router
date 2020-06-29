package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

@PathClass("camera")
public class CameraPath {

    @PathClass("camera/camera2")
    public class Camera2Path {

    }

}
