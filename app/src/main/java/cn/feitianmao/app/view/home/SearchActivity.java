package cn.feitianmao.app.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.ViewPagerAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.view.me.GuanzhuHuatiFragment;
import cn.feitianmao.app.view.me.GuanzhuWentiFragment;
import cn.feitianmao.app.view.me.GuanzhuYonghuFragment;

import static android.widget.TextView.*;


/**
 * 我的关注
 * Created by Administrator on 2016/7/28 0028.
 */
public class SearchActivity extends BaseFragmentActivity implements OnEditorActionListener{

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_search);

    }

    @Override
    protected void setInitData() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GuanzhuWentiFragment(), "问题");
        adapter.addFragment(new GuanzhuHuatiFragment(), "话题");
        adapter.addFragment(new GuanzhuYonghuFragment(), "用户");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        tvRight.setOnClickListener(this);
        etSearch.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) etSearch.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    SearchActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }



}
