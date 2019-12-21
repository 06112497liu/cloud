package com.lwb.user.service.study;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class WeakReferenceDemo {

    public static void main(String[] args) {

//        ReferenceQueue queue = new ReferenceQueue();
//
//        Salad salad = new Salad(new Apple("红富士"), queue);
//        System.out.println("gc前：" + salad.get());
//
//        System.out.println(queue.poll());
//        System.gc();
//
//        System.out.println("gc后：" + salad.get());
//
//        System.out.println(queue.poll());

        m02();

    }

    public static void m01() {
        Apple apple = new Apple("红富士");
        WeakReference<Apple> weakReference = new WeakReference<>(apple);
        int i = 0;
        while (true) {
            Apple a = weakReference.get();
            if (a != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + a);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        }
    }

    public static void m02() {
        Apple apple = new Apple("红富士");
        WeakReference<Apple> weakReference = new WeakReference<>(apple);

        Map<String, WeakReference<Apple>> map = new HashMap<>();
        map.put("a", weakReference);
        int i = 0;
        while (true) {
            Apple a = map.get("a").get();
            if (a != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + a);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        }
    }
}
