package com.hutb.domain;

public class User {

  private long scores;
  private String name;
  private String password;


  public long getScores() {
    return scores;
  }

  public void setScores(long scores) {
    this.scores = scores;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "scores=" + scores +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
