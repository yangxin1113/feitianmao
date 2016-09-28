package cn.feitianmao.app.view.me;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.mob.tools.utils.UIHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.http.UpLoadListener;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.utils.StatusBarUtil;
import cn.feitianmao.app.utils.UploadManager;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.main.IndexActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class LoginActivity1 extends BaseFragmentActivity implements Handler.Callback,
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
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    private static final int MSG_REGISTER = 6;
    private int abc = 0;

    //登录
    private static final int USER_LOGIN = 0;
    private static final int OTHER_LOGIN = 1;
    private Handler handler;
    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        handler = new Handler(this);
    }

    @Override
    protected void setInitData() {
        StatusBarUtil.setStatusBarColor(LoginActivity1.this, R.color.white);//设置状态栏颜色
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
        switch (v.getId()) {
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.tv_right:
                Bundle bundle = new Bundle();
                bundle.putString("title", "注册");
                showItemActivity(bundle, RegisterActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.tv_lost_pwd:
                bundle = new Bundle();
                bundle.putString("title", "忘记密码");
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
        final String LOGIN_URL = ((MyApplication) getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("UserLogin").toString();
        if (TextUtils.isEmpty(params.get("username")) || TextUtils.isEmpty(params.get("password"))) {
            LSUtils.showToast(getApplicationContext(), "请输入用户名或密码！");
            return;
        }
        OkHttpUtils.post(LOGIN_URL)
                .params(params)
                .execute(new StringDialogCallback(LoginActivity1.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Intent intent = new Intent(LoginActivity1.this, IndexActivity.class);
                        //PreferencesUtils.putString(getApplicationContext(), "cookies", response.header("Set-Cookie"));
                        try {
                            JSONObject json = new JSONObject(s);
                            if (json.getBoolean("status") == true) {
                                LSUtils.showToast(getApplicationContext(), json.getString("alert"));
                                setUserInfo(json.getJSONObject("data").getJSONObject("userInfo"));
                                startActivityForResult(intent, USER_LOGIN);
                            } else {
                                LSUtils.showToast(getApplicationContext(), json.getString("alert"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    /**
     * 将用户信息到本地
     *
     * @param data
     */
    private void setUserInfo(JSONObject data) throws JSONException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> UserInfo = gson.fromJson(data.toString(), type);
        ((MyApplication) getApplication()).setUserInfo(UserInfo);
        LSUtils.i("UserInfo", (parse.isNull(((MyApplication) getApplication()).getUserInfo().get("name"))));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void authorize(Platform plat) {
        if (plat.isValid()) {
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
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        /*System.out.println("------User ID ---------" + platform.getDb().getUserId());
        System.out.println("------UserIcon ---------" + platform.getDb().getUserIcon());
        System.out.println("------Token ---------" + platform.getDb().getToken());
        System.out.println("------UserGender ---------" + platform.getDb().getUserGender());
        System.out.println("------TokenSecret ---------" + platform.getDb().getTokenSecret());
        System.out.println("------Secret ---------" + platform.getDb().getTokenSecret());
        System.out.println("------ExpiresIn ---------" + platform.getDb().getExpiresIn());
        System.out.println("------ExpiresTime ---------" + platform.getDb().getExpiresTime());
        System.out.println("------PlatformVersion ---------" + platform.getDb().getPlatformVersion());*/
        System.out.println("------Name ---------" + platform.getName());
        regAndLogin(params);

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
        switch (msg.what) {
            case MSG_REGISTER:
                Response response = (Response) msg.obj;
                //对response需要自己做解析
                String data = null;
                try {
                    data = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject json = new JSONObject(data);
                    if (json.getBoolean("status") == true) {
                        LSUtils.showToast(getApplicationContext(), json.getString("alert"));
                        setUserInfo(json.getJSONObject("data").getJSONObject("userInfo"));
                        Intent intent = new Intent(LoginActivity1.this, IndexActivity.class);
                        startActivityForResult(intent, USER_LOGIN);
                    } else {
                        LSUtils.showToast(getApplicationContext(), json.getString("alert"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*System.out.println("同步请求的数据：" + data.toString());
                Toast.makeText(OkHttpUtils.getContext(), "同步请求成功" + data, Toast.LENGTH_SHORT).show();*/
                break;
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
                LSUtils.i("MSG_AUTH_COMPLETE", R.string.auth_complete + (String) msg.obj);
                //Platform platform = (Platform)msg.obj;
                //System.out.println("res"+platform.getDb().getPlatformNname());

                System.out.println("--------MSG_AUTH_COMPLETE-------");

            }
            break;

        }
        return false;
    }


    /**
     * 输入框为空时,登录按钮不可点击
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ColorStateList csl = null;
            if (TextUtils.isEmpty(s.toString())) {
                bt_login.setBackgroundResource(R.color.unable_press_bg);
                bt_login.setTextColor(getResources().getColor(R.color.unable_press_text));
            } else {
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
    private void regAndLogin(final Map<String, String> params) {
        final String avator_url = params.get("avator");
        OkHttpUtils.get(avator_url)
                .tag(this)
                .execute(new FileCallback("head.jpg") {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        //handleResponse(file, call, response);
                        Log.e("path", "onResponse :" + file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }

                    @Override
                    public void onAfter(@Nullable File file, @Nullable Exception e) {
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
                LSUtils.d("zzz", " uploadPath:" + uploadPath.replace("http://flycat.oss-cn-hangzhou.aliyuncs.com/",""));
                params.put("avator", uploadPath.replace("http://flycat.oss-cn-hangzhou.aliyuncs.com/",""));
                signUp(params);
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

    private void signUp(final Map<String, String> params) {

        final String SIGNUP_URL = ((MyApplication) getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("WxUserLogin").toString();
        LSUtils.i("注册", params.get("avator").toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步会阻塞主线程，必须开线程
                    Response response = OkHttpUtils.post(SIGNUP_URL)//
                            .tag(this)//
                            .params(params)
                            .execute();  //不传callback即为同步请求

                    Message message = Message.obtain();
                    message.what = 6;
                    message.obj = response;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /*OkHttpUtils.post(SIGNUP_URL)
                .tag(this)
                .params(params)
                .execute(new StringDialogCallback(LoginActivity1.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LSUtils.i("signup", s);
                        //Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                        //LSUtils.showToast(getApplicationContext(),"微信登录");
                        //intent.putExtra("username","微信登录");
                        //startActivityForResult(intent, OTHER_LOGIN);
                        //LSUtils.i("zzz", response.header("HEAD_KEY_SET_COOKIE ").toString());
                    }
                });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(2);
    }
}
