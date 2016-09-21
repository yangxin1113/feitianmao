package cn.feitianmao.app.view.me;


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
import cn.feitianmao.app.adapter.MyAnswersAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.MyAnswerData;
import cn.feitianmao.app.callback.ItemClickListenner;
import cn.feitianmao.app.utils.LSUtils;

/**
 * 按时间排序
 * Created by Administrator on 2016/8/29 0029.
 */
public class ZantongSortFragment extends BaseFragment {


    @BindView(R.id.rv_answer)
    RecyclerView rvAnswer;
    private List<MyAnswerData> myAnswerDatas = null;
    private MyAnswersAdapter myAnswersAdapter;


    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_answers);
    }

    @Override
    protected void initEvent() {
        itemOnClickListenner();
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvAnswer.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTopicData();
        myAnswersAdapter = new MyAnswersAdapter(getContext(), myAnswerDatas);
        rvAnswer.setAdapter(myAnswersAdapter);

    }

    private void getTopicData() {
        myAnswerDatas = new ArrayList<MyAnswerData>();
        for (int i = 0; i < 10; i++) {
            MyAnswerData myAnswerData = new MyAnswerData();
            myAnswerData.setHeadImg("https://flycat.oss-cn-hangzhou.aliyuncs.com/CDN/image/201609191706262413.jpg");
            myAnswerData.setId(i);
            myAnswerData.setAnswer("的发生地方能卡死机都很发达生来就发生的恢复健康哈萨克发动机灵魂的伤口和甲方承诺的市场将肯定舒服啥地方了" + i);
            myAnswerData.setQuestion("人生如逆旅，我亦是行人" + i);
            myAnswerData.setAgreecount(2 * i + 212);
            myAnswerData.setTalkcount(3 * i + 3 * i);
            myAnswerData.setTopicImg("https://flycat.oss-cn-hangzhou.aliyuncs.com/CDN/image/201609191706262413.jpg");
            myAnswerData.setTime(30);
            myAnswerDatas.add(myAnswerData);
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
        myAnswersAdapter.setItemClickListenner(new ItemClickListenner() {

            @Override
            public void onItemClick(View view, int position) {
                LSUtils.showToast(getContext(), "点击");
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LSUtils.showToast(getContext(), "长按");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
