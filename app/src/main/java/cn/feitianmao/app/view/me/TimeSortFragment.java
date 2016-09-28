package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuYonghuAdapter;
import cn.feitianmao.app.adapter.MyAnswersAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.MyAnswerData;
import cn.feitianmao.app.bean.YonghuData;
import cn.feitianmao.app.callback.GuanzhuYonghuClickListenner;
import cn.feitianmao.app.callback.ItemClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.view.application.MyApplication;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 按时间排序
 * Created by Administrator on 2016/8/29 0029.
 */
public class TimeSortFragment extends BaseFragment {


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
        getData();
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
            myAnswerData.setTopicImg("http://avatar.csdn.net/2/2/D/1_chenguang79.jpg");
            myAnswerData.setTime(30);
            myAnswerDatas.add(myAnswerData);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }


    //用户信息
    private void getData() {
        final String ANSWERS_URL = ((MyApplication) getActivity().getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getActivity().getApplication()).getApis().get("UserAnswer").toString();

        OkHttpUtils.post(ANSWERS_URL)
                .headers("cookies", PreferencesUtils.getString(getActivity(), "Cookies"))
                .execute(new StringDialogCallback(getActivity()) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = new JSONObject(s);
                            LSUtils.i("UserInfo",s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

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


}
