package com.human_developing_soft.accurate_translation.translation.domain;

public class StayRunnable implements Runnable {
    private final Runnable mCodeBlock;

    public StayRunnable(Runnable pRunnable) {
        mCodeBlock = pRunnable;
    }

    @Override
    public void run() {
        mCodeBlock.run();
    }
}
