package com.bingo.demo.meizi;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bingo.demo.model.Gank;
import com.bingo.demo.model.Result;
import com.bingo.demo.network.MeiziService;
import com.bingo.demo.network.RetrofitHelper;

import java.util.List;
import java.util.Random;

public class MeiziActivityViewModel extends ViewModel {

    public ObservableList<Result> list = new ObservableArrayList<>();

    public ObservableBoolean showLoad=new ObservableBoolean();

    public ObservableField<Result> result = new ObservableField<>();

    public void loadMeizi(int pageNum, LifecycleOwner owner) {
        showLoad.set(true);
        RetrofitHelper.provideApi(MeiziService.class).loadMeizi("福利", 10, pageNum)
                .observe(owner, new Observer<Gank<List<Result>>>() {
                    @Override
                    public void onChanged(Gank<List<Result>> listGank) {
                        if (!listGank.isError()) {
                            result.set(listGank.getResults().get(new Random().nextInt(10)));
//                            list.addAll(listGank.getResults());
                        }
                    }
                });
    }

}
