package cn.feitianmao.app.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.ShoucangAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.bean.FavoriteList;
import cn.feitianmao.app.bean.ShoucangData;
import cn.feitianmao.app.callback.ItemClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.view.main.IndexActivity;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.VERTICAL;


/**
 * 我的成就
 * Created by Administrator on 2016/7/28 0028.
 */
public class ShoucangActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivleft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_top)
    RelativeLayout ivTop;
    @BindView(R.id.rv_caogao)
    RecyclerView rvCaogao;

    private ShoucangAdapter shoucangAdapter;
    private List<FavoriteList> shoucangDatas;
    private FullyLinearLayoutManager mLayoutManager;

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_shoucang);

    }

    @Override
    protected void setInitData() {

        mLayoutManager = new FullyLinearLayoutManager(getApplicationContext(), VERTICAL, false);
        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvCaogao.setLayoutManager(mLayoutManager);
        rvCaogao.addItemDecoration(new ListItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        getData();
        shoucangAdapter = new ShoucangAdapter(ShoucangActivity.this, shoucangDatas);
        rvCaogao.setAdapter(shoucangAdapter);

    }

    @Override
    protected void initEvent() {
        ivleft.setOnClickListener(this);
        itemOnClickListenner(); //item监听事件
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
        }
    }

    private void itemOnClickListenner() {
        shoucangAdapter.setItemClickListenner(new ItemClickListenner() {

            @Override
            public void onItemClick(View view, int position) {
                LSUtils.showToast(getApplicationContext(),"点击");
                showItemActivity(ShoucangFileActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LSUtils.showToast(getApplicationContext(),"长按");
                showItemActivity(ShoucangFileActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //登录
    private void getData() {
        final String LOGIN_URL = ((MyApplication) getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("Favorite").toString();

        OkHttpUtils.post(LOGIN_URL)
                .headers("cookies", PreferencesUtils.getString(getApplicationContext(), "Cookies"))
                .execute(new StringDialogCallback(ShoucangActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {


                        try {
                            JSONObject json = new JSONObject(s);
                            JSONObject array = json.getJSONObject("data");
                            JSONArray array1 = array.getJSONArray("favoriteList");
                            LSUtils.i("favorite", array1.get(0).toString());
                            Gson gson = new Gson();
                            shoucangDatas = gson.fromJson(array1.toString(), new TypeToken<List<FavoriteList>>(){}.getType());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }
}
