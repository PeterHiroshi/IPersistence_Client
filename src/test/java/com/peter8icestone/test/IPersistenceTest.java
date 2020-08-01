package com.peter8icestone.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.impl.C3P0PooledConnection;
import com.peter8icestone.io.Resources;
import com.peter8icestone.pojo.User;
import com.peter8icestone.sqlSession.SqlSessionFactory;
import com.peter8icestone.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class IPersistenceTest {

    @Test
    public void testSelectOne() {
        InputStream sqlMapConfigStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(sqlMapConfigStream);
        } catch (DocumentException | PropertyVetoException e) {
            e.printStackTrace();
        }
        String statementId = "user.selectOne";
        User user = new User();
        user.setUserId(1);
        user.setUsername("Peter");
        User selectedUser = (User) Optional.ofNullable(sqlSessionFactory)
                .map(SqlSessionFactory::openSession)
                .map(sqlSession -> sqlSession.selectOne(statementId, user))
                .orElse(null);
        Optional.ofNullable(selectedUser).ifPresent(System.out::println);
    }

    @Test
    public void testSelectList() throws PropertyVetoException, DocumentException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        List<User> users = sqlSessionFactory.openSession().selectList("user.selectList");
        users.forEach(System.out::println);
    }

    @Test
    public void testC3P0() {
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jdbcTest";
        String username = "root";
        String password = "a129bzhma";
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(driverClass);
            comboPooledDataSource.setJdbcUrl(url);
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);
            Connection connection = comboPooledDataSource.getConnection();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM t_user").executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2));
            }
            connection.close();
        } catch (PropertyVetoException | SQLException e) {
            e.printStackTrace();
        }
    }
}
