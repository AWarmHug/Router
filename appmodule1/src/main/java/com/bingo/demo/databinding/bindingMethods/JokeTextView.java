package com.bingo.demo.databinding.bindingMethods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(type = TextView.class, attribute = "showText", method = "showText")})
public class JokeTextView extends TextView {
    public JokeTextView(Context context) {
        super(context);
    }

    public JokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void showText(String text) {
        setText(text);
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
