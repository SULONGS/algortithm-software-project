package com.hutb.service.impl;

import com.hutb.dao.IQuestionBankDao;
import com.hutb.domain.QuestionBank;
import com.hutb.service.QuestionBankService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;


/**
 * @ClassName QuestionBankImpl
 * @Description TODO
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 9:34
 * @Version 1.0
 */
public class QuestionBankServiceImpl implements QuestionBankService {
    private InputStream in;
    private SqlSession sqlSession;
    private IQuestionBankDao iQuestionBankDao;
    private QuestionBank questionBank;

    public QuestionBankServiceImpl() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession(true);
        //4.获取dao的代理对象
        iQuestionBankDao = sqlSession.getMapper(IQuestionBankDao.class);
    }

    public String printQueAndAns(int id) {
        String str;
        questionBank = iQuestionBankDao.queryById(id);
        str = questionBank.getContent() + " " + questionBank.getUseranswer();
        return str;
    }
    public String printResult(int id) {
        String str;
        questionBank = iQuestionBankDao.queryById(id);
        str ="正确答案：" + questionBank.getResult() + "得分：" + questionBank.getScore();
        return str;
    }



    public void recordScore(int score, int id) {
        iQuestionBankDao.updateScore(score, id);
    }


    public void recordAnswer(int answer, int id) {
        iQuestionBankDao.updateAnswer(answer, id);
    }


    public int findResult(int id) {
      return iQuestionBankDao.selectResultById(id);
    }


    public String findQuestion(int id) {
        return iQuestionBankDao.selectContentById(id);
    }
}
