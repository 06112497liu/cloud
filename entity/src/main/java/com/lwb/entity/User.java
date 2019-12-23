package com.lwb.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {

    private static final long serialVersionUID = 2947233466216200520L;

    Long id;
    String name;

}
