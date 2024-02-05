package com.example.mock.web;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        System.out.println(uuid);
        double rangeMin = 0.0;
        double rangeMax = 1.0;
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        System.out.println(randomValue);
        System.out.println(System.currentTimeMillis());
    }
}
