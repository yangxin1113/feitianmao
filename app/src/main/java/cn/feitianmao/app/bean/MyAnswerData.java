package cn.feitianmao.app.bean;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class MyAnswerData {

    private int id;
    private String question;
    private String answer;
    private String topicImg;
    private String headImg;
    private int agreecount;
    private int talkcount;
    private int time;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getAgreecount() {
        return agreecount;
    }

    public void setAgreecount(int agreecount) {
        this.agreecount = agreecount;
    }

    public int getTalkcount() {
        return talkcount;
    }

    public void setTalkcount(int talkcount) {
        this.talkcount = talkcount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
