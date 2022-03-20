package service.impl;

import dao.IQuestionBankDao;
import dao.impl.IQuestionBankDaoImpl;
import domain.QuestionBank;
import service.QuestionBankService;

/**
 * @ClassName QuestionBankImpl
 * @Description TODO
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 9:34
 * @Version 1.0
 */
public class QuestionBankServiceImpl implements QuestionBankService {
    IQuestionBankDao iQuestionBankDao = new IQuestionBankDaoImpl();
    QuestionBank questionBank = new QuestionBank();
    String str;
//注释
    @Override
    public String printPaper(int id) {
        questionBank = iQuestionBankDao.queryById(id);
        str = questionBank.getContent() + " " + questionBank.getUseranswer() + "    正确答案：" + questionBank.getResult() + "得分：" + questionBank.getScore();
        return str;
    }

    @Override
    public void recordScore(int score, int id) {
        iQuestionBankDao.updateScore(score, id);
    }

    @Override
    public void recordAnswer(int answer, int id) {
        iQuestionBankDao.updateAnswer(answer, id);
    }

    @Override
    public int findResult(int id) {
      return iQuestionBankDao.selectResultById(id);
    }

    @Override
    public String findQuestion(int id) {
        return iQuestionBankDao.selectContentById(id);
    }
}
