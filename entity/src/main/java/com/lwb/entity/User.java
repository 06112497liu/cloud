package com.lwb.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {

    Long id;
    String name;

}
