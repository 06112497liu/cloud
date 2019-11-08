package com.lwb.user.service.custom.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lwb.user.service.custom.Model;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

/**
 * @author liuweibo
 * @date 2019/10/18
 */
public class Test {

    @org.junit.Test
    public void m01() throws IOException {

        String jsonStr = "{\n" +
            "  \"id\": 1,\n" +
            "  \"name\": \"sam\",\n" +
            "  \"ss\": \"dd\"\n" +
            "}";

        ObjectMapper mapper = new ObjectMapper();

        // 反序列化时，若遇到未知属性，是否抛出异常，默认是抛出的，下面这个配置是关闭了
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Model model = mapper.readValue(jsonStr, Model.class);
        System.out.println(model);


    }

    @org.junit.Test
    public void m02() throws IOException {


        ObjectMapper mapper = new ObjectMapper();

        Model model = new Model(1L, "sam", new Date(), LocalDateTime.now());
        model.setOptional(Optional.empty());


        // 关闭序列化时，Date序列化成时间戳的配置，而是序列化成默认的时间格式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 对jdk8时间格式的支持
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(
            LocalDateTime.class,
            // 采用本地时间格式进行序列化，
            new LocalDateTimeSerializer(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
        );
        mapper.registerModule(module);

        mapper.registerModule(new Jdk8Module());

        String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
        System.out.println(s);
    }

    /**
     * jackson反序列化时，默认使用{@link StdDateFormat}来对{@link Date}类型进行序列化
     * {@link StdDateFormat}默认支持4种格式日期字符串：
     * 1. yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * 2. yyyy-MM-dd'T'HH:mm:ss.SSS
     * 3. EEE, dd MMM yyyy HH:mm:ss zzz
     * 4. yyyy-MM-dd
     * {@link ObjectMapper#setDateFormat(DateFormat)} 方法只能设置一种格式，如果设置成yyyy-MM-dd，其它所有格式反序列化时就会报错
     * 如果要支持多种格式，可以像{@link StdDateFormat}一样，完全重写，然后自己实现一套逻辑
     * 所以前端在传参时，尽量统一格式，以保证反序列化成功
     *
     * @throws IOException
     */
    @org.junit.Test
    public void m03() throws IOException {
        String json = "{\n" +
            "  \"id\": 1,\n" +
            "  \"name\": \"sam\",\n" +
            "  \"date1\": \"2018-01-01\",\n" +
            "  \"date2\": \"2018-01-01\",\n" +
            "  \"date3\": \"2018-01-01\",\n" +
            "  \"localDate\": \"2018-01-01\",\n" +
            "  \"localDateTime\": \"2018-01-01 15:28:25\"\n" +
            "}";

        ObjectMapper mapper = new ObjectMapper();

        // 序列化默认忽略未知字段，默认是不会忽略的，会报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        mapper.registerModule(timeModule);

        Model model = mapper.readValue(json, Model.class);

        System.out.println(model);
    }

    @org.junit.Test
    public void m04() throws IOException {

        JsonFactory factory = new JsonFactory();

        // -----------------JsonGenerator --------------------

        JsonGenerator generator = factory.createGenerator(new File("user.json"), JsonEncoding.UTF8);
        generator.writeStartObject(); // {
        generator.writeStringField("name", "Tomson");
        generator.writeNumberField("age", 23);

        generator.writeFieldName("messages");
        generator.writeStartArray(); // [
        generator.writeString("msg1");
        generator.writeString("msg2");
        generator.writeString("msg3");

        generator.writeEndArray(); // ]

        generator.writeEndObject(); // }


        generator.close();

        // ------------------JsonParser --------------------

        JsonParser parser = factory.createParser(new File("user.json"));

        ObjectMapper mapper = new ObjectMapper();
        Object o = mapper.readValue(parser, Object.class);
        System.out.println(o);

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.getCurrentName();
            if ("name".equals(fieldName)) {
                // current token is "name",move to next which is "name"'s value.
                parser.nextToken();
                System.out.println(parser.getText());// display "Tomson"
            }

            if ("age".equals(fieldName)) {
                parser.nextToken();
                System.out.println(parser.getIntValue());
            }

            if ("messages".equals(fieldName)) {
                parser.nextToken();
                // messages is array, loop until equals "]"

                while(parser.nextToken() != JsonToken.END_ARRAY) {
                    System.out.println(parser.getText());
                }
            }
        }
        parser.close();
    }

    @org.junit.Test
    public void m05() throws IOException {
        String json = "{\n" +
            "  \"id\": 1,\n" +
            "  \"name\": \"sam\",\n" +
            "  \"date1\": \"2018-01-01\",\n" +
            "  \"date2\": \"2018-01-01\",\n" +
            "  \"date3\": \"2018-01-01 15:28:25\",\n" +
            "  \"localDate\": \"2018-01-01\",\n" +
            "  \"localDateTime\": \"2018-01-01 15:28:25\",\n" +
            "  \"arr\": []\n" +
            "}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        mapper.registerModule(timeModule);

        JsonNode jsonNode = mapper.readTree(json);

        System.out.println(jsonNode);
    }

    @org.junit.Test
    public void m06() {
        TimeZone timeZoneNY = TimeZone.getTimeZone("CTT");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.CHINA);
        outputFormat.setTimeZone(timeZoneNY);
        Date date = new Date(System.currentTimeMillis());
        System.out.println(outputFormat.format(date));
    }
}



