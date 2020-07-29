package com.peter8icestone.test;

import com.peter8icestone.io.Resources;
import com.peter8icestone.pojo.User;
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
        String statementId = "user.selectOne";
        User user = new User();
        user.setId(7);
        user.setUserName("Peter");
        User selectedUser = (User) Optional.ofNullable(sqlSessionFactory)
                .map(SqlSessionFactory::openSession)
                .map(sqlSession -> sqlSession.selectOne(statementId, user))
                .orElse(null);
        Optional.ofNullable(selectedUser).ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        test();
    }
}
