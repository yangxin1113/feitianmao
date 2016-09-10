package cn.feitianmao.app.view.me;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.http.MyCountTimer;
import cn.feitianmao.app.http.ReadSmsContent;
import cn.feitianmao.app.utils.RegulaUtils;
import cn.feitianmao.app.utils.LSUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ForgetPwdActivity extends BaseFragmentActivity {

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
    private ReadSmsContent readSmsContent;
    private MyCountTimer count;

    private static final int CODE_ING = 1;   //已发送，倒计时
    private static final int CODE_REPEAT = 2;  //重新发送
    private static final int SMSDDK_HANDLER = 3;  //短信回调
    private int TIME = 60;//倒计时60s
    private EventHandler eventHandler;

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_reg2);
        SMSSDK.initSDK(this, "155f27b5ec9f4", "854f43251eed598914b34b0cc7408e69");
        /*ed_code = (EditText) findViewById(R.id.ed_code);
        readSmsContent = new ReadSmsContent(new Handler(), this, ed_code);
        //注册短信内容监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, readSmsContent);*/

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = SMSDDK_HANDLER;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

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
                getMsg();
                break;

        }
    }

    //获取验证码
    private void getMsg() {
        count = new MyCountTimer(bt_code,getResources().getColor(R.color.text_gray),getResources().getColor(R.color.text_gray2));
        count.start();
        cn.smssdk.SMSSDK.getVerificationCode("86", phone);

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
            if(!RegulaUtils.getInstance().isMobileNO(s.toString())){
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


    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CODE_ING://已发送,倒计时
                    bt_code.setText("重新发送("+--TIME+"s)");
                    break;
                case CODE_REPEAT://重新发送
                    bt_code.setText("获取验证码");
                    bt_code.setClickable(true);
                    break;
                case SMSDDK_HANDLER:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    //回调完成
                    if (result == SMSSDK.RESULT_COMPLETE)
                    {
                        //验证码验证成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                        {
                            Toast.makeText(ForgetPwdActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                            //register();
                        }
                        //已发送验证码
                        else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
                        {
                            Toast.makeText(getApplicationContext(), "验证码已经发送",
                                    Toast.LENGTH_SHORT).show();
                        } else
                        {
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    if(result==SMSSDK.RESULT_ERROR)
                    {
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            //do something
                        }
                    }
                    break;
               /* case R.id.register_status:
                    String result_code = msg.getData().getString("result").toString();
                    if("1".equals(result_code))
                    {
                        Toast.makeText(SetPwdActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityMessageRegister.this,LoginActivity.class);
                        intent.putExtra("userPhone", userPhone);
                        ActivityMessageRegister.this.setResult(RESULE_CODE, intent);
                        //startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(ActivityMessageRegister.this, "注册失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.check_phone_exist://手机号是否已存在
                    String result_code_2 = msg.getData().getString("result").toString();
                    if("1".equals(result_code_2))
                    {
                        errPhoneText.setText("手机号码已经注册，请换用其他号码");
                        resultMap.put("phone", false);
                    }
                    else
                    {
                        errPhoneText.setText("");
                        resultMap.put("phone", true);
                    }
                    break;*/
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
