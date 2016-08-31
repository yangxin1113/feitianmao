package cn.feitianmao.app.view.tuijian;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.HomeAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.HomeData;
import cn.feitianmao.app.utils.ViewPagerHelper;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.HomeScrollView;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class TuijainFragment extends BaseFragment {



    @BindView(R.id.swipelayout)
    SwipeRefreshLayout swipelayout;
    @BindView(R.id.rv_topic)
    RecyclerView rv_topic;


    private List<View> views = null;
    private List<HomeData> homeDatas = null;
    private HomeAdapter homeAdapter;
    private FullyLinearLayoutManager mLayoutManager;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_tuijian);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void setInitData() {


        //scrollHome.setSwipeRefreshLayout(swipelayout);
        swipelayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        mLayoutManager = new FullyLinearLayoutManager(getActivity(),VERTICAL,false);
        rv_topic.setLayoutManager(mLayoutManager);

        getTopicData();
        homeAdapter = new HomeAdapter(getContext(), homeDatas);
        rv_topic.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

    }

    private void getTopicData() {
        homeDatas = new ArrayList<HomeData>();
        for (int i=1; i<=10; i++){
            HomeData homeData= new HomeData();
            homeData.setId(i);
            homeData.setTopic("我有一个问题"+i);
            homeData.setAnswer("我来回答"+i);
            homeData.setTopicImg("http://img3.imgtn.bdimg.com/it/u=1245246407,2475689242&fm=21&gp=0.jpg");
            homeData.setHeadImg("https://flycat.oss-cn-hangzhou.aliyuncs.com/CDN/image/201608171258258888.jpg");
            homeData.setUsername("我是哈哈" + i);
            homeData.setAgreecount("12"+i+"");
            homeData.setTalkcount("25"+i+"");
            homeDatas.add(homeData);
        }

    }

    @Override
    public void onClick(View v) {

    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //fragment可见时执行加载数据或者进度条等
            setInitData();
        } else {
            //不可见时不执行操作
        }
    }
}
