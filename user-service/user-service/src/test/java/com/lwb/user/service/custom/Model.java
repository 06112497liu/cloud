package com.lwb.user.service.custom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author liuweibo
 * @date 2019/10/17
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    Long id;
    String name;

    Date date1;

    Date date2;

    Date date3;

    LocalDate localDate;

    LocalDateTime localDateTime;

    Optional<String> optional;

    public Model(Long id, String name, Date date1, LocalDateTime localDateTime) {
        this.id = id;
        this.name = name;
        this.date1 = date1;
        this.localDateTime = localDateTime;
    }
}
