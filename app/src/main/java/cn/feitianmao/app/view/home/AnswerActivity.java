package cn.feitianmao.app.view.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.utils.RegulaUtils;
import cn.feitianmao.app.utils.StatusBarUtil;
import cn.feitianmao.app.widget.CircleImageView;
import cn.feitianmao.app.widget.imgandtext.HtmlTextView;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class AnswerActivity extends BaseFragmentActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
    @BindView(R.id.ll_logined)
    LinearLayout llLogined;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tv_content)
    HtmlTextView tvContent;
    @BindView(R.id.iv_zantong)
    ImageView ivZantong;
    @BindView(R.id.tv_zantong)
    TextView tvZantong;
    @BindView(R.id.ll_zantong)
    LinearLayout llZantong;
    @BindView(R.id.iv_shoucang)
    ImageView ivShoucang;
    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;
    @BindView(R.id.ll_shoucang)
    LinearLayout llShoucang;
    @BindView(R.id.iv_fenxaing)
    ImageView ivFenxaing;
    @BindView(R.id.tv_fenxaing)
    TextView tvFenxaing;
    @BindView(R.id.ll_fenxaing)
    LinearLayout llFenxaing;
    @BindView(R.id.iv_pinglun)
    ImageView ivPinglun;
    @BindView(R.id.tv_pinglun)
    TextView tvPinglun;
    @BindView(R.id.ll_pinglun)
    LinearLayout llPinglun;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private RegulaUtils regulaUtils; //提取字符串中的数字

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_answer);
    }

    @Override
    protected void setInitData() {
        //StatusBarUtil.setStatusBarColor(AnswerActivity.this, R.color.white);//设置状态栏颜色
        //StatusBarUtil.StatusBarLightMode(AnswerActivity.this,3);
        regulaUtils = RegulaUtils.getInstance();
        llZantong.setTag(false);
        llShoucang.setTag(false);
        llFenxaing.setTag(false);
        llPinglun.setTag(false);

        String html = "下面是图片了 " +
                "<img src='http://www.qqpk.cn/Article/UploadFiles/201411/20141116135722282.jpg'/>" +
                "这也是图片" +
                " <img src='http://h.hiphotos.baidu.com/image/pic/item/d000baa1cd11728b2027e428cafcc3cec3fd2cb5.jpg'/>" +
                "还有一张"+
                " <img src='http://img.61gequ.com/allimg/2011-4/201142614314278502.jpg' />";

        String html1 = "下面是图片"+"<img src='" + R.drawable.lena + "'/>"+"哈哈";

        tvContent.setHtmlFromString(html, false);
    }

    @Override
    protected void initEvent() {
        ivLeft.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        tvNick.setOnClickListener(this);
        tvQianming.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llZantong.setOnClickListener(this);
        llShoucang.setOnClickListener(this);
        llFenxaing.setOnClickListener(this);
        llPinglun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.iv_head:
                break;
            case R.id.tv_nick:
                break;
            case R.id.tv_qianming:
                break;
            case R.id.ll_guanzhu:
                if(llGuanzhu.getTag() == true){
                    llGuanzhu.setTag(true);
                }else {
                    llGuanzhu.setTag(false);
                }
                break;
            case R.id.ll_zantong:
                if(llZantong.getTag() == false){
                    ivZantong.setImageDrawable(ContextCompat.getDrawable(AnswerActivity.this, R.drawable.icon_zantong_selected));
                    tvZantong.setTextColor(ContextCompat.getColor(AnswerActivity.this, R.color.text_color4));
                    tvZantong.setText("赞同("+(regulaUtils.getDigit(tvZantong.getText().toString())+1+"")+")");
                    llZantong.setTag(true);
                }else {
                    ivZantong.setImageDrawable(ContextCompat.getDrawable(AnswerActivity.this, R.drawable.icon_zantong));
                    tvZantong.setTextColor(ContextCompat.getColor(AnswerActivity.this, R.color.text_gray4));
                    tvZantong.setText("赞同("+(regulaUtils.getDigit(tvZantong.getText().toString())-1+"")+")");
                    llZantong.setTag(false);
                }
                break;
            case R.id.ll_shoucang:
                if(llShoucang.getTag() == false){
                    ivShoucang.setImageDrawable(ContextCompat.getDrawable(AnswerActivity.this, R.drawable.icon_shoucang_selected));
                    tvShoucang.setTextColor(ContextCompat.getColor(AnswerActivity.this, R.color.text_color4));
                    llShoucang.setTag(true);
                }else {
                    ivShoucang.setImageDrawable(ContextCompat.getDrawable(AnswerActivity.this, R.drawable.icon_shoucang1));
                    tvShoucang.setTextColor(ContextCompat.getColor(AnswerActivity.this, R.color.text_gray4));
                    llShoucang.setTag(false);
                }
                break;
            case R.id.ll_fenxaing:
                if(llFenxaing.getTag() == true){
                    llFenxaing.setTag(true);
                }else {
                    llFenxaing.setTag(false);
                }
                break;
            case R.id.ll_pinglun:
                if(llPinglun.getTag() == true){
                    llPinglun.setTag(true);
                }else {
                    llPinglun.setTag(false);
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

}
