package cn.feitianmao.app.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.feitianmao.app.R;
import cn.feitianmao.app.bean.HuatiData;
import cn.feitianmao.app.bean.YonghuData;
import cn.feitianmao.app.callback.GuanzhuHuatiClickListenner;
import cn.feitianmao.app.callback.GuanzhuYonghuClickListenner;

import static android.view.View.OnClickListener;

/**
 * 我关注的话题
 * Created by Administrator on 2016/8/30 0030.
 */
public class GuanzhuHuatiAdapter extends RecyclerView.Adapter<GuanzhuHuatiAdapter.MyViewHolder> {
    private Context context;
    private List<HuatiData> data;
    private GuanzhuHuatiClickListenner guanzhuHuatiClickListenner;

    public GuanzhuHuatiAdapter(Context context, List<HuatiData> data){
        this.context = context;
        this.data = data;

    }

    public void setGuanzhuHuatiClickListenner(GuanzhuHuatiClickListenner guanzhuHuatiClickListenner){
        this.guanzhuHuatiClickListenner = guanzhuHuatiClickListenner;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_huati, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {

        Picasso.with(context).load(data.get(i).getTopic_images()).into(holder.iv_huati);
        holder.iv_huati.setTag(i);
        holder.iv_huati.setOnClickListener(mOnClickListener);
        holder.tv_topic.setText(data.get(i).getTopic_name());
        holder.tv_topic.setTag(i);
        holder.tv_topic.setOnClickListener(mOnClickListener);
        holder.tv_guanzhucount.setText(data.get(i).getFollow_count()+"");
        holder.tv_guanzhucount.setTag(i);
        holder.tv_guanzhucount.setOnClickListener(mOnClickListener);
        holder.tv_questioncount.setText(data.get(i).getQues_count()+"");
        holder.tv_questioncount.setTag(i);
        holder.tv_questioncount.setOnClickListener(mOnClickListener);
        if (data.get(i).getIs_cancel() == 0){
            holder.ll_guanzhu.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gaunzhu0));
            holder.iv_guanzhu.setVisibility(View.GONE);
            holder.tv_guanzhu.setText("已关注");
        }else {
            holder.ll_guanzhu.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gaunzhu1));
            holder.iv_guanzhu.setVisibility(View.VISIBLE);
            holder.tv_guanzhu.setText("关注");
        }
        final int isGuanzhu =data.get(i).getIs_cancel();
        holder.ll_guanzhu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuanzhu == 0){
                    holder.ll_guanzhu.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gaunzhu1));
                    holder.iv_guanzhu.setVisibility(View.VISIBLE);
                    holder.tv_guanzhu.setText("关注");
                    data.get(i).setIs_cancel(1);
                }else {
                    holder.ll_guanzhu.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gaunzhu0));
                    holder.iv_guanzhu.setVisibility(View.GONE);
                    holder.tv_guanzhu.setText("已关注");
                    data.get(i).setIs_cancel(0);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_huati;
        private TextView tv_topic;
        private TextView tv_guanzhucount;
        private TextView tv_questioncount;
        private ImageView iv_guanzhu;
        private TextView tv_guanzhu;
        private LinearLayout ll_guanzhu;
        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                iv_huati = (ImageView) itemView.findViewById(R.id.iv_huati);
                tv_topic = (TextView) itemView.findViewById(R.id.tv_topic);
                tv_guanzhucount = (TextView) itemView.findViewById(R.id.tv_guanzhucount);
                tv_questioncount = (TextView) itemView.findViewById(R.id.tv_questioncount);
                iv_guanzhu = (ImageView) itemView.findViewById(R.id.iv_guanzhu);
                tv_guanzhu = (TextView) itemView.findViewById(R.id.tv_guanzhu);
                ll_guanzhu = (LinearLayout) itemView.findViewById(R.id.ll_guanzhu);
            }
        }
    }


    private  OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (guanzhuHuatiClickListenner != null) {
                int position = (Integer) v.getTag();
                switch (v.getId()){

                    case R.id.iv_huati:
                        //头像
                        guanzhuHuatiClickListenner.showHuati(v, position);
                        break;
                    case R.id.tv_topic:
                        //昵称
                        guanzhuHuatiClickListenner.showTopic(v, position);
                        break;
                    case R.id.tv_guanzhucount:
                        //签名
                        guanzhuHuatiClickListenner.showGuanzhucount(v, position);
                        break;
                    case R.id.tv_questioncount:
                        //是否关注
                        guanzhuHuatiClickListenner.showWenticount(v, position);
                        break;

                }

            }
        }
    };

}
