package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuWentiAdapter;
import cn.feitianmao.app.adapter.HomeAdapter1;
import cn.feitianmao.app.base.BaseFragment;

import cn.feitianmao.app.base.BaseFragment1;
import cn.feitianmao.app.bean.HomeData;
import cn.feitianmao.app.bean.Question;
import cn.feitianmao.app.bean.WentiData;
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class GuanzhuWentiFragment extends BaseFragment1 {

    @BindView(R.id.rv_wenti)
    RecyclerView rvWenti;
    private List<WentiData> questionDatas = new ArrayList<WentiData>();
    private GuanzhuWentiAdapter guanzhuWentiAdapter;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private int page = 1;
    boolean isLoading;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_wenti);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
       rvWenti.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*guanzhuWentiAdapter = new GuanzhuWentiAdapter(getContext(), questionDatas);
        rvWenti.setAdapter(guanzhuWentiAdapter);*/
        //wenTi(1);

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        wenTi(1);
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
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getTitle());
            }

            @Override
            public void showAnswer(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getAnswer());
            }

            @Override
            public void showGuanzhu(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getConcern());
            }
        });
    }


    //
    private void wenTi(int page) {
        final String WEN_URL = ((MyApplication) getActivity().getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getActivity().getApplication()).getApis().get("Personalquestion").toString();

        OkHttpUtils.post(WEN_URL)
                .params("uid","me")
                .params("page",String.valueOf(page))
                .execute(new StringDialogCallback(getActivity()) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject json = null;
                        JSONArray array = null;
                        try {
                            json = new JSONObject(s);
                            if(json.getBoolean("status")){
                                GsonBuilder gb = new GsonBuilder();
                                gb.registerTypeAdapter(String.class, new StringConverter());
                                Gson gson = gb.create();

                                    json = new JSONObject(s);
                                    array = json.getJSONArray("data");

                                if (questionDatas != null || questionDatas.size() == 0){
                                    try {
                                        questionDatas = gson.fromJson(array.toString(), new TypeToken<List<WentiData>>(){}.getType());
                                    } catch (JsonSyntaxException e) {
                                        //e.printStackTrace();
                                        LSUtils.i("错误","gson解析错误");
                                    }
                                }else {
                                    List<WentiData> more = gson.fromJson(array.toString(), new TypeToken<List<WentiData>>(){}.getType());
                                    questionDatas.addAll(more);
                                }

                                if(guanzhuWentiAdapter==null){
                                    guanzhuWentiAdapter = new GuanzhuWentiAdapter(getContext(), questionDatas);
                                    rvWenti.setAdapter(guanzhuWentiAdapter);
                                }else{
                                    guanzhuWentiAdapter.notifyDataSetChanged();
                                }
                                rvWenti.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                rvWenti.setFocusable(false);
                            }else {
                                LSUtils.showToast(getContext(),json.getString("alert"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        itemOnClickListenner();

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

}
