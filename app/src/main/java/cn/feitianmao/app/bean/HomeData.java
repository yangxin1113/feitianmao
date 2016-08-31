package cn.feitianmao.app.bean;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class HomeData {

    private int id;
    private String topic;
    private String question;
    private String answer;
    private String topicImg;
    private String headImg;
    private String username;
    private String agreecount;
    private String talkcount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgreecount() {
        return agreecount;
    }

    public void setAgreecount(String agreecount) {
        this.agreecount = agreecount;
    }

    public String getTalkcount() {
        return talkcount;
    }

    public void setTalkcount(String talkcount) {
        this.talkcount = talkcount;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
