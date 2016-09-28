package cn.feitianmao.app.view.home;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuHuatiAdapter;
import cn.feitianmao.app.adapter.HomeAdapter;

import cn.feitianmao.app.adapter.NewAdapter;
import cn.feitianmao.app.base.BaseFragment1;
import cn.feitianmao.app.bean.HomeBean;
import cn.feitianmao.app.bean.HuatiData;
import cn.feitianmao.app.callback.GuanzhuHuatiClickListenner;
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
 * 话题详情 最新
 * Created by Administrator on 2016/8/29 0029.
 */
public class NewFragment extends BaseFragment1 {

    @BindView(R.id.rv_huati_detail)
    RecyclerView rv_huati_detail;

    private HomeBean homeDatas = null;
    private NewAdapter homeAdapter;
    private FullyLinearLayoutManager mLayoutManager;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_huati_detail);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void setInitData() {

        getData();


        mLayoutManager = new FullyLinearLayoutManager(getActivity(), VERTICAL, false);
        rv_huati_detail.setLayoutManager(mLayoutManager);

    }


    @Override
    protected void initEvent() {
        //RecycleView中item布局中每个控件的点击事件
        //itemOnClickListenner();
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
                        homeAdapter = new NewAdapter(getContext(), homeDatas);
                        rv_huati_detail.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                        rv_huati_detail.setAdapter(homeAdapter);
                        rv_huati_detail.setFocusable(false);

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
     * 接口回调实现RecyclerView的item布局中每个控件的点击事件
     */
    private void itemOnClickListenner() {
        homeAdapter.setHomeClickListenner(new HomeClickListenner() {
            @Override
            public void showTopic(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + homeDatas.getData().get(position).getTopic());
                Intent i = new Intent(getActivity(), HuatiDetailActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
