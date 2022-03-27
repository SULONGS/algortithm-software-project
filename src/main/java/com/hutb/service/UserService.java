package com.hutb.service;

/**
 * @InterfaceName UserService
 * @Description service for users
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 9:30
 * @Version 1.0
 */
public interface UserService {
    /*
     * @Author Yuan Fen,Yiyou Long
     * @Description  test login situation
     * @Date 9:44 2022/3/18
     * @Param [name, password]
     * @return int  username existing but password wrong, username not existing, login successfully are different from int.
     **/
    int login(String name,String password);

    Boolean register(String name,String password);

    int historicScore(String name);

    int recordSum(int score,String name);
}
