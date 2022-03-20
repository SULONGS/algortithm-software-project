package domain;


public class QuestionBank {

  private String id;
  private String content;
  private int result;
  private long useranswer;
  private long score;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }


  public long getUseranswer() {
    return useranswer;
  }

  public void setUseranswer(long useranswer) {
    this.useranswer = useranswer;
  }


  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

}
