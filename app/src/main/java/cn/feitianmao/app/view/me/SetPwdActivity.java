package cn.feitianmao.app.view.me;

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

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.utils.InputUtils;
import cn.feitianmao.app.utils.LSUtils;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class SetPwdActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.ed_code)
    EditText ed_code;
    @BindView(R.id.bt_code)
    TextView bt_code;
    @BindView(R.id.ed_pwd1)
    TextView ed_pwd1;
    @BindView(R.id.ed_pwd2)
    TextView ed_pwd2;
    @BindView(R.id.bt_submit)
    Button bt_submit;

    private String phone;

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_reg2);
        SMSSDK.initSDK(this, "155f27b5ec9f4", "854f43251eed598914b34b0cc7408e69");
        /*ed_code = (EditText) findViewById(R.id.ed_code);
        readSmsContent = new ReadSmsContent(new Handler(), this, ed_code);
        //注册短信内容监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, readSmsContent);*/

        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
    }

    @Override
    protected void setInitData() {


        //readSmsContent = new ReadSmsContent(new Handler(), this, ed_code);
        //注册短信内容监听
        //this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, readSmsContent);

        //getMsg();

        tv_phone.setText(phone);
    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        bt_code.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        ed_pwd2.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.bt_submit:
                /*if (isNext()){
                    utils.showToast(getApplicationContext(),"注册成功");
                }*/
                break;
            case R.id.bt_code:
                //getMsg();
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

    /**
     * 判断是否可进行提交
     *
     * */
    private boolean isNext(){
        boolean isNext = true;
        if(TextUtils.isEmpty(ed_code.getText())){
            LSUtils.showToast(getApplicationContext(), getResources().getString(R.string.input_verification_code_zh));
            isNext = false;
        }else if(TextUtils.isEmpty(ed_pwd1.getText().toString())){
            LSUtils.showToast(getApplicationContext(),getResources().getString(R.string.pwd_length_error_zh));
            isNext = false;
        }else if(TextUtils.isEmpty(ed_pwd2.getText().toString())){
            LSUtils.showToast(getApplicationContext(),getResources().getString(R.string.please_confirm_pwd_zh));
            isNext = false;
        }else if(!ed_pwd1.getText().toString().equals(ed_pwd2.getText().toString())){
            LSUtils.showToast(getApplicationContext(),getResources().getString(R.string.input_tow_pwd_error_zh));
            isNext = false;
        }
        return isNext;
    }


    /**
     * 输入内容不符合,下一步不可点击
     *
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!InputUtils.getInstance().isMobileNO(s.toString())){
                bt_submit.setBackgroundResource(R.color.unable_press_bg);
                bt_submit.setTextColor(getResources().getColor(R.color.unable_press_text));
            }else{
                bt_submit.setBackgroundResource(R.drawable.bt_single);
                bt_submit.setTextColor(getResources().getColor(R.color.white));
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
