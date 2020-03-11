package com.awen.codebase.model.design;

/**
 * Created by Administrator on 2017/12/13.
 */

public class Person {

    private ICar car;

    public Person(ICar car) {
        this.car = car;
    }

    public void drive(){
        car.addGas();
        car.brakeCar();
        car.controlDirection();
    }
}
