package cn.feitianmao.app.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.HomeAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.base.BaseFragment1;
import cn.feitianmao.app.bean.HomeBean;
import cn.feitianmao.app.callback.HomeClickListenner;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.utils.ViewPagerHelper;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.HomeScrollView;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class HomeFragment extends BaseFragment1 {

    @BindView(R.id.vp_guide)
    public ViewPager mViewPager;
    @BindView(R.id.dots_parent)
    public LinearLayout viewPoints;
    @BindView(R.id.scroll_home)
    HomeScrollView scrollHome;
    @BindView(R.id.swipelayout)
    SwipeRefreshLayout swipelayout;
    @BindView(R.id.rv_topic)
    RecyclerView rv_topic;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_saerch)
    LinearLayout llSaerch;

    private List<View> views = null;
    private HomeBean homeDatas = null;
    private HomeAdapter homeAdapter;
    private FullyLinearLayoutManager mLayoutManager;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_home);
        isPrepared = true;
        lazyLoad();
    }



    @Override
    protected void setInitData() {
        ivRight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.select_xinxi0));
        setViewPager();
        getData();
        scrollHome.setSwipeRefreshLayout(swipelayout);
        swipelayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        mLayoutManager = new FullyLinearLayoutManager(getActivity(), VERTICAL, false);
        rv_topic.setLayoutManager(mLayoutManager);

    }


    @Override
    protected void initEvent() {
        //RecycleView中item布局中每个控件的点击事件
        //itemOnClickListenner();
        llSaerch.setOnClickListener(this);
    }

    private void getData() {
        final String HOME_URL = ((MyApplication) getActivity().getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getActivity().getApplication()).getApis().get("Question").toString();
        OkHttpUtils.post(HOME_URL)
                //.addCookies(OkHttpUtils.getInstance().getCookieJar().getCookieStore().getAllCookie())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        GsonBuilder gb = new GsonBuilder();
                        gb.registerTypeAdapter(String.class, new StringConverter());
                        Gson json = gb.create();
                        homeDatas = json.fromJson(s,HomeBean.class);
                        homeAdapter = new HomeAdapter(getContext(), homeDatas);
                        rv_topic.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                        rv_topic.setAdapter(homeAdapter);
                        rv_topic.setFocusable(false);
                        scrollHome.smoothScrollTo(0, 0);
                        itemOnClickListenner(); //item监听事件
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_saerch:
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void setViewPager() {
        views = new ArrayList<View>();

        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_first, null);
        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_second, null);
        View view3 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_third, null);
        View view4 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_four, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showItemActivity(IndexActivity.class);
            }
        });

        new ViewPagerHelper(false, mViewPager, views, viewPoints, R.drawable.page_indicator_focused, R.drawable.page_indicator_unfocused);
    }



    /**
     * 接口回调实现RecyclerView的item布局中每个控件的点击事件
     */
    private void itemOnClickListenner() {
        homeAdapter.setHomeClickListenner(new HomeClickListenner() {
            @Override
            public void showTopic(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + homeDatas.getData().get(position).getTopic());
            }

            @Override
            public void showQuestion(View view, int position) {
                //LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getQuestion());
                Intent i = new Intent(getActivity(), AnswerActivity.class);
                startActivity(i);
            }

            @Override
            public void showAnswer(View view, int position) {
                //LSUtils.showToast(getContext(), "点击了我" + homeDatas.getData().get(position).getContent());
                Intent i = new Intent(getActivity(), AnswerActivity.class);
                startActivity(i);
            }

            @Override
            public void showTopicImg(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我图片");
            }

            @Override
            public void showhead(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我头像");
                Intent i = new Intent(getActivity(), OhtersActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        setInitData();
    }
}
