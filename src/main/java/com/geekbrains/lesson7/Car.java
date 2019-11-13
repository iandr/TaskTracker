package com.geekbrains.lesson7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    public static ReentrantReadWriteLock rwl;
    public static CountDownLatch countDownLatchFinish;
    public static CountDownLatch countDownLatchPrepare;
    private static boolean winnerExists = false;
    private static Lock lockWin = new ReentrantLock();

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            countDownLatchPrepare.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Car.rwl.readLock().lock();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
        } finally {
            Car.rwl.readLock().unlock();
        }

        try {
            lockWin.lock();
            if (!winnerExists) {
                System.out.println(this.name + " - WIN");
                winnerExists = true;
            }
        } finally {
            lockWin.unlock();
        }
        countDownLatchFinish.countDown();
    }
}