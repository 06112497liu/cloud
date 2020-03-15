package com.lwb.google.thumbnailator.demo;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @author liuweibo
 * @date 2020/3/13
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        m01();
    }

    public static void m01() throws IOException {
        Thumbnails.of("F:\\tmp\\新家管_主界面.jpg")
            .scale(1f)
            .outputQuality(0.9f)
            .toFile(new File("F:\\tmp\\新家管_主界面-1.jpg"));

    }
}
