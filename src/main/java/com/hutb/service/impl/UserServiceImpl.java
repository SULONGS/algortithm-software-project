package com.hutb.service.impl;


import com.hutb.dao.IUserDao;

import com.hutb.domain.User;
import com.hutb.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 9:35
 * @Version 1.0
 */

public class UserServiceImpl implements UserService {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao iUserDao;
    private User user;

    public UserServiceImpl() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession(true);
        //4.获取dao的代理对象
        iUserDao = sqlSession.getMapper(IUserDao.class);
    }

    @Override
    public int login(String name, String password) {
        int loginSuccess = 1;
        int passWrong = 2;
        int nameInvalid = 3;
        user = iUserDao.queryByName(name);
        if (user.getName() != null) {
            if (user.getPassword().equals(password)) {
                return loginSuccess;
            } else {
                System.out.println(passWrong);
                return passWrong;
            }
        } else {
            System.out.println(nameInvalid);
            return nameInvalid;
        }
    }


    @Override
    public Boolean register(String name, String password) {
        if (iUserDao.insertUser(name, password) > 0) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public int historicScore(String name) {

        return iUserDao.selectScoreByName(name);

    }


    @Override
    public int recordSum (int score,String name){
       return iUserDao.updateScore(score,name);
    }
}
