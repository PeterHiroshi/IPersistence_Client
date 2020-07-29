package com.peter8icestone.test;

import com.peter8icestone.io.Resources;
import com.peter8icestone.sqlSession.SqlSession;
import com.peter8icestone.sqlSession.SqlSessionFactory;
import com.peter8icestone.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.Optional;

public class IPersistenceTest {

    private static void test() {
        InputStream sqlMapConfigStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(sqlMapConfigStream);
        } catch (DocumentException | PropertyVetoException e) {
            e.printStackTrace();
        }
        SqlSession sqlSession = Optional.ofNullable(sqlSessionFactory)
                .map(SqlSessionFactory::openSession)
                .orElse(null);
    }

    public static void main(String[] args) {
        test();
    }
}
