package com.peter8icestone.test;

import com.peter8icestone.io.Resources;

import java.io.InputStream;
import java.util.Optional;

public class IPersistenceTest {

    private static void test() {
        InputStream resourceAsStream = Resources.getResourceAsStream("userMapper.xml");
        Optional.ofNullable(resourceAsStream).ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        test();
    }
}
