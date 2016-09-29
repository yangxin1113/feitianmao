package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.GuanzhuWentiAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.bean.Question;
import cn.feitianmao.app.bean.WentiData;
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.utils.StringConverter;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.widget.ListItemDecoration;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okhttputils.OkHttpUtils.getContext;

/**
 * 我的提问
 * Created by Administrator on 2016/8/29 0029.
 */
public class MyTiwenActivity extends BaseFragmentActivity {

    @BindView(R.id.rv_tiwen)
    RecyclerView rvTiwen;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_top)
    RelativeLayout ivTop;
    private List<WentiData> questionDatas = new ArrayList<WentiData>();
    private GuanzhuWentiAdapter guanzhuWentiAdapter;

    @Override
    protected void initEvent() {
        itemOnClickListenner();
        ivLeft.setOnClickListener(this);
    }

    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_tiwen);
    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvTiwen.setLayoutManager(new LinearLayoutManager(MyTiwenActivity.this));

        /*guanzhuWentiAdapter = new GuanzhuWentiAdapter(MyTiwenActivity.this, questionDatas);
        rvTiwen.setAdapter(guanzhuWentiAdapter);*/

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
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 接口回调实现RecyclerView的item布局中每个控件的点击事件
     */
    private void itemOnClickListenner() {
        guanzhuWentiAdapter.setGuzhuWentiClickListenner(new GuanzhuWentiClickListenner() {

            @Override
            public void showQuestion(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getTitle());
            }

            @Override
            public void showAnswer(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getAnswer());
            }

            @Override
            public void showGuanzhu(View view, int position) {
                LSUtils.showToast(getContext(), "点击了我" + questionDatas.get(position).getConcern());
            }
        });
    }


    //
    private void wenTi(int page) {
        final String WEN_URL = ((MyApplication) getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("Personalquestion").toString();

        OkHttpUtils.post(WEN_URL)
                .params("uid","me")
                .params("page",String.valueOf(page))
                .execute(new StringDialogCallback(MyTiwenActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject json = null;
                        JSONArray array = null;
                        try {
                            json = new JSONObject(s);
                            if(json.getBoolean("status")){
                                GsonBuilder gb = new GsonBuilder();
                                gb.registerTypeAdapter(String.class, new StringConverter());
                                Gson gson = gb.create();

                                json = new JSONObject(s);
                                array = json.getJSONArray("data");

                                if (questionDatas != null || questionDatas.size() == 0){
                                    try {
                                        questionDatas = gson.fromJson(array.toString(), new TypeToken<List<WentiData>>(){}.getType());
                                    } catch (JsonSyntaxException e) {
                                        //e.printStackTrace();
                                        LSUtils.i("错误","gson解析错误");
                                    }
                                }else {
                                    List<WentiData> more = gson.fromJson(array.toString(), new TypeToken<List<WentiData>>(){}.getType());
                                    questionDatas.addAll(more);
                                }

                                if(guanzhuWentiAdapter==null){
                                    guanzhuWentiAdapter = new GuanzhuWentiAdapter(getContext(), questionDatas);
                                    rvTiwen.setAdapter(guanzhuWentiAdapter);
                                }else{
                                    guanzhuWentiAdapter.notifyDataSetChanged();
                                }
                                rvTiwen.addItemDecoration(new ListItemDecoration(MyTiwenActivity.this, LinearLayoutManager.VERTICAL));
                                rvTiwen.setFocusable(false);
                            }else {
                                LSUtils.showToast(getContext(),json.getString("alert"));
                            }
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
