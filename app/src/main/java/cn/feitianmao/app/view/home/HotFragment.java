package cn.feitianmao.app.view.home;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuHuatiAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.HuatiData;
import cn.feitianmao.app.callback.GuanzhuHuatiClickListenner;
import cn.feitianmao.app.utils.LSUtils;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class HotFragment extends BaseFragment {


    @BindView(R.id.rv_huati_detail)
    RecyclerView rvHuatiDetail;
    private List<HuatiData> huatiDatas = null;
    private GuanzhuHuatiAdapter guanzhuHuatiAdapter;


    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_huati_detail);
    }

    @Override
    protected void initEvent() {
        itemOnClickListenner();
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvHuatiDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTopicData();
        guanzhuHuatiAdapter = new GuanzhuHuatiAdapter(getContext(), huatiDatas);
        rvHuatiDetail.setAdapter(guanzhuHuatiAdapter);

    }

    private void getTopicData() {
        huatiDatas = new ArrayList<HuatiData>();
        for (int i = 0; i < 10; i++) {
            HuatiData huatiData = new HuatiData();
            huatiData.setName("三金");
            huatiData.setId(i);
            huatiData.setIsGuanzhu(0);
            huatiData.setQianming("人生如逆旅，我亦是行人");
            huatiData.setGuanzhucount(i * 3 + 111);
            huatiData.setQuestioncount(i * 6 + 111);
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
                LSUtils.showToast(getContext(), "话题");
            }

            @Override
            public void showTopic(View view, int position) {
                LSUtils.showToast(getContext(), "topic");
            }

            @Override
            public void showGuanzhucount(View view, int position) {
                LSUtils.showToast(getContext(), "关注数");
            }

            @Override
            public void showWenticount(View view, int position) {
                LSUtils.showToast(getContext(), "问题数");
            }
        });
    }


}
