package cn.feitianmao.app.view.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.view.me.FriendsActivity;
import cn.feitianmao.app.view.me.MyAnswersActivity;
import cn.feitianmao.app.view.me.MyTiwenActivity;
import cn.feitianmao.app.widget.CircleImageView;


/**
 * 我的成就
 * Created by Administrator on 2016/7/28 0028.
 */
public class OhtersActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_top)
    RelativeLayout ivTop;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.ll_qianming)
    LinearLayout llQianming;
    @BindView(R.id.tv_friends)
    TextView tvFriends;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.ll_friends)
    LinearLayout llFriends;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.ll_sixin)
    LinearLayout llSixin;
    @BindView(R.id.ll_caozuo)
    LinearLayout llCaozuo;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.tv_zantong)
    TextView tvZantong;
    @BindView(R.id.ll_zantong)
    LinearLayout llZantong;
    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;
    @BindView(R.id.ll_shoucang)
    LinearLayout llShoucang;
    @BindView(R.id.tv_fenxaing)
    TextView tvFenxaing;
    @BindView(R.id.ll_fenxiang)
    LinearLayout llFenxiang;
    @BindView(R.id.ll_lan)
    LinearLayout llLan;
    @BindView(R.id.tv_dongtai)
    TextView tvDongtai;
    @BindView(R.id.tv_dongtaicount)
    TextView tvDongtaicount;
    @BindView(R.id.iv_dongtai_jiantou)
    ImageView ivDongtaiJiantou;
    @BindView(R.id.ll_dongtai)
    RelativeLayout llDongtai;
    @BindView(R.id.tv_huida)
    TextView tvHuida;
    @BindView(R.id.tv_huidacount)
    TextView tvHuidacount;
    @BindView(R.id.iv_huida_jiantou)
    ImageView ivHuidaJiantou;
    @BindView(R.id.ll_huida)
    RelativeLayout llHuida;
    @BindView(R.id.tv_tiwen)
    TextView tvTiwen;
    @BindView(R.id.tv_tiwencount)
    TextView tvTiwencount;
    @BindView(R.id.iv_tiwen_jiantou)
    ImageView ivTiwenJiantou;
    @BindView(R.id.ll_tiwen)
    RelativeLayout llTiwen;
    @BindView(R.id.tv_huati)
    TextView tvHuati;
    @BindView(R.id.tv_huaticount)
    TextView tvHuaticount;
    @BindView(R.id.iv_huati_jiantou)
    ImageView ivHuatiJiantou;
    @BindView(R.id.ll_huati)
    RelativeLayout llHuati;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_others);

    }

    @Override
    protected void setInitData() {

    }

    @Override
    protected void initEvent() {
        iv_left.setOnClickListener(this);
        ivHead.setOnClickListener(this);

        llZantong.setOnClickListener(this);
        llShoucang.setOnClickListener(this);
        llFenxiang.setOnClickListener(this);
        llDongtai.setOnClickListener(this);
        llHuida.setOnClickListener(this);
        llTiwen.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.iv_head:
                showItemActivity(FriendsActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_account:
                showItemActivity(FriendsActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_zantong:
                showItemActivity(FriendsActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_shoucang:
                showItemActivity(FriendsActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_fenxaing:
                showItemActivity(FriendsActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_dongtai:
                showItemActivity(FriendsActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_huida:
                showItemActivity(MyAnswersActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.ll_tiwen:
                showItemActivity(MyTiwenActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
