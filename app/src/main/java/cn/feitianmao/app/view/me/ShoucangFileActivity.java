package cn.feitianmao.app.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.HomeAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.bean.HomeBean;
import cn.feitianmao.app.callback.HomeClickListenner;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.utils.ViewPagerHelper;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.home.AnswerActivity;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.HomeScrollView;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class ShoucangFileActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_top)
    RelativeLayout ivTop;
    @BindView(R.id.rv_topic)
    RecyclerView rvTopic;
    @BindView(R.id.scroll_home)
    HomeScrollView scrollHome;
    @BindView(R.id.swipelayout)
    SwipeRefreshLayout swipelayout;
    private HomeBean homeDatas = null;
    private HomeAdapter homeAdapter;
    private FullyLinearLayoutManager mLayoutManager;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_shoucang_file);
    }

    @Override
    protected void setInitData() {
        getData();
        scrollHome.setSwipeRefreshLayout(swipelayout);
        swipelayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mLayoutManager = new FullyLinearLayoutManager(ShoucangFileActivity.this, VERTICAL, false);
        rvTopic.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void initEvent() {
        //RecycleView中item布局中每个控件的点击事件
        //itemOnClickListenner();
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
    }

    private void getData() {
        final String HOME_URL = ((MyApplication)getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("Question").toString();
        OkHttpUtils.post(HOME_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        GsonBuilder gb = new GsonBuilder();
                        gb.registerTypeAdapter(String.class, new StringConverter());
                        Gson json = gb.create();
                        homeDatas = json.fromJson(s, HomeBean.class);
                        homeAdapter = new HomeAdapter(ShoucangFileActivity.this, homeDatas);
                        rvTopic.addItemDecoration(new ListItemDecoration(ShoucangFileActivity.this, LinearLayoutManager.VERTICAL));
                        rvTopic.setAdapter(homeAdapter);
                        rvTopic.setFocusable(false);
                        rvTopic.setFocusable(false);
                        scrollHome.smoothScrollTo(0, 0);
                        itemOnClickListenner(); //item监听事件
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.iv_right:
                LSUtils.showToast(ShoucangFileActivity.this, "更多");
                break;

        }

    }


    /**
     * 接口回调实现RecyclerView的item布局中每个控件的点击事件
     */
    private void itemOnClickListenner() {
        homeAdapter.setHomeClickListenner(new HomeClickListenner() {
            @Override
            public void showTopic(View view, int position) {
                LSUtils.showToast(ShoucangFileActivity.this, "点击了我" + homeDatas.getData().get(position).getTopic());
            }

            @Override
            public void showQuestion(View view, int position) {
                //LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getQuestion());
                Intent i = new Intent(ShoucangFileActivity.this, AnswerActivity.class);
                startActivity(i);
            }

            @Override
            public void showAnswer(View view, int position) {
                //LSUtils.showToast(getContext(), "点击了我" + homeDatas.getData().get(position).getContent());
                Intent i = new Intent(ShoucangFileActivity.this, AnswerActivity.class);
                startActivity(i);
            }

            @Override
            public void showTopicImg(View view, int position) {
                LSUtils.showToast(ShoucangFileActivity.this, "点击了我图片");
            }

            @Override
            public void showhead(View view, int position) {
                LSUtils.showToast(ShoucangFileActivity.this, "点击了我头像");
            }

            @Override
            public void showUsername(View view, int position) {
                //LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getUsername());
            }

            @Override
            public void showAgreecount(View view, int position) {
                //LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getAgreecount());
            }

            @Override
            public void showTalkcount(View view, int position) {
                //LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getTalkcount());
            }
        });
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

}
