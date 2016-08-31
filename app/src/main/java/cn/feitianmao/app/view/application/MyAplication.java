package cn.feitianmao.app.view.application;

import java.io.File;
import java.util.Map;

import cn.feitianmao.app.base.BaseApplication;
import cn.feitianmao.app.http.Contants;
import cn.feitianmao.app.utils.MyUtils;
import cn.feitianmao.app.utils.SaveListObject;


/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class MyAplication extends BaseApplication{

    private Map<String, Object> apis;// 用户资料


    @Override
    protected void setConfig() {
        super.setConfig();

    }

    public Map<String, Object> getApis(){
        if(apis == null){
            File file = MyUtils.getInstance().getCache(getApplicationContext(),
                    Contants.USER_PATH_PRIVATE, Contants.CONFIG_APIS, true);
            apis = (Map<String, Object>) SaveListObject.getInstance()
                    .openObject(file);
        }
        return apis;
    }

    public void setApis(Map<String, Object> apis) {
        if (apis == null) {
            MyUtils.getInstance()
                    .getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.CONFIG_APIS,
                            true).delete();
        } else {
            SaveListObject.getInstance().saveObject(
                    MyUtils.getInstance().getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.CONFIG_APIS,
                            true), apis);
        }
        this.apis = apis;
    }
}
