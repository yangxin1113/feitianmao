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
