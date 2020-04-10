package com.lwb.google.thumbnailator.demo;

import ch.qos.logback.classic.spi.LoggingEventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuweibo
 * @date 2020/4/10
 */
public class SocketAppenderDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketAppenderDemo.class);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(6000);

        while (true) {
            Socket client = socket.accept();
            Thread t = new Thread(new LogRunner(client));
            t.start();
        }
    }

    static class LogRunner implements Runnable {
        private ObjectInputStream ois;

        public LogRunner(Socket client) {
            try {
                this.ois = new ObjectInputStream(client.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    LoggingEventVO obj = (LoggingEventVO) ois.readObject();
                    System.out.println(obj.getMessage());
                }
            } catch (java.io.EOFException e) {
                //读取的时候到达尾端抛出的异常，屏蔽掉
                System.out.println(e.getMessage());
            } catch (java.net.SocketException e) {
            } catch (InterruptedIOException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}