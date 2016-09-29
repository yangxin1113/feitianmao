package cn.feitianmao.app.view.tuijian;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
public class WentiFragment extends BaseFragment {


    @BindView(R.id.rv_yonghu)
    RecyclerView rvYonghu;
    private List<HuatiData> huatiDatas = null;
    private GuanzhuHuatiAdapter guanzhuHuatiAdapter;


    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_yonghu);
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

    private void getTopicData() {
        huatiDatas = new ArrayList<HuatiData>();
        for (int i = 0; i < 10; i++) {
            HuatiData huatiData = new HuatiData();

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
}
