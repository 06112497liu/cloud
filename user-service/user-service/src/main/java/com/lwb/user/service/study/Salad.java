package com.lwb.user.service.study;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class Salad extends WeakReference<Apple> {
    public Salad(Apple referent) {
        super(referent);
    }

    public Salad(Apple referent, ReferenceQueue<? super Apple> q) {
        super(referent, q);
    }
}
