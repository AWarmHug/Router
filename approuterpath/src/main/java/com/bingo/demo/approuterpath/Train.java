package com.bingo.demo.approuterpath;

import com.bingo.router.Request;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

public interface Train {

    @Route("train/home")
    Request openHome(@Parameter HomeParams params,@Parameter("userId") String id);

    @Route("train/orderDetail")
    Request getOrderDetail(@Parameter String orderId);

}
