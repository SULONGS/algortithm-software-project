package com.hutb.dao;

import com.hutb.domain.User;
import org.apache.ibatis.annotations.Param;

import java.sql.ResultSet;

/**
 * @InterfaceName IUserDaoImpl
 * @Description operate user table
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/17 20:42
 * @Version 1.0
 */

public interface IUserDao {

    public int updateScore(@Param("score") int score, @Param("name") String name);

    public int selectScoreByName(String name);

    public User queryByName(String name);

    public int insertUser(@Param("name") String name, @Param("password") String password);


}
