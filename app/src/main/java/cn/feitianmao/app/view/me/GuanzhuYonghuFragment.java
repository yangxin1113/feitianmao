package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import cn.feitianmao.app.bean.Question;
import cn.feitianmao.app.bean.YonghuData;
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;
import cn.feitianmao.app.callback.GuanzhuYonghuClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.view.application.MyApplication;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class GuanzhuYonghuFragment extends BaseFragment {


    @BindView(R.id.rv_yonghu)
    RecyclerView rvYonghu;
    private List<YonghuData> yonghuDatas = null;
    private GuanzhuYonghuAdapter guanzhuYonghuAdapter;


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
        guanzhuYonghuAdapter = new GuanzhuYonghuAdapter(getContext(), yonghuDatas);
        rvYonghu.setAdapter(guanzhuYonghuAdapter);
        yongHu(1);
    }

    private void getTopicData() {
        yonghuDatas = new ArrayList<YonghuData>();
        for (int i = 0; i < 10; i++) {
            YonghuData yonghuData = new YonghuData();
            yonghuData.setName("三金");
            yonghuData.setId(i);
            yonghuData.setIsGuanzhu(0);
            yonghuData.setQianming("人生如逆旅，我亦是行人");
            yonghuDatas.add(yonghuData);
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
        guanzhuYonghuAdapter.setGuanzhuYonghuClickListenner(new GuanzhuYonghuClickListenner() {


            @Override
            public void showHead(View view, int position) {
                LSUtils.showToast(getContext(),"头像");
            }

            @Override
            public void showNick(View view, int position) {
                LSUtils.showToast(getContext(),"昵称");
            }

            @Override
            public void showQianming(View view, int position) {
                LSUtils.showToast(getContext(),"签名");
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
                        try {
                            JSONObject json = new JSONObject(s);
                            if(json.getBoolean("status")){
                                JSONArray array = json.getJSONArray("data");
                                if(array!=null || array.equals("")){
                                    LSUtils.i("zz",s);
                                }else {
                                    LSUtils.showToast(getContext(),json.getString("alert"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        LSUtils.i("huati", s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
