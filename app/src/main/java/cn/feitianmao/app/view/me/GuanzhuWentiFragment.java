package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuWentiAdapter;
import cn.feitianmao.app.base.BaseFragment;

import cn.feitianmao.app.bean.Question;
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;
import cn.feitianmao.app.utils.LSUtils;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class GuanzhuWentiFragment extends BaseFragment {

    @BindView(R.id.rv_wenti)
    RecyclerView rvWenti;
    private List<Question> questionDatas = null;
    private GuanzhuWentiAdapter guanzhuWentiAdapter;


    @Override
    protected void init() {
        setLayoutRes(R.layout.activity_huati_detail);
    }

    @Override
    protected void initEvent() {
        itemOnClickListenner();
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
       rvWenti.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTopicData();
        guanzhuWentiAdapter = new GuanzhuWentiAdapter(getContext(), questionDatas);
        rvWenti.setAdapter(guanzhuWentiAdapter);

    }

    private void getTopicData() {
        questionDatas = new ArrayList<Question>();
        for(int i=0; i<10; i++){
            Question question = new Question();
            question.setQuestion("的发生地方的三个地方公司的");
            question.setId(i);
            question.setGuanzhucount(i*2+20);
            question.setAnswercount(i*1+100);
            questionDatas.add(question);
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
        guanzhuWentiAdapter.setGuzhuWentiClickListenner(new GuanzhuWentiClickListenner() {

            @Override
            public void showQuestion(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getQuestion());
            }

            @Override
            public void showAnswer(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getAnswercount());
            }

            @Override
            public void showGuanzhu(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getGuanzhucount());
            }
        });
    }


}
