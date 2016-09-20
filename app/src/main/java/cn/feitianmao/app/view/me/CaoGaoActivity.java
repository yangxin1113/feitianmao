package cn.feitianmao.app.view.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.CaogaoAdapter;
import cn.feitianmao.app.adapter.HomeAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.bean.CaogaoData;
import cn.feitianmao.app.callback.CaogaoClickListenner;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.widget.ListItemDecoration;


/**
 * 我的成就
 * Created by Administrator on 2016/7/28 0028.
 */
public class CaoGaoActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivleft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_top)
    RelativeLayout ivTop;
    @BindView(R.id.rv_caogao)
    RecyclerView rvCaogao;

    private CaogaoAdapter caogaoAdapter;
    private List<CaogaoData> caogaoDatas;


    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_caogao);

    }

    @Override
    protected void setInitData() {

        //设置LinearLayoutManager布局管理器，实现ListView效果
        rvCaogao.setLayoutManager(new LinearLayoutManager(CaoGaoActivity.this));
        rvCaogao.addItemDecoration(new ListItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        getData();
        caogaoAdapter = new CaogaoAdapter(CaoGaoActivity.this, caogaoDatas);
        rvCaogao.setAdapter(caogaoAdapter);

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
        caogaoAdapter.setCaogaoClickListenner(new CaogaoClickListenner() {
            @Override
            public void showQuestion(View view, int position) {
                LSUtils.showToast(getApplicationContext(), "问题");
            }

            @Override
            public void showContent(View view, int position) {
                LSUtils.showToast(getApplicationContext(), "内容");
            }

            @Override
            public void showDelete(View view, int position) {
                LSUtils.showToast(getApplicationContext(), "删除");
            }
        });
    }


    public void getData() {
        caogaoDatas = new ArrayList<CaogaoData>();
        for(int i=0; i<10; i++){
            CaogaoData caogao = new CaogaoData();
            caogao.setId(i);
            caogao.setQuestion("梦幻西游是怎么做到让玩家粘性那么高的？"+i);
            caogao.setContent("啥大事发生地方的水果卡卡将更好的风景谁更好的撒娇发货单上广东佛山将控股和大家谁空间按收费点卡收费就卡死的发送宽带房价");
            caogaoDatas.add(caogao);
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

}
