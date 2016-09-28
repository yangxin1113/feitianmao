package cn.feitianmao.app.view.me;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuHuatiAdapter;
import cn.feitianmao.app.adapter.GuanzhuYonghuAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.base.BaseFragment1;
import cn.feitianmao.app.bean.HuatiData;
import cn.feitianmao.app.bean.YonghuData;
import cn.feitianmao.app.callback.GuanzhuHuatiClickListenner;
import cn.feitianmao.app.callback.GuanzhuYonghuClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.main.IndexActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class GuanzhuHuatiFragment extends BaseFragment1 {


    @BindView(R.id.rv_yonghu)
    RecyclerView rvYonghu;
    private List<HuatiData> huatiDatas = null;
    private GuanzhuHuatiAdapter guanzhuHuatiAdapter;


    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private int page = 1;
    boolean isLoading;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_yonghu);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void initEvent() {
        itemOnClickListenner();
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvYonghu.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTopicData();
        guanzhuHuatiAdapter = new GuanzhuHuatiAdapter(getContext(), huatiDatas);
        rvYonghu.setAdapter(guanzhuHuatiAdapter);

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        huaTi();
    }

    private void getTopicData() {
        huatiDatas = new ArrayList<HuatiData>();
        for (int i = 0; i < 10; i++) {
            HuatiData huatiData = new HuatiData();
            huatiData.setName("三金");
            huatiData.setId(i);
            huatiData.setIsGuanzhu(0);
            huatiData.setQianming("人生如逆旅，我亦是行人");
            huatiData.setGuanzhucount(i*3+111);
            huatiData.setQuestioncount(i*6+111);
            huatiDatas.add(huatiData);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }


    /**
     * 接口回调实现RecyclerView的item布局中每个控件的点击事件
     */
    private void itemOnClickListenner() {
        guanzhuHuatiAdapter.setGuanzhuHuatiClickListenner(new GuanzhuHuatiClickListenner() {

            @Override
            public void showHuati(View view, int position) {
                LSUtils.showToast(getContext(),"话题");
            }

            @Override
            public void showTopic(View view, int position) {
                LSUtils.showToast(getContext(),"topic");
            }

            @Override
            public void showGuanzhucount(View view, int position) {
                LSUtils.showToast(getContext(),"关注数");
            }

            @Override
            public void showWenticount(View view, int position) {
                LSUtils.showToast(getContext(),"问题数");
            }
        });
    }


    //
    private void huaTi() {
        final String HUATI_URL = ((MyApplication)getActivity().getApplication()).getApis().get("Host").toString()+
                ((MyApplication)getActivity().getApplication()).getApis().get("Personaltopic").toString();

        OkHttpUtils.post(HUATI_URL)
                .params("uid","me")
                .execute(new StringDialogCallback(getActivity()) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //Intent intent = new Intent(getActivity(), IndexActivity.class);
                        //intent.putExtra("userInfo",s);
                        //List<String> cookies=response.headers("Set-Cookie");
                        LSUtils.i("huati", s);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });



    }
}
