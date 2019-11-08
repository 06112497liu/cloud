package com.lwb.user.service.custom.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lwb.user.service.custom.Model;
import com.lwb.user.service.entity.Student;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.util.Lists;
import org.junit.Before;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;

/**
 * @author liuweibo
 * @date 2019/10/14
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Test {

    SerializeConfig serializeConfig = SerializeConfig.globalInstance;

    SerializerFeature[] features;

    @Before
    public void serializeConfigs() {
        this.serializeConfig.put(BigInteger.class, com.alibaba.fastjson.serializer.ToStringSerializer.instance);
        this.serializeConfig.put(Long.class, com.alibaba.fastjson.serializer.ToStringSerializer.instance);
        this.serializeConfig.put(Long.TYPE, com.alibaba.fastjson.serializer.ToStringSerializer.instance);

        //设置序列化的特征
        this.features = new SerializerFeature[]{
            //    输出key是包含双引号
//                SerializerFeature.QuoteFieldNames,
            //    是否输出为null的字段,若为null 则显示该字段
            SerializerFeature.WriteMapNullValue,
            //    数值字段如果为null，则输出为0
            //SerializerFeature.WriteNullNumberAsZero,
            //     List字段如果为null,输出为[],而非null
            SerializerFeature.WriteNullListAsEmpty,
            //    字符类型字段如果为null,输出为"",而非null
//            SerializerFeature.WriteNullStringAsEmpty,
            //    Boolean字段如果为null,输出为false,而非null
            //SerializerFeature.WriteNullBooleanAsFalse,
            //    Date的日期转换器
            SerializerFeature.WriteDateUseDateFormat,
            //    循环引用
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteClassName
        };
    }

    @org.junit.Test
    public void m01() {
        Student s = new Student();
        s.setId(2L);
        s.setName("小明");
        String s1 = JSON.toJSONString(s, serializeConfig, features);
        System.out.println(s1);
    }

    @org.junit.Test
    public void m03() throws InvocationTargetException, IllegalAccessException {
        Student s = new Student();
        s.setId(2L);
        s.setName("小明");

        String s1 = JSON.toJSONString(s, serializeConfig, features);
        System.out.println(s1);
    }

    @org.junit.Test
    public void m02() {
        Student s = new Student();
        s.setId(2L);
        s.setName("小明");
        List<Long> bookIds = Lists.newArrayList();
        bookIds.add(11L);
        s.setBookIds(bookIds);

        SimplePropertyPreFilter preFilter = new SimplePropertyPreFilter();
        preFilter.getIncludes().add("name");

        String s1 = JSON.toJSONString(s, preFilter);

        System.out.println(s1);
    }

    @org.junit.Test
    public void m04() {

        String json = "[{},{}]";

        Type listType = new TypeReference<List<Model>>(){}.getType();

        List<Model> modelList = JSON.parseObject(json, listType);

        List list = JSON.parseObject(json, List.class);
    }

    @org.junit.Test
    public void m05() {
        int mask = Feature.AutoCloseSource.getMask();
        System.out.println(mask);
        int mask1 = Feature.AllowComment.getMask();
        System.out.println(mask1);
        int mask2 = Feature.AllowUnQuotedFieldNames.getMask();
        System.out.println(mask2);
    }

}
