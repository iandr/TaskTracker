package com.geekbrains.lesson7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        Car.countDownLatchFinish = new CountDownLatch(CARS_COUNT);
        Car.countDownLatchPrepare = new CountDownLatch(CARS_COUNT);
        Car.rwl = new ReentrantReadWriteLock();
        new Thread(() -> {
            for (int i = 0; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }
        }).start();

        try {
            Car.rwl.writeLock().lock();
            Car.countDownLatchPrepare.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Car.rwl.writeLock().unlock();
        }

        try {
            Car.countDownLatchFinish.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
