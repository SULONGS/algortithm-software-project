package dao.impl;

import dao.IQuestionBankDao;
import domain.QuestionBank;
import utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName IQuestionBankImpl
 * @Description TODO
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 10:26
 * @Version 1.0
 */
public class IQuestionBankDaoImpl implements IQuestionBankDao {
    QuestionBank questionbank = new QuestionBank();
    DBUtils dbUtils = new DBUtils();


    @Override
    public QuestionBank queryById(int id) {
        return _questionBank(dbUtils.query("SELECT * FROM questionbank WHERE id=?", id));
    }

    @Override
    public int updateScore(int score, int id) {
        return dbUtils.update("UPDATE questionbank SET score = ? WHERE id = ?", score, id);

    }

    @Override
    public int updateAnswer(int answer, int id) {
        return dbUtils.update("UPDATE questionbank SET useranswer = ? WHERE id = ?", answer, id);
    }

    @Override
    public int selectResultById(int id) {

        return _result(dbUtils.query("SELECT result FROM questionbank WHERE id=? ", id));
    }

    @Override
    public String selectContentById(int id) {
        return _content(dbUtils.query("SELECT content FROM questionbank WHERE id=? ", id));
    }


    int _result(ResultSet rs) {
        try {
            while (rs.next()) {
                questionbank.setResult(rs.getInt("result"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtils.closeAll();
        }
        return questionbank.getResult();
    }

    String _content(ResultSet rs) {
        try {
            while (rs.next()) {
                questionbank.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbUtils.closeAll();
        }
        return questionbank.getContent();
    }

    QuestionBank _questionBank(ResultSet rs) {
        try {
            while (rs.next()) {
                questionbank.setContent(rs.getString("content"));
                questionbank.setResult(rs.getInt("result"));
                questionbank.setUseranswer(rs.getInt("useranswer"));
                questionbank.setScore(rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionbank;
    }
}
