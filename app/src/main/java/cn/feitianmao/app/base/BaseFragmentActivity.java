package cn.feitianmao.app.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import cn.feitianmao.app.utils.MyUtils;

import static android.view.View.OnClickListener;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity implements OnClickListener {

    protected MyUtils utils;
    protected boolean isToken = false;// 是否检测更新
    protected boolean isUserMapNull = false;// 是否已登录
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        init(arg0);
        ButterKnife.bind(this);
        /*isUserMapNull = isUserMapNull();
        isToken = isToken();*/
        setInitData();
        initEvent();

    }

    /**
     *
     * 设置是否检测用户数据是否为空
     *
     * @return true为检测，false为不检测
     */
    //protected abstract boolean isUserMapNull();

    /**
     *
     * 设置是否检测其它设备登录
     *
     * @return true为检测，false为不检测
     */
    //protected abstract boolean isToken();

    /**
     * 初始化布局
     */
    protected abstract void init(Bundle arg0);

    /**
     * 初始化数据
     */
    protected abstract void setInitData();

    /**
     * 初始化控件事件
     */
    protected abstract void initEvent();



    /**
     * 切换到指定的Activity 无传递数据
     *
     * @param cls
     *            指定的Activity
     */
    public void showItemActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        //overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    /**
     * 切换到指定的Activity，有数据传
     *
     * @param bundle
     *            传的数据
     * @param cls
     *            指定的Activity
     */
    public void showItemActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        //overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    /**
     * 切换到指定的Activity，无传数据，但需要返回结
     *
     * @param cls
     *            指定的Activity
     * @param requestCode
     *            返回结果代码
     */
    public void showItemActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
        //overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

    /**
     * 切换到指定的Activity，传递数据，要返回结果
     *
     * @param bundle
     *            传数据
     * @param cls
     *            指定的Activity
     * @param requestCode
     *            返回结果代码
     */
    public void showItemActivityForResult(Bundle bundle, Class<?> cls,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        //overridePendingTransition(enterAnim, exitAnim);
        // overridePendingTransition(R.anim.push_out, R.anim.scale_out);
    }

}
