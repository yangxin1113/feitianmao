package cn.feitianmao.app.view.me;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import cn.feitianmao.app.R;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.view.application.MyAplication;
import cn.sharesdk.framework.Platform;
import butterknife.BindView;

import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;


/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class LoginActivity  extends BaseFragmentActivity implements Handler.Callback,
        PlatformActionListener {

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.ed_account)
    EditText ed_account;
    @BindView(R.id.ed_pwd)
    EditText ed_pwd;
    @BindView(R.id.tv_lost_pwd)
    TextView tv_lost_pwd;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.ll_qq)
    LinearLayout ll_qq;
    @BindView(R.id.ll_weixin)
    LinearLayout ll_weixin;
    @BindView(R.id.ll_weibo)
    LinearLayout ll_weibo;

    //状态
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR= 4;
    private static final int MSG_AUTH_COMPLETE = 5;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
    }

    @Override
    protected void setInitData() {

    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_lost_pwd.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);
        ll_weibo.setOnClickListener(this);
        ed_pwd.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.tv_right:
                Bundle bundle = new Bundle();
                bundle.putString("title","注册");
                showItemActivity(bundle, RegisterActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.tv_lost_pwd:
                bundle = new Bundle();
                bundle.putString("title","忘记密码");
                showItemActivity(bundle, RegisterActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.bt_login:
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", ed_account.getText().toString());
                params.put("password", ed_pwd.getText().toString());
                params.put("from", "Android");
                login(params);
                break;
            case R.id.ll_qq:
                authorize(new QQ(this));
                break;
            case R.id.ll_weixin:
                authorize(new Wechat(this));
                break;
            case R.id.ll_weibo:
                authorize(new SinaWeibo(this));
                break;
        }
    }

    //登录
    private void login(Map<String, String> params) {
         final String LOGIN_URL = ((MyAplication)getApplication()).getApis().get("Host").toString()+
                 ((MyAplication)getApplication()).getApis().get("UserLogin").toString();

        OkHttpUtils.post()
                .url(LOGIN_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {
                        LSUtils.showToast(getApplicationContext(), s);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void authorize(Platform plat) {
        if(plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        System.out.println(res);
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {

                String text = getString(R.string.loginingMob, msg.obj);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                System.out.println("---------------");

//				Builder builder = new Builder(this);
//				builder.setTitle(R.string.if_register_needed);
//				builder.setMessage(R.string.after_auth);
//				builder.setPositiveBu萨达萨达是打发打发
// tton(R.string.ok, null);
//				builder.create().show();
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUT实打实的H_CANCEL--------");
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_ERROR--------");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
                System.out.println("--------MSG_AUTH_COMPLETE-------");
            }
            break;
        }
        return false;
    }

    /**
     * 输入框为空时,登录按钮不可点击
     *
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ColorStateList csl = null;
            if(TextUtils.isEmpty(s.toString())){
                bt_login.setBackgroundResource(R.color.unable_press_bg);
                bt_login.setTextColor(getResources().getColor(R.color.unable_press_text));
            }else{
                bt_login.setBackgroundResource(R.drawable.bt_single);
                bt_login.setTextColor(getResources().getColor(R.color.white));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

    };


    /**
     * 要判断 key value 是否存在，不要因为不存在报错， 做一个 json解析入口的数据统一处理， null数据解析成 0
     * @param userInfo
     * @return
     */
    public String  checkUserInfo(String userInfo){
        String result = "";
        try {
            JSONObject json = new JSONObject(userInfo);
            Iterator it = json.keys();
            while(it.hasNext()){
                String key = (String)it.next();
                String value = json.getString(key);
                if(value==null || value.equals("")){
                    json.put(key,"0");
                }
            }
            result = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
