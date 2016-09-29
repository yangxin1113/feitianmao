package cn.feitianmao.app.view.tuijian;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.HotAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.HomeBean;
import cn.feitianmao.app.callback.HomeClickListenner;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.home.AnswerActivity;
import cn.feitianmao.app.view.home.HuatiDetailActivity;
import cn.feitianmao.app.view.home.OhtersActivity;
import cn.feitianmao.app.view.home.SearchActivity;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class TuijianWentiFragment extends BaseFragment {
    @BindView(R.id.rv_huati_detail)
    RecyclerView rv_huati_detail;

    private HomeBean homeDatas = null;
    private HotAdapter homeAdapter;
    private FullyLinearLayoutManager mLayoutManager;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_huati_detail);
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
                        homeDatas = json.fromJson(s, HomeBean.class);
                        homeAdapter = new HotAdapter(getContext(), homeDatas);
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


}
