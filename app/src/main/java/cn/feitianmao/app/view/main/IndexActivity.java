package cn.feitianmao.app.view.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.TwoQuit;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.me.MeFragment;
import cn.feitianmao.app.view.find.FindFragment;
import cn.feitianmao.app.view.home.HomeFragment;
import cn.feitianmao.app.view.tuijian.TuijainFragment;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class IndexActivity extends BaseFragmentActivity {

    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.tv_home)
    TextView tv_home;

    @BindView(R.id.ll_tuijian)
    LinearLayout ll_tuijain;
    @BindView(R.id.iv_tuijian)
    ImageView iv_tuijian;
    @BindView(R.id.tv_tuijian)
    TextView tv_tuiajin;

    @BindView(R.id.ll_find)
    LinearLayout ll_find;
    @BindView(R.id.iv_find)
    ImageView iv_find;
    @BindView(R.id.tv_find)
    TextView tv_find;

    @BindView(R.id.ll_me)
    LinearLayout ll_me;
    @BindView(R.id.iv_me)
    ImageView iv_me;
    @BindView(R.id.tv_me)
    TextView tv_me;

    @BindView(R.id.ll_ask)
    LinearLayout ll_ask;

    private TwoQuit mTwoQuit;
    private List<Fragment> fragmentList ;
    private int oldIndex;// 记录当前展示的Fragment
    private List<Map<String, Object>> viewList ;

    private HomeFragment mHomeFragment;//首页
    private MeFragment mMeFragment;

    //登录
    private static final int USER_LOGIN = 0;
    private static final int OTHER_LOGIN = 1;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_index);

    }

    @Override
    protected void setInitData() {
        setFragments();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout, mHomeFragment).commitAllowingStateLoss();
        oldIndex = 0;
        
        loadApis();
    }


    @Override
    protected void initEvent() {
        ll_home.setOnClickListener(this);
        ll_tuijain.setOnClickListener(this);
        ll_find.setOnClickListener(this);
        ll_me.setOnClickListener(this);
        ll_ask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                //首页
                addOrShowFragment(0);
                break;
            case R.id.ll_tuijian:
                //推荐
                addOrShowFragment(1);
                break;
            case R.id.ll_find:
                //发现
                addOrShowFragment(2);
                break;
            case R.id.ll_me:
                //我的
                addOrShowFragment(3);
                break;
            case R.id.ll_ask:
                requestContactSMSPermission();

                break;
        }
    }



    /**
     * 设置当前为要展示的Fragment并设置底部图片、文字
     *
     * @param index
     */
    private void addOrShowFragment(int index) {
        if (fragmentList.get(index) == null || index == oldIndex) {

            return;
        }
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.my_alpha_0_1_200,
                R.anim.my_alpha_1_0_200);
        Fragment f = fragmentList.get(index);
        if (f.isAdded()) {
            mFragmentTransaction.hide(fragmentList.get(oldIndex)).show(f).commitAllowingStateLoss();
        } else {
            mFragmentTransaction.hide(fragmentList.get(oldIndex)).add(R.id.framelayout, f).commitAllowingStateLoss();
        }
        setImgText(index);
        oldIndex = index;

    }


    /**
     * 设置底部图片与文字
     *
     * @param index
     */
    private void setImgText(int index) {
        for (int i = 0; i < viewList.size(); i++) {
            Map<String, Object> item = viewList.get(i);
            if (i == index) {
                ((ImageView) item.get("imageView")).setImageResource((int)item.get("img_true"));
                ((TextView) item.get("textView")).setTextColor(ContextCompat.getColor(IndexActivity.this, R.color.text_bottom_true));
            } else {
                ((ImageView) item.get("imageView")).setImageResource((int)item.get("img_false"));
                ((TextView) item.get("textView")).setTextColor(ContextCompat.getColor(IndexActivity.this, R.color.text_bottom_false));
            }
        }
    }


    private void setFragments(){

        fragmentList = new ArrayList<Fragment>();
        viewList = new ArrayList<Map<String, Object>>();// 所有底部图标与文字
        mHomeFragment = new HomeFragment();
        mMeFragment = new MeFragment();
        fragmentList.add(mHomeFragment);
        fragmentList.add(new TuijainFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(mMeFragment);

        Map<String, Object> item0 = new HashMap<String, Object>();
        item0.put("textView", tv_home);
        item0.put("imageView", iv_home);
        item0.put("img_false", R.drawable.icon_shouye_normal);
        item0.put("img_true", R.drawable.icon_shouye_selected);
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("textView", tv_tuiajin);
        item1.put("imageView", iv_tuijian);
        item1.put("img_false", R.drawable.icon_tuijian_normal);
        item1.put("img_true", R.drawable.icon_tuijian_selected);
        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("textView", tv_find);
        item2.put("imageView", iv_find);
        item2.put("img_false", R.drawable.icon_faxian_normal);
        item2.put("img_true", R.drawable.icon_faxian_selected);
        Map<String, Object> item3= new HashMap<String, Object>();
        item3.put("textView", tv_me);
        item3.put("imageView", iv_me);
        item3.put("img_false", R.drawable.icon_wode_normal);
        item3.put("img_true", R.drawable.icon_wode_selected);
        viewList.add(item0);
        viewList.add(item1);
        viewList.add(item2);
        viewList.add(item3);
    }

    /**
     * 加载Apis
     */
    private void loadApis() {
        OkHttpUtils.post()
                .url(getResources().getString(R.string.apis))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        LSUtils.showToast(IndexActivity.this, "请求错误");
                    }

                    @Override
                    public void onResponse(String s, int i) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(jsonObject.getBoolean("status")){
                                JSONObject data = jsonObject.getJSONObject("data");
                                setApis(data);
                            }else {
                                LSUtils.showToast(IndexActivity.this, "请求错误");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    /**
     * 将Apis保存本地
     * @param data
     */
    private void setApis(JSONObject data) throws JSONException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> mapApis = gson.fromJson(data.toString(), type);
        ((MyApplication)getApplication()).setApis(mapApis);
        LSUtils.i("zzz",((MyApplication)getApplication()).getApis().get("Host").toString());
    }


    /**
     * 跳转到首页
     */
    public void startHomeFragment() {
        onClick(ll_home);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (oldIndex == 0) {
                if (mTwoQuit == null)
                    mTwoQuit = new TwoQuit();
                mTwoQuit.finish(getApplicationContext());
            } else {
                onClick(ll_home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 申请联系人、短信、权限。
     */
    private void requestContactSMSPermission() {
        AndPermission.with(this)
                .requestCode(101)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(rationaleListener)
                .send();
    }

    @PermissionYes(101)
    private void getMultiYes() {
        Toast.makeText(IndexActivity.this, "获取SD卡权限成功", Toast.LENGTH_SHORT).show();
        showItemActivity(AskQueActivity.class);
    }

    @PermissionNo(101)
    private void getMultiNo() {
        Toast.makeText(IndexActivity.this, "获取SD卡权限失败", Toast.LENGTH_SHORT).show();
    }

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(IndexActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("您已拒绝过SD卡权限，没有SD卡权限正常使用提问功能获取图片！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };


   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // 这个Activity中有Fragment，这句话不能注释，否则Fragment讲接受不到获取权限的通知。
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
   }

}
