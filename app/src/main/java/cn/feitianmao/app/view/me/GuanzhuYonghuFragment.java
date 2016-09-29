package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuWentiAdapter;
import cn.feitianmao.app.adapter.GuanzhuYonghuAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.base.BaseFragment1;
import cn.feitianmao.app.bean.Question;
import cn.feitianmao.app.bean.WentiData;
import cn.feitianmao.app.bean.YonghuData;
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;
import cn.feitianmao.app.callback.GuanzhuYonghuClickListenner;
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
public class GuanzhuYonghuFragment extends BaseFragment1 {


    @BindView(R.id.rv_yonghu)
    RecyclerView rvYonghu;
    private List<YonghuData> yonghuDatas = new ArrayList<YonghuData>();
    private GuanzhuYonghuAdapter guanzhuYonghuAdapter;

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
        //itemOnClickListenner();
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvYonghu.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        yongHu(1);

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
        guanzhuYonghuAdapter.setGuanzhuYonghuClickListenner(new GuanzhuYonghuClickListenner() {


            @Override
            public void showHead(View view, int position) {
                LSUtils.showToast(getContext(),yonghuDatas.get(position).getAvator());
            }

            @Override
            public void showNick(View view, int position) {
                LSUtils.showToast(getContext(),yonghuDatas.get(position).getName());
            }

            @Override
            public void showQianming(View view, int position) {
                LSUtils.showToast(getContext(),yonghuDatas.get(position).getSignature());
            }

            @Override
            public void showIsGuanzhu(View view, int position) {


            }
        });
    }


    //
    private void yongHu(int page) {
        final String YONGHU_URL = ((MyApplication) getActivity().getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getActivity().getApplication()).getApis().get("Personaluser").toString();

        OkHttpUtils.post(YONGHU_URL)
                .params("uid", "me")
                .params("page", String.valueOf(page))
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

                                if (yonghuDatas != null || yonghuDatas.size() == 0){
                                    try {
                                        yonghuDatas = gson.fromJson(array.toString(), new TypeToken<List<YonghuData>>(){}.getType());
                                    } catch (JsonSyntaxException e) {
                                        //e.printStackTrace();
                                        LSUtils.i("错误","gson解析错误");
                                    }
                                }else {
                                    List<YonghuData> more = gson.fromJson(array.toString(), new TypeToken<List<YonghuData>>(){}.getType());
                                    yonghuDatas.addAll(more);
                                }

                                if(guanzhuYonghuAdapter==null){
                                    guanzhuYonghuAdapter = new GuanzhuYonghuAdapter(getContext(), yonghuDatas);
                                    rvYonghu.setAdapter(guanzhuYonghuAdapter);
                                }else{
                                    guanzhuYonghuAdapter.notifyDataSetChanged();
                                }
                                rvYonghu.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                rvYonghu.setFocusable(false);
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
