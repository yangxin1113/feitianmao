package cn.feitianmao.app.view.me;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;

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
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;
import cn.feitianmao.app.callback.StringDialogCallback;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.PreferencesUtils;
import cn.feitianmao.app.view.application.MyApplication;
import okhttp3.Call;
import okhttp3.Response;

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
    private List<Question> questionDatas = null;
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
        getTopicData();
        guanzhuWentiAdapter = new GuanzhuWentiAdapter(MyTiwenActivity.this, questionDatas);
        rvTiwen.setAdapter(guanzhuWentiAdapter);
        getData();
    }

    private void getTopicData() {
        questionDatas = new ArrayList<Question>();
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setQuestion("的发生地方的三个地方公司的");
            question.setId(i);
            question.setGuanzhucount(i * 2 + 20);
            question.setAnswercount(i * 1 + 100);
            questionDatas.add(question);
        }
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
                LSUtils.showToast(MyTiwenActivity.this, "点击了我" + questionDatas.get(position).getQuestion());
            }

            @Override
            public void showAnswer(View view, int position) {
                LSUtils.showToast(MyTiwenActivity.this, "点击了我" + questionDatas.get(position).getAnswercount());
            }

            @Override
            public void showGuanzhu(View view, int position) {
                LSUtils.showToast(MyTiwenActivity.this, "点击了我" + questionDatas.get(position).getGuanzhucount());
            }
        });
    }

    //用户信息
    private void getData() {
        final String QUESTION_URL = ((MyApplication)getApplication()).getApis().get("Host").toString() +
                ((MyApplication) getApplication()).getApis().get("UserQuestion").toString();

        OkHttpUtils.post(QUESTION_URL)
                .headers("cookies", PreferencesUtils.getString(MyTiwenActivity.this, "Cookies"))
                .execute(new StringDialogCallback(MyTiwenActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = new JSONObject(s);
                            LSUtils.i("UserInfo",s);
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
