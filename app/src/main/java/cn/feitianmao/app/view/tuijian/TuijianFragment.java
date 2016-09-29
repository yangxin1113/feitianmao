package cn.feitianmao.app.view.tuijian;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.ViewPagerAdapter;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.view.me.GuanzhuHuatiFragment;
import cn.feitianmao.app.view.me.GuanzhuWentiFragment;
import cn.feitianmao.app.view.me.GuanzhuYonghuFragment;


/**
 * 我的关注
 * Created by Administrator on 2016/7/28 0028.
 */
public class TuijianFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_tuijian);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new WentiFragment(), "问题");
        adapter.addFragment(new TuijianHuatiFragment(), "话题");
        adapter.addFragment(new WentiFragment(), "达人榜");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void setInitData() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:

                break;

        }
    }


}
