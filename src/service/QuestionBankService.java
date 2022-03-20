package service;

/**
 * @InterfaceName QuestionBankService
 * @Description service for question bank
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 9:30
 * @Version 1.0
 */
public interface QuestionBankService {
    public String printPaper(int id);

    public void recordScore(int score, int id);

    public void recordAnswer(int answer, int id);

    public int findResult(int id);

    public String findQuestion(int id);
}
