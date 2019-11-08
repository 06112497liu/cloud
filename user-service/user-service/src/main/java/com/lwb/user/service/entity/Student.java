package com.lwb.user.service.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuweibo
 * @date 2019/10/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student implements Serializable {

    private static final long serialVersionUID = -4058126937525486564L;

    Long id;
    String name;
    String grade;
    String classes;
    Long length;
    List<Long> bookIds;
    Long[] ids;
}
