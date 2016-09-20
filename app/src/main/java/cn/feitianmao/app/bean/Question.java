package cn.feitianmao.app.bean;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class Question {

    private int id;
    private String question;
    private int guanzhucount;
    private int answercount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getGuanzhucount() {
        return guanzhucount;
    }

    public void setGuanzhucount(int guanzhucount) {
        this.guanzhucount = guanzhucount;
    }

    public int getAnswercount() {
        return answercount;
    }

    public void setAnswercount(int answercount) {
        this.answercount = answercount;
    }
}
