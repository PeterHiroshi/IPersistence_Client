package com.peter8icestone.config;

import com.peter8icestone.io.Resources;
import com.peter8icestone.pojo.Configuration;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class XMLConfigBuilderTest {

    InputStream inputStream;

    @Before
    public void before() {
        inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    }

    @Test
    public void parseConfig() throws PropertyVetoException, DocumentException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);
        configuration.getMappedStatementMap()
                .forEach((id, statement) -> System.out.println(id + "->" + statement));
    }
}
