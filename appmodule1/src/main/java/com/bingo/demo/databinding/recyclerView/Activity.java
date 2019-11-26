package com.bingo.demo.databinding.recyclerView;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.DataBinding;
import com.bingo.demo.databinding.ActivityRecyclerviewBinding;
import com.bingo.router.annotations.Route;

import java.util.List;

@Route(pathClass = DataBinding.RecyclerView.class)
public class Activity extends AppCompatActivity {
    private ActivityRecyclerviewBinding mBinding;
    private ViewModel1 viewModel1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview);
        viewModel1 = ViewModelProviders.of(this).get(ViewModel1.class);
        mBinding.setVm(viewModel1);
        Adapter adapter = new Adapter();
        mBinding.list.setAdapter(adapter);
        mBinding.list.setLayoutManager(new LinearLayoutManager(this));

        viewModel1.loadPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                adapter.personList=people;
                adapter.notifyDataSetChanged();
            }
        });
        viewModel1.personLiveData.observe(this, new Observer<Person>() {
            @Override
            public void onChanged(Person person) {
                adapter.personList.add(person);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
