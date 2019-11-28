package com.bingo.demo.meizi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bingo.demo.model.Gank;
import com.bingo.demo.model.Result;
import com.bingo.demo.network.MeiziService;
import com.bingo.demo.network.RetrofitHelper;
import com.bingo.libpublic.adapter_livedata.RLiveData;

import java.util.List;

public class MeiziActivityViewModel extends ViewModel {


    public RLiveData<Gank<List<Result>>> loadMeizi(int pageNum) {
        return RetrofitHelper.provideApi(MeiziService.class).loadMeizi("福利", 10, pageNum);
    }

}
