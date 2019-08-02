package com.warm.router.demo;

public class Manager extends Leader {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void handleRequest(LeaveRequest request) {
        if (request.getLeaveDays() < 7) {
            System.out.println("员工：" + request.getEmpName() + "请假，天数" + request.getLeaveDays() + "原因：" + request.getReason());
            System.out.println("经理：" + name + "批准");
        } else {
            if (nextLeader != null) {
                nextLeader.handleRequest(request);
            }
        }
    }
}
