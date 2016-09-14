package cn.feitianmao.app.view.me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mob.tools.utils.UIHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import cn.feitianmao.app.R;
import cn.feitianmao.app.http.Contants;
import cn.feitianmao.app.http.UpLoadListener;
import cn.feitianmao.app.utils.FileUtil;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.MyUtils;
import cn.feitianmao.app.utils.SaveListObject;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.utils.UploadManager;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.main.IndexActivity;
import cn.sharesdk.framework.Platform;
import butterknife.BindView;

import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Request;


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

    Map<String, String> params;

    //状态
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR= 4;
    private static final int MSG_AUTH_COMPLETE = 5;

    //登录
    private static final int USER_LOGIN = 0;
    private static final int OTHER_LOGIN = 1;


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
         final String LOGIN_URL = ((MyApplication)getApplication()).getApis().get("Host").toString()+
                 ((MyApplication)getApplication()).getApis().get("UserLogin").toString();


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
                        /*GsonBuilder gb = new GsonBuilder();
                        gb.registerTypeAdapter(String.class, new StringConverter());
                        Gson json = gb.create();*/
                        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                        //intent.putExtra("userInfo",s);

                        try {
                            JSONObject json = new JSONObject(s);
                            LSUtils.showToast(getApplicationContext(), json.get("alert").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivityForResult(intent,USER_LOGIN );

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
                login(plat, userId, null);
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
            login(platform, platform.getDb().getUserId(), res);
        }
        System.out.println(res);

        params = new HashMap<String, String>();
        params.put("key", platform.getDb().getUserId());
        params.put("username", platform.getDb().getUserName());
        params.put("avator", platform.getDb().getUserIcon());
        params.put("type", "Android");
        /*System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
        System.out.println("------UserIcon ---------" + platform.getDb().getUserIcon());
        System.out.println("------Token ---------" + platform.getDb().getToken());
        System.out.println("------UserGender ---------" + platform.getDb().getUserGender());
        System.out.println("------TokenSecret ---------" + platform.getDb().getTokenSecret());
        System.out.println("------Secret ---------" + platform.getDb().getTokenSecret());
        System.out.println("------ExpiresIn ---------" + platform.getDb().getExpiresIn());
        System.out.println("------ExpiresTime ---------" + platform.getDb().getExpiresTime());
        System.out.println("------PlatformVersion ---------" + platform.getDb().getPlatformVersion());*/
        System.out.println("------Name ---------" + platform.getName());

        /*Intent i = new Intent(LoginActivity.this, IndexActivity.class);
        i.putExtra("key", platform.getDb().getUserId());
        i.putExtra("username", platform.getDb().getUserId());
        startActivityForResult(i, OTHER_LOGIN);*/
        regAndLogin();

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

    private void login(Platform plat, String userId, HashMap<String, Object> userInfo) {

        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case MSG_USERID_FOUND: {
                LSUtils.i("MSG_USERID_FOUND", R.string.userid_found + (String) msg.obj);
            }
            break;
            case MSG_LOGIN: {

                String text = getString(R.string.loginingMob, msg.obj);
                LSUtils.i("MSG_LOGIN", text);

                System.out.println("---------------");

            }
            break;
            case MSG_AUTH_CANCEL: {
                LSUtils.i("MSG_AUTH_CANCEL", R.string.auth_cancel + (String) msg.obj);
                System.out.println("-------MSG_AUT实打实的H_CANCEL--------");
            }
            break;
            case MSG_AUTH_ERROR: {
                LSUtils.i("MSG_AUTH_ERROR", R.string.auth_error + (String) msg.obj);
                System.out.println("-------MSG_AUTH_ERROR--------");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                //Toast.makeText(this, R.string.auth_complete+(String)msg.obj, Toast.LENGTH_SHORT).show();
                LSUtils.i( "MSG_AUTH_COMPLETE",R.string.auth_complete+(String)msg.obj);
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
     * 第三方登录上传信息至服务器
     */
    private void regAndLogin() {
        final String avator_url = params.get("avator");
        OkHttpUtils//
                .get()//
                .url(avator_url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "userIcon.jpg")//
                {

                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(File file, int i) {
                        Log.e("path", "onResponse :" + file.getAbsolutePath());
                        uploadHead(file.getAbsolutePath());
                    }
                });
    }

    //第三方头像上传至阿里云存储并注册
    private void uploadHead(final String path) {


        UploadManager.getInstance().uploadImage(path);
        UploadManager.getInstance().setUpLoadListener(new UpLoadListener() {
            @Override
            public void upLoading(long currentSize, long totalSize) {

            }

            @Override
            public void upLoadSuccess(Object result, String uploadPath) {
                LSUtils.d("zzz", " uploadPath:" + uploadPath);
                params.put("avator", uploadPath);
                signUp();
            }

            @Override
            public void upLoadSuccess(Object result, String thumbnailPath, String uploadPath) {

                LSUtils.d("zzz", "thumbnailPath:" + thumbnailPath + "   uploadPath:" + uploadPath);

            }

            @Override
            public void upLoadError(String msg) {

            }
        });
    }

    private void signUp() {
        final String SIGNUP_URL = ((MyApplication)getApplication()).getApis().get("Host").toString()+
                ((MyApplication)getApplication()).getApis().get("WxUserLogin").toString();

        OkHttpUtils.post()
                .url(SIGNUP_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {
                        LSUtils.i("signup", s);
                        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                        intent.putExtra("username","微信登录");
                        startActivityForResult(intent, OTHER_LOGIN);

                    }
                });
    }
}
