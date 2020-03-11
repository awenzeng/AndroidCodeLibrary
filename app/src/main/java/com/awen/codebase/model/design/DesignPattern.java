package com.awen.codebase.model.design;


/**
 * 设计模式Pattern
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
