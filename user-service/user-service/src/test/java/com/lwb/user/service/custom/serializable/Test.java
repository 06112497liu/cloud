package com.lwb.user.service.custom.serializable;

import com.lwb.user.service.entity.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuweibo
 * @date 2019/10/14
 */
public class Test {

    @org.junit.Test
    public void m01() throws IOException, ClassNotFoundException {
        FileOutputStream out = new FileOutputStream("F:/tmp/student.txt");
        ObjectOutputStream objOut = new ObjectOutputStream(out);

        Student s = new Student();
        s.setId(5L);
        s.setName("小明");
        s.setGrade("5年级");
        s.setClasses("3班");
        objOut.writeObject(s);
        objOut.flush();
        objOut.close();

        FileInputStream in = new FileInputStream("F:/tmp/student.txt");
        ObjectInputStream objIn = new ObjectInputStream(in);
        Object o = objIn.readObject();
        System.out.println(o);
    }

    @org.junit.Test
    public void m02() throws IOException, ClassNotFoundException {
        FileOutputStream out = new FileOutputStream("F:/tmp/student.txt");
        ObjectOutputStream objOut = new ObjectOutputStream(out);

        List<Student> list = new ArrayList<>();

        Student s = new Student();
        s.setId(5L);
        s.setName("小明");
        s.setGrade("5年级");
        s.setClasses("3班");
        list.add(s);

        objOut.writeObject(list);
        objOut.flush();
        objOut.close();

        FileInputStream in = new FileInputStream("F:/tmp/student.txt");
        ObjectInputStream objIn = new ObjectInputStream(in);
        Object o = objIn.readObject();
        System.out.println(o);
    }
}
