package com.bingo.demo.network;

import androidx.lifecycle.LiveData;

import com.bingo.demo.model.Result;

public interface MeiziService {

//    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
    LiveData<Result> loadMeizi();

}
