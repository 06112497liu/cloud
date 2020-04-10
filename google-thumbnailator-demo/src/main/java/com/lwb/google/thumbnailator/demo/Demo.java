package com.lwb.google.thumbnailator.demo;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.idrsolutions.image.png.PngCompressor;
import com.tinify.Source;
import com.tinify.Tinify;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * @author liuweibo
 * @date 2020/3/13
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        m01();
//        m02();
//        m06();
    }

    public static void m01() throws IOException {
        for (int i = 9; i > 0; i--) {
            BigDecimal scale = BigDecimal.valueOf(i).divide(BigDecimal.valueOf(10));
            long start = System.currentTimeMillis();
            Thumbnails.of("F:\\tmp\\mmexport1584770653943.jpg") // 456KB
                .scale(scale.doubleValue()) // 缩放比例
                .outputQuality(0.6) // 输出图片质量（1表示不损失图片质量）
                .toFile("F:\\tmp\\mmexport1584770653943-1.jpg"); // 输出地址
            System.out.println("google-jpg: " + (System.currentTimeMillis() - start));
            long length = new File("F:\\tmp\\mmexport1584770653943-1.jpg").length();
            System.out.println(length / 1024);
        }

    }

    public static void m07() throws IOException {
        long start = System.currentTimeMillis();
        Thumbnails.of("F:\\tmp\\mmexport1584770653943.JPG") // 456KB
            .size(1080, 1440)
            .outputQuality(0.5) // 输出图片质量（1表示不损失图片质量）
            .toFile("F:\\tmp\\mmexport1584770653943-1.jpg"); // 输出地址
        System.out.println("google-jpg: " + (System.currentTimeMillis() - start));

    }

    public static void m02() throws IOException {
        long start = System.currentTimeMillis();
        Thumbnails.of("F:\\tmp\\Umix6.png") // 原始图片（693KB）
            .scale(1f) // 不缩放
            .outputQuality(0.9f) // 输出质量
            .outputFormat("jpg") // 转化为jpg图片（png没法在压缩了）
            .toFile("F:\\tmp\\Umix6-google"); // 输出图片
        System.out.println("google-png-jpg: " + (System.currentTimeMillis() - start)); // 这种大小，平均耗时在300-400ms
    }

    public static void m03() throws IOException {

        long start = System.currentTimeMillis();
        String filePath = "F:\\tmp\\Umix6.png";
        File file = new File(filePath);
        File outfile = new File("F:\\tmp\\Umix6-openViewer.png");
        PngCompressor.compress(file, outfile);
        System.out.println("m03: " + (System.currentTimeMillis() - start));
    }

    public static void m04() throws IOException {

        long start = System.currentTimeMillis();

        File in = new File("F:\\tmp\\Umix6.png");      //原图片
        File out = new File("F:\\tmp\\Umix6-alibaba.png");       //目的图片
        ScaleParameter scaleParam = new ScaleParameter();  //将图像缩略到1024x1024以内，不足1024x1024则不做任何处理

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        WriteRender wr = null;
        try {
            inStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            ImageRender rr = new ReadRender(inStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, outStream);

            wr.render();                            //触发图像处理
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream);         //图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    wr.dispose();                   //释放simpleImage的内部资源
                } catch (SimpleImageException ignore) {
                    // skip ...
                }
            }
        }
        System.out.println("m04: " + (System.currentTimeMillis() - start));
    }

    public static void m05() throws IOException {
        Tinify.setKey("589V0PYxzqF1C1Q13qcmVh4ZHkzf8ssr");
        Source source = Tinify.fromFile("F:\\tmp\\Umix6.png");
        source.toFile("F:\\tmp\\Umix6-tinify.png");
    }

    public static void m06() throws IOException {

        long start = System.currentTimeMillis();


        File imageFile = new File("F:\\tmp\\Umix6.png");
        File compressedImageFile = new File("F:\\tmp\\Umix6-jdk.jpg");

        InputStream is = new FileInputStream(imageFile);
        OutputStream os = new FileOutputStream(compressedImageFile);

        float quality = 0.6f;

        // create a BufferedImage as the result of decoding the supplied InputStream
        BufferedImage image = ImageIO.read(is);

        // get all image writers for JPG format
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

        if (!writers.hasNext()) {
            throw new IllegalStateException("No writers found");
        }

        ImageWriter writer = (ImageWriter) writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        // compress to a given quality
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        // appends a complete image stream containing a single image and
        //associated stream and image metadata and thumbnails to the output
        writer.write(null, new IIOImage(image, null, null), param);

        // close all streams
        is.close();
        os.close();
        ios.close();
        writer.dispose();
        System.out.println("jdk: " + (System.currentTimeMillis() - start));

    }
}
