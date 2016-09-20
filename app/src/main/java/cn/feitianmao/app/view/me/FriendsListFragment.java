package cn.feitianmao.app.view.me;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuYonghuAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.YonghuData;
import cn.feitianmao.app.callback.GuanzhuYonghuClickListenner;
import cn.feitianmao.app.utils.LSUtils;

/**
 * 我关注的问题
 * Created by Administrator on 2016/8/29 0029.
 */
public class FriendsListFragment extends BaseFragment {


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
}
