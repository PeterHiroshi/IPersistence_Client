package com.peter8icestone.test;

import com.peter8icestone.dao.IUserDao;
import com.peter8icestone.io.Resources;
import com.peter8icestone.pojo.User;
import com.peter8icestone.sqlSession.SqlSession;
import com.peter8icestone.sqlSession.SqlSessionFactory;
import com.peter8icestone.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class IPersistenceTest {

    private SqlSession sqlSession = null;
    private IUserDao iUserDao = null;

    @Before
    public void before() throws PropertyVetoException, DocumentException {
        InputStream sqlMapConfigStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(sqlMapConfigStream);
        sqlSession = sqlSessionFactory.openSession();
        iUserDao = sqlSession.getMapper(IUserDao.class);
    }

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
    public void testFindAll() throws PropertyVetoException, DocumentException {
        InputStream sqlMapConfigStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(sqlMapConfigStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = iUserDao.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void testFindByCondition() throws PropertyVetoException, DocumentException {
        InputStream sqlMapConfigStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(sqlMapConfigStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setUserId(7);
        user.setUsername("Peter8");
        User selectedUser = iUserDao.findByCondition(user);
        Optional.of(selectedUser).ifPresent(System.out::println);
    }

    @Test
    public void testFindByUserId() {
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        User user = iUserDao.findByUserId(7);
        Optional.ofNullable(user).ifPresent(System.out::println);
    }

    @Test
    public void testAddUser() {
        User newUser = new User();
        newUser.setUsername("testUser1");
        newUser.setAge(21);
        int updatedRows = iUserDao.addUser(newUser);
        System.out.println(updatedRows);
    }

    @Test
    public void testUpdateUser() {
        User updateUser = new User();
        updateUser.setUserId(7);
        updateUser.setUsername("PeterNew");
        updateUser.setAge(29);
        int updatedRows = iUserDao.updateUser(updateUser);
        System.out.println(updatedRows);
    }

    @Test
    public void testDeleteUser() {
        int updatedRows = iUserDao.deleteUser(15);
        System.out.println(updatedRows);
    }
}
