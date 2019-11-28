package com.bingo.demo.databinding.bindingMethods;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

@InverseBindingMethods({@InverseBindingMethod(type = DisplayProgressBar.class,attribute = "dis",event = "disAttrChanged",method = "isDis")})
public class DisplayViewBindingAdapter {


//    @BindingAdapter(value = {"display"}, requireAll = false)
//    public static void setDisplay(DisplayProgressBar view, boolean display) {
//        if (getDisplay(view) == display) {
//            return;
//        } else {
//            view.setVisibility(display ? View.VISIBLE : View.GONE);
//        }
//    }
//
//    @InverseBindingAdapter(attribute = "display", event = "displayAttrChanged")
//    public static boolean getDisplay(DisplayProgressBar view) {
//        return view.getVisibility() == View.VISIBLE;
//    }
//
//    @BindingAdapter(value = {"displayAttrChanged"}, requireAll = false)
//    public static void setDisplayAttrChanged(DisplayProgressBar view, InverseBindingListener changeListener) {
//        if (changeListener == null) {
//            view.setOnViewChangeListener(null);
//        } else {
//            view.setOnViewChangeListener(new DisplayProgressBar.OnViewChangeListener() {
//                @Override
//                public void onChange() {
//                    changeListener.onChange();
//                }
//            });
//        }
//
//
//    }


}
