package cn.feitianmao.app.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.feitianmao.app.R;
import cn.feitianmao.app.bean.MyAnswerData;
import cn.feitianmao.app.bean.ShoucangData;
import cn.feitianmao.app.callback.ItemClickListenner;

import static android.view.View.OnClickListener;

/**
 * 我的草稿
 * Created by Administrator on 2016/8/30 0030.
 */
public class MyAnswersAdapter extends RecyclerView.Adapter<MyAnswersAdapter.MyViewHolder> {
    private Context context;
    private List<MyAnswerData> data;
    private ItemClickListenner itemClickListenner;

    public MyAnswersAdapter(Context context, List<MyAnswerData> data){
        this.context = context;
        this.data = data;

    }

    public void setItemClickListenner(ItemClickListenner itemClickListenner){
        this.itemClickListenner = itemClickListenner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_huida, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        Picasso.with(context).load(data.get(i).getHeadImg()).into(holder.iv_head);
        holder.tv_timetext.setText(data.get(i).getTime()+"分钟前");
        holder.tv_question.setText(data.get(i).getQuestion());
        holder.tv_content.setText(data.get(i).getAnswer());
        Picasso.with(context).load(data.get(i).getTopicImg()).into(holder.iv_topic_img);
        holder.tv_agreecount.setText(data.get(i).getAgreecount()+"");
        holder.tv_talkcount.setText(data.get(i).getTalkcount()+"");

        if(itemClickListenner != null){
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClickListenner.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClickListenner.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_head;
        private TextView tv_timetext;
        private TextView tv_question;
        private TextView tv_content;
        private ImageView iv_topic_img;
        private TextView tv_agreecount;
        private TextView tv_talkcount;

        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
                tv_timetext = (TextView) itemView.findViewById(R.id.tv_timetext);
                tv_question = (TextView) itemView.findViewById(R.id.tv_question);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                iv_topic_img = (ImageView) itemView.findViewById(R.id.iv_topic_img);
                tv_agreecount = (TextView) itemView.findViewById(R.id.tv_agreecount);
                tv_talkcount = (TextView) itemView.findViewById(R.id.tv_talkcount);

            }
        }
    }


}
