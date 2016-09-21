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
import cn.feitianmao.app.R;
import cn.feitianmao.app.adapter.ShoucangAdapter;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.bean.ShoucangData;
import cn.feitianmao.app.callback.ItemClickListenner;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.widget.FullyLinearLayoutManager;
import cn.feitianmao.app.widget.ListItemDecoration;

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
    private List<ShoucangData> shoucangDatas;
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


    public void getData() {
        shoucangDatas = new ArrayList<ShoucangData>();
        for(int i=0; i<10; i++){
            ShoucangData shoucangData = new ShoucangData();
            shoucangData.setId(i);
            shoucangData.setShoucang("守望先锋" + i);
            shoucangData.setContent("啥大事发生地方的水果卡卡将更好的风景谁更好的撒娇发");
            shoucangData.setAnswercount(i*3+1*2);
            shoucangDatas.add(shoucangData);
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
