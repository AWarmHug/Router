package com.warm.router.demo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static class ProcessChain implements Chain {

        private List<Handler> mHandlers;
        private int mIndex;
        private Action mAction;

        public ProcessChain(List<Handler> handlers, int index, Action action) {
            mHandlers = handlers;
            mIndex = index;
            mAction = action;
        }

        @Override
        public Action action() {
            return mAction;
        }

        @Override
        public void proceed(Action action) {
            if (mHandlers.size() > mIndex) {
                Handler handler = mHandlers.get(mIndex);
                ProcessChain chain = new ProcessChain(mHandlers, mIndex + 1, mAction);
                handler.handle(chain);
            }
        }

        @Override
        public void abort() {

        }
    }

    public static class OpenHandler implements Handler {

        @Override
        public void handle(Chain chain) {
            chain.proceed(chain.action());
        }
    }

    public static class MoveHandler implements Handler {

        @Override
        public void handle(Chain chain) {
            chain.proceed(chain.action());
        }
    }

    public static class LieHandler implements Handler {

        @Override
        public void handle(Chain chain) {
            chain.proceed(chain.action());
        }
    }


    public static void main(String[] args) {
        Action action = new Action();
        List<Handler> mHandler = new ArrayList<>();
        mHandler.add(new OpenHandler());
        mHandler.add(new MoveHandler());
        mHandler.add(new LieHandler());
        ProcessChain chain = new ProcessChain(mHandler, 0, action);
        chain.proceed(action);
    }
}
