package cn.feitianmao.app.view.me;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.utils.RegulaUtils;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.main.IndexActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class RegisterActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.bt_next)
    Button bt_next;
    @BindView(R.id.iv_check)
    ImageView iv_check;
    @BindView(R.id.tv_disgree)
    TextView tv_disgree;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private String title;
    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_reg1);
        title = getIntent().getExtras().getString("title");
    }

    @Override
    protected void setInitData() {
        iv_check.setTag(false);
        tv_title.setText(title);
    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        ed_phone.addTextChangedListener(watcher);
        iv_check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;

            case R.id.bt_next:
                if(isnext()){

                    Bundle bundle = new Bundle();
                    bundle.putString("phone", ed_phone.getText().toString());

                    if(title.equals("注册")){
                        if(isUserExit()) {
                            showItemActivity(bundle, SetPwdActivity.class);
                        }
                    }
                    else{
                            showItemActivity(bundle,ForgetPwdActivity.class);

                    }
                }
                break;
            case R.id.iv_check:
                LSUtils.showToast(getApplicationContext(),"zzzz");
                if(iv_check.getTag().equals(false)){
                    iv_check.setImageResource(R.drawable.icon_tongyi_selected);
                    iv_check.setTag(true);
                }else {
                    iv_check.setImageResource(R.drawable.icon_tongyi_normal);
                    iv_check.setTag(false);
                }
                break;

        }
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


    private boolean isnext(){
        boolean isNext = true;
        if(!RegulaUtils.getInstance().isMobileNO(ed_phone.getText().toString())){
            LSUtils.showToast(getApplicationContext(), getResources().getString(R.string.input_phone_no_error_zh));
            isNext = false;
        }else if(iv_check.getTag().equals(false)){
            LSUtils.showToast(getApplicationContext(),getResources().getString(R.string.please_agree_agreement_zh_));
            isNext = false;
        }
        return isNext;
    }

    /**
     *  判断用户是否存在
     *
     * @return
     */
    private boolean isUserExit() {
        final boolean[] isExit = {false};
        final String LOGIN_URL = ((MyApplication) getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("VerifyPhone").toString();
        OkHttpUtils.post(LOGIN_URL)
                .params("phone", ed_phone.getText().toString())
                .execute(new StringDialogCallback(RegisterActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = new JSONObject(s);
                            if (json.getBoolean("status") == true) {
                                LSUtils.showToast(getApplicationContext(), json.getString("alert"));
                                isExit[0] = true;
                            } else {
                                LSUtils.showToast(getApplicationContext(), json.getString("alert"));
                                isExit[0] = false;
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
        return isExit[0];
    }




    /**
     * 输入内容不符合,下一步不可点击
     *
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!RegulaUtils.getInstance().isMobileNO(s.toString())){
                bt_next.setBackgroundResource(R.color.unable_press_bg);
                bt_next.setTextColor(getResources().getColor(R.color.unable_press_text));

            }else{
                bt_next.setBackgroundResource(R.drawable.bt_single);
                bt_next.setTextColor(getResources().getColor(R.color.white));

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

}
