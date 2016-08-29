package cn.feitianmao.app.view.home;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.utils.ViewPagerHelper;
import cn.feitianmao.app.widget.HomeScrollView;

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

    private List<View> views = null;
    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_home);
    }

    @Override
    protected void initEvent() {

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
}
