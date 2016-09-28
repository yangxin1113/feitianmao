package cn.feitianmao.app.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.main.IndexActivity;
import cn.feitianmao.app.widget.MyTitleBar;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 我的成就
 * Created by Administrator on 2016/7/28 0028.
 */
public class ShezhiActivity extends BaseFragmentActivity {

    @BindView(R.id.mtb_title)
    MyTitleBar mtbTitle;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_account)
    RelativeLayout llAccount;
    @BindView(R.id.tv_tongzhi)
    TextView tvTongzhi;
    @BindView(R.id.ll_tongzhi)
    RelativeLayout llTongzhi;
    @BindView(R.id.tv_fankui)
    TextView tvFankui;
    @BindView(R.id.ll_fankui)
    RelativeLayout llFankui;
    @BindView(R.id.tv_us)
    TextView tvUs;
    @BindView(R.id.ll_us)
    RelativeLayout llUs;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.iv_you)
    ImageView ivYou;
    @BindView(R.id.ll_delete)
    RelativeLayout llDelete;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.tv_loginout)
    TextView tvLoginout;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_shezhi);
    }

    @Override
    protected void setInitData() {
        mtbTitle.setText("设置");
        mtbTitle.setLeftImage(R.drawable.icon_fanhui_w);
        mtbTitle.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
            }
        });
    }


    @Override
    protected void initEvent() {
        tvLoginout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loginout:
                loginOut();
                break;
        }
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

    //退出登录
    private void loginOut() {
        final String LOGINOUT_URL = ((MyApplication)getApplication()).getApis().get("Host").toString()+
                ((MyApplication)getApplication()).getApis().get("UserLogout").toString();
        String cookies = null;

        OkHttpUtils.post(LOGINOUT_URL)
                //.headers("cookies", PreferencesUtils.getString(getApplicationContext(), "Cookies"))
                .execute(new StringDialogCallback(ShezhiActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        LSUtils.i("loginout", s);
                        ((MyApplication) getApplication()).setUserInfo(null);
                        showItemActivity(IndexActivity.class);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }
}
