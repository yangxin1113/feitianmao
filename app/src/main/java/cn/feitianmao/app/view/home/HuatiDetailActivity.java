package cn.feitianmao.app.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.request.BaseRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.HomeAdapter1;
import cn.feitianmao.app.adapter.ViewPagerAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.bean.HomeData;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.widget.CircleImageView;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;


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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_top)
    RelativeLayout ivTop;
    @BindView(R.id.iv_topic_img)
    CircleImageView ivTopicImg;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.tv_guanzhucount)
    TextView tvGuanzhucount;
    @BindView(R.id.ll_guanzhu)
    LinearLayout llGuanzhu;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_showmore)
    LinearLayout llShowmore;

    private String topic_id;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_huati_detail);
        topic_id = getIntent().getStringExtra("topicId");
    }

    @Override
    protected void setInitData() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        getData(topic_id);
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


    private void getData(String topic_id) {
        final String HOME_URL = ((MyApplication) getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("TopicDetail").toString();
        OkHttpUtils.get(HOME_URL)
                .params("topic_id", topic_id)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        GsonBuilder gb = new GsonBuilder();
                        gb.registerTypeAdapter(String.class, new StringConverter());
                        Gson gson = gb.create();
                        JSONObject json = null;
                        JSONArray array = null;
                        try {
                            json = new JSONObject(s);
                            array = json.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
    }

}
