package com.bingo.demo.network;

import androidx.lifecycle.LiveData;

import com.bingo.demo.model.Gank;
import com.bingo.demo.model.Result;
import com.bingo.libpublic.adapter_livedata.RLiveData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MeiziService {

    @GET("data/{type}/{pageSize}/{pageNum}")
    RLiveData<Gank<List<Result>>> loadMeizi(@Path("type") String type, @Path("pageSize") int pageSize, @Path("pageNum") int pageNum);

}
