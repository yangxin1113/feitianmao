package cn.feitianmao.app.view.tuijian;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import cn.feitianmao.app.adapter.GuanzhuHuatiAdapter;
import cn.feitianmao.app.base.BaseFragment1;
import cn.feitianmao.app.bean.HuatiData;
import cn.feitianmao.app.callback.GuanzhuHuatiClickListenner;
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
public class TuijianHuatiFragment extends BaseFragment1 {


    @BindView(R.id.rv_huati)
    RecyclerView rvHuati;
    private List<HuatiData> huatiDatas = new ArrayList<HuatiData>();
    private GuanzhuHuatiAdapter guanzhuHuatiAdapter;


    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private int page = 1;
    boolean isLoading;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_huati);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void initEvent() {
        //itemOnClickListenner();
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvHuati.setLayoutManager(new LinearLayoutManager(getActivity()));

       /* guanzhuHuatiAdapter = new GuanzhuHuatiAdapter(getContext(), huatiDatas);
        rvHuati.setAdapter(guanzhuHuatiAdapter);*/

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        huaTi();
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
                ((MyApplication)getActivity().getApplication()).getApis().get("RecommendTopic").toString();

        OkHttpUtils.get(HUATI_URL)
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

                                if (huatiDatas != null || huatiDatas.size() == 0){
                                    try {
                                        huatiDatas = gson.fromJson(array.toString(), new TypeToken<List<HuatiData>>(){}.getType());
                                    } catch (JsonSyntaxException e) {
                                        //e.printStackTrace();
                                        LSUtils.i("错误","gson解析错误");
                                    }
                                }else {
                                    List<HuatiData> more = gson.fromJson(array.toString(), new TypeToken<List<HuatiData>>(){}.getType());
                                    huatiDatas.addAll(more);
                                }

                                if(guanzhuHuatiAdapter==null){
                                    guanzhuHuatiAdapter = new GuanzhuHuatiAdapter(getContext(), huatiDatas);
                                    rvHuati.setAdapter(guanzhuHuatiAdapter);
                                }else{
                                    guanzhuHuatiAdapter.notifyDataSetChanged();
                                }
                                rvHuati.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                rvHuati.setFocusable(false);
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
