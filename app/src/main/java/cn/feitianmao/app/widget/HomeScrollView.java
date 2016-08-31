package cn.feitianmao.app.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class HomeScrollView extends ScrollView {

    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t != 0) {

            swipeRefreshLayout.setEnabled(false);
        } else {

            swipeRefreshLayout.setEnabled(true);
        }
    }

}