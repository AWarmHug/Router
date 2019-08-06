package com.warm.router.demo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Leader a = new Director("张三");
//        Leader b = new Manager("李四");
//        Leader b2 = new ViceGeneralManager("王五");
//        Leader c = new GeneralManager("波波烤鸭");
//        //组织责任链对象的关系
//        a.setNextLeader(b);
//        b.setNextLeader(b2);
//        b2.setNextLeader(c);
//
//        //开始请假操作
//        LeaveRequest req1 = new LeaveRequest("TOM", 15, "老婆生孩子回家探望！");
//        a.handleRequest(req1);

        System.out.println("2^3的结果:" + (2 ^ 3));
        System.out.println("2&3的结果:" + (8 << 2));
        System.out.println(5);
        System.out.println(~5);
        int statues = 0;

        int statue_a = 0b01;
        int statue_b = 0b10;
        int statue_c = 0b100;
        int statue_d = 0b1000;
        int statue_f = 0b10000;
        int statue_g = 0b100000;


        statues = statue_b | statue_d;

        System.out.println(statues);

        System.out.println(isStatusEnabled(statues,statue_f));
        System.out.println(1010&1010);

    }

    public static boolean isStatusEnabled(int statuses, int status) {
        return (statuses & status) != 0;
    }
}
