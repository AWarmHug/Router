package com.bingo.demo.databinding.notifyPropertyChanged;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.bingo.demo.R;
import com.bingo.libpublic.ContextProvider;

public class ViewModel1 extends ViewModel {

    public ObservableField<Drawable> placeHolder = new ObservableField<>();

    {
        placeHolder.set(ContextCompat.getDrawable(ContextProvider.autoContext, R.drawable.ic_launcher_background));
    }


}
