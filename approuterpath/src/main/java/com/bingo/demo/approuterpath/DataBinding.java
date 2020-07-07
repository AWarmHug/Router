package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

public interface DataBinding {
    @PathClass("/databinding/notifyPropertyChanged")
    public interface NotifyPropertyChanged {

    }

    @PathClass("/databinding/recyclerView")
    public interface RecyclerView {

    }

    @PathClass("/databinding/bindingMethods")
    public interface BindingMethods {

    }

}
