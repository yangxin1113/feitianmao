package cn.feitianmao.app.view.main;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.ViewPagerHelper;
import cn.feitianmao.app.view.application.MyApplication;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class GuideActivity extends BaseFragmentActivity {

    private List<View> views = null;

    @BindView(R.id.vp_guide)
    public ViewPager mViewPager;
    @BindView(R.id.dots_parent)
    public LinearLayout viewPoints;

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_guide);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestContactSMSPermission();
    }

    @Override
    protected void setInitData() {
        //PreferencesUtils.putString(GuideActivity.this, "help", "on");
        setViewPager();
        loadApis();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化数据
     */
    private void setViewPager() {
        views = new ArrayList<View>();

        View view1 = LayoutInflater.from(this).inflate(R.layout.page_guide_first, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.page_guide_second, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.page_guide_third, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showItemActivity(IndexActivity.class);
            }
        });

        new ViewPagerHelper(false, mViewPager, views, viewPoints, R.drawable.page_indicator_focused, R.drawable.page_indicator_unfocused);
    }


    /**
     * 加载Apis
     */
    private void loadApis() {

        OkHttpUtils.post(getResources().getString(R.string.apis))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getBoolean("status")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                setApis(data);
                            } else {
                                LSUtils.showToast(GuideActivity.this, "请求错误");
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
     * 申请内存权限。
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
        Toast.makeText(GuideActivity.this, "获取SD卡权限成功", Toast.LENGTH_SHORT).show();

    }

    @PermissionNo(101)
    private void getMultiNo() {
        Toast.makeText(GuideActivity.this, "获取SD卡权限失败", Toast.LENGTH_SHORT).show();
    }

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(GuideActivity.this)
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
