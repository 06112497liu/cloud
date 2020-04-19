package com.lwb.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
    public static void main(String[] args) {

        LOGGER.info("demo");

    }
}
