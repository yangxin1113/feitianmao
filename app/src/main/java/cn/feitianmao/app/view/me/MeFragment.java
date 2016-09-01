package cn.feitianmao.app.view.me;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.widget.MyTitleBar;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.mtb_title)
    MyTitleBar myTitleBar;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_me);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void setInitData() {
        myTitleBar.setText("个人中心");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }

    }
}
