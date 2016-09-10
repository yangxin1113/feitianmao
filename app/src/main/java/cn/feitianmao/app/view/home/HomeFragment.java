package cn.feitianmao.app.view.home;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import cn.feitianmao.app.adapter.HomeClickListenner;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.bean.HomeData;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.ViewPagerHelper;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.HomeScrollView;
import cn.feitianmao.app.widget.ListItemDecoration;
import cn.feitianmao.app.widget.RecycleViewDivider;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.vp_guide)
    public ViewPager mViewPager;
    @BindView(R.id.dots_parent)
    public LinearLayout viewPoints;
    @BindView(R.id.scroll_home)
    HomeScrollView scrollHome;
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
        setLayoutRes(R.layout.fragment_home);
    }

    @Override
    protected void initEvent() {
        //RecycleView中item布局中每个控件的点击事件
        itemOnClickListenner();


    }



    @Override
    protected void setInitData() {
        setViewPager();

        scrollHome.setSwipeRefreshLayout(swipelayout);
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
        rv_topic.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rv_topic.setAdapter(homeAdapter);
        rv_topic.setFocusable(false);
        scrollHome.smoothScrollTo(0, 0);

    }

    private void getTopicData() {
        homeDatas = new ArrayList<HomeData>();
        for (int i=1; i<=10; i++){
            HomeData homeData= new HomeData();
            homeData.setId(i);
            homeData.setTopic("哈哈哈" + i);
            homeData.setQuestion("我有一个问题"+i);
            homeData.setAnswer("丰塞卡符合双方大方大方卡拉是风景哦撒旦法度搜好丰盛的回佛山喀范德萨发松岛枫松岛枫发士大发生地方撒旦飞洒地方撒的发生打发士大夫撒的发生的"+i);
            homeData.setTopicImg("http://img3.imgtn.bdimg.com/it/u=1245246407,2475689242&fm=21&gp=0.jpg");
            homeData.setHeadImg("http://img5.imgtn.bdimg.com/it/u=636001998,396338405&fm=21&gp=0.jpg");
            homeData.setUsername("我是哈哈" + i);
            homeData.setAgreecount("12"+i+"");
            homeData.setTalkcount("25"+i+"");
            homeDatas.add(homeData);
        }

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化数据
     */
    private void setViewPager() {
        views = new ArrayList<View>();

        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_first, null);
        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_second, null);
        View view3 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_third, null);
        View view4 = LayoutInflater.from(getActivity()).inflate(R.layout.banner_four, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showItemActivity(IndexActivity.class);
            }
        });

        new ViewPagerHelper(false, mViewPager, views, viewPoints, R.drawable.page_indicator_focused, R.drawable.page_indicator_unfocused);
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


    /**
     * 接口回调实现RecyclerView的item布局中每个控件的点击事件
     */
    private void itemOnClickListenner() {
        homeAdapter.setHomeClickListenner(new HomeClickListenner() {
            @Override
            public void showTopic(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getTopic());
            }

            @Override
            public void showQuestion(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getQuestion());
                Intent i = new Intent(getActivity(), AnswerActivity.class);
                startActivity(i);
            }

            @Override
            public void showAnswer(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getAnswer());
                Intent i = new Intent(getActivity(), AnswerActivity.class);
                startActivity(i);
            }

            @Override
            public void showTopicImg(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我图片");
            }

            @Override
            public void showhead(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我头像");
            }

            @Override
            public void showUsername(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getUsername());
            }

            @Override
            public void showAgreecount(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getAgreecount());
            }

            @Override
            public void showTalkcount(View view, int position) {
                LSUtils.showToast(getContext(),"点击了我"+ homeDatas.get(position).getTalkcount());
            }
        });
    }
}
