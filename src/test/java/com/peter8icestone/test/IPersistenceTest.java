package com.peter8icestone.test;

import com.peter8icestone.config.XMLConfigBuilder;
import com.peter8icestone.io.Resources;
import com.peter8icestone.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.Optional;

public class IPersistenceTest {

    private static void test() {
        InputStream sqlMapConfigStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = null;
        try {
            configuration = xmlConfigBuilder.parseConfig(sqlMapConfigStream);
        } catch (DocumentException | PropertyVetoException e) {
            e.printStackTrace();
        }
        Optional.ofNullable(configuration)
                .map(Configuration::getMappedStatementMap)
                .ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        test();
    }
}
