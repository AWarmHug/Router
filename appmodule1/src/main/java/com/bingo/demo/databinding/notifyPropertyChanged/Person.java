package com.bingo.demo.databinding.notifyPropertyChanged;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class Person extends BaseObservable {
    private String name;
    private long id;
    private String img;

    public Person(String name, long id, String img) {
        this.name = name;
        this.id = id;
        this.img = img;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Bindable
    public String getImg() {
        return img;
    }

    public void addId1() {
        id += 1;
        name = name + id;
        notifyPropertyChanged(com.bingo.demo.BR.name);
    }

    public void setImg(String img) {
        this.img = img;
        notifyPropertyChanged(com.bingo.demo.BR.img);
    }
}
