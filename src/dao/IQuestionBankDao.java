package dao;

import domain.QuestionBank;

/**
 * @InterfaceName IUserDaoImpl
 * @Description operate questionbank table
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/17 20:42
 * @Version 1.0
 */
public interface IQuestionBankDao {

    public QuestionBank queryById(int id);

    public int updateScore(int score, int id);

    public int updateAnswer(int answer, int id);

    public int selectResultById(int id);

    public String selectContentById(int id);

}
