package com.warm.router.demo;

public class Director extends Leader {
    public Director(String name) {
        super(name);
    }

    @Override
    public void handleRequest(LeaveRequest request) {
        if (request.getLeaveDays() < 3) {
            System.out.println("员工：" + request.getEmpName() + "请假，天数" + request.getLeaveDays() + "原因：" + request.getReason());
            System.out.println("主任：" + name + "批准");
        } else {
            if (nextLeader != null) {
                nextLeader.handleRequest(request);
            }
        }
    }
}
