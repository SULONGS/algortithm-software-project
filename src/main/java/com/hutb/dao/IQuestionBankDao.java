package com.hutb.dao;


import com.hutb.domain.QuestionBank;
import org.apache.ibatis.annotations.Param;

/**
 * @InterfaceName IUserDaoImpl
 * @Description operate questionbank table
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/17 20:42
 * @Version 1.0
 */
public interface IQuestionBankDao {

    public QuestionBank queryById(int id);

    public int updateScore(@Param("score")int score, @Param("id")int id);

    public int updateAnswer(@Param("useranswer")int answer,@Param("id") int id);

    public int selectResultById(int id);

    public String selectContentById(int id);

}
