package com.awen.codebase.model;

/**
 * Created by Administrator on 2017/12/13.
 */

public class DesignPattern {

    public DesignPattern() {

        HavardCar havardCar = new HavardCar();
        Person boy = new Person(havardCar);
        boy.drive();

        CayenneCar bydCar = new CayenneCar();
        Person girl = new Person(bydCar);
        girl.drive();
    }
}
