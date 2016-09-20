package cn.feitianmao.app.bean;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class HuatiData {

    private int id;
    private String name;
    private String qianming;
    private int isGuanzhu;
    private int guanzhucount;
    private int questioncount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public int getIsGuanzhu() {
        return isGuanzhu;
    }

    public void setIsGuanzhu(int isGuanzhu) {
        this.isGuanzhu = isGuanzhu;
    }

    public int getGuanzhucount() {
        return guanzhucount;
    }

    public void setGuanzhucount(int guanzhucount) {
        this.guanzhucount = guanzhucount;
    }

    public int getQuestioncount() {
        return questioncount;
    }

    public void setQuestioncount(int questioncount) {
        this.questioncount = questioncount;
    }
}
