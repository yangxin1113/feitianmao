package cn.feitianmao.app.bean;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class FavoriteList {


    /**
     * _id : 57e7a29eacdbc449b04524e0
     * favorites_name : moren
     * favorites_content :
     * uid : 200010
     * createtime : 1474798238
     * updatetime : 1474798238
     * is_delete : 0
     * favorite_count : 0
     * number : 0
     */

    private String _id;
    private String favorites_name;
    private String favorites_content;
    private int uid;
    private int createtime;
    private int updatetime;
    private int is_delete;
    private int favorite_count;
    private int number;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFavorites_name() {
        return favorites_name;
    }

    public void setFavorites_name(String favorites_name) {
        this.favorites_name = favorites_name;
    }

    public String getFavorites_content() {
        return favorites_content;
    }

    public void setFavorites_content(String favorites_content) {
        this.favorites_content = favorites_content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
