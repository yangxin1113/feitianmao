package cn.feitianmao.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.feitianmao.app.R;
import cn.feitianmao.app.bean.HomeBean;
import cn.feitianmao.app.callback.HomeClickListenner;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.MyViewHolder> {
    private Context context;
    private HomeBean data;
    private HomeClickListenner homeClickListenner;

    public HotAdapter(Context context, HomeBean data){
        this.context = context;
        this.data = data;

    }

    public void setHomeClickListenner(HomeClickListenner homeClickListenner){
        this.homeClickListenner = homeClickListenner;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context
        ).inflate(R.layout.item_hot, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        holder.tv_question.setText(data.getData().get(i).getTitle());
        holder.tv_question.setTag(i);
        holder.tv_question.setOnClickListener(mOnClickListener);
        holder.tv_answer.setText(data.getData().get(i).getContent());
        holder.tv_answer.setTag(i);
        holder.tv_answer.setOnClickListener(mOnClickListener);
        Picasso.with(context).load(data.getData().get(i).getAvator()).into(holder.iv_topic_img);
        holder.iv_topic_img.setTag(i);
        holder.iv_topic_img.setOnClickListener(mOnClickListener);
        Picasso.with(context).load(data.getData().get(i).getAvator()).into(holder.iv_head);
        holder.iv_head.setTag(i);
        holder.iv_head.setOnClickListener(mOnClickListener);
        holder.tv_username.setText(data.getData().get(i).getName());
        holder.tv_username.setTag(i);
        holder.tv_username.setOnClickListener(mOnClickListener);
        holder.tv_pingluncount.setText("200");
        holder.tv_pingluncount.setTag(i);
        holder.tv_pingluncount.setOnClickListener(mOnClickListener);
        holder.tv_guanzhu.setTag(i);
        holder.tv_guanzhu.setOnClickListener(mOnClickListener);


    }

    @Override
    public int getItemCount() {
        return data.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView tv_question;
        private TextView tv_answer;
        private ImageView iv_topic_img;
        private ImageView iv_head;
        private TextView tv_username;
        private TextView tv_pingluncount;
        private TextView tv_guanzhu;
        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){

                tv_question = (TextView) itemView.findViewById(R.id.tv_question);
                tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
                iv_topic_img = (ImageView) itemView.findViewById(R.id.iv_topic_img);
                iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
                tv_username = (TextView) itemView.findViewById(R.id.tv_username);
                tv_pingluncount = (TextView) itemView.findViewById(R.id.tv_pingluncount);
                tv_guanzhu = (TextView) itemView.findViewById(R.id.tv_guanzhu);
            }
        }
    }


    private  View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (homeClickListenner != null) {
                int position = (Integer) v.getTag();
                switch (v.getId()){
                    /*case R.id.tv_topic:
                        //话题
                        homeClickListenner.showTopic(v, position);
                        break;*/
                    case R.id.tv_question:
                        //问题
                        homeClickListenner.showQuestion(v, position);
                        break;
                    case R.id.tv_answer:
                        //回答
                        homeClickListenner.showAnswer(v, position);
                        break;
                    case R.id.iv_topic_img:
                        //话题图片
                        homeClickListenner.showTopicImg(v, position);
                        break;
                    case R.id.iv_head:
                        //头像
                        homeClickListenner.showhead(v, position);
                        break;
                    case R.id.tv_username:
                        //头像
                        homeClickListenner.showUsername(v, position);
                        break;
                    case R.id.tv_pingluncount:
                        //赞同
                        homeClickListenner.showAgreecount(v, position);
                        break;
                    case R.id.tv_guanzhu:
                        //评论
                        homeClickListenner.showTalkcount(v, position);
                        break;

                }

            }
        }
    };

}
