package cn.feitianmao.app.view.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.ViewPagerAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.view.me.GuanzhuHuatiFragment;
import cn.feitianmao.app.view.me.GuanzhuWentiFragment;
import cn.feitianmao.app.view.me.GuanzhuYonghuFragment;


/**
 * 我的关注
 * Created by Administrator on 2016/7/28 0028.
 */
public class HuatiDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivleft;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_huati_detail);

    }

    @Override
    protected void setInitData() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HotFragment(), "最热");
        adapter.addFragment(new NewFragment(), "最新");
        adapter.addFragment(new HotFragment(), "待回答");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        ivleft.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
