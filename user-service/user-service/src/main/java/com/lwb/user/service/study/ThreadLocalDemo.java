package com.lwb.user.service.study;

public class ThreadLocalDemo {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        System.out.println(threadLocal.get());

        threadLocal.set(55);

        System.out.println(threadLocal.get());

    }
}
