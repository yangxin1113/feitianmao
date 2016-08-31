package cn.feitianmao.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.feitianmao.app.R;
import cn.feitianmao.app.bean.HomeData;

import static android.view.View.OnClickListener;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private List<HomeData> data;
    private HomeClickListenner homeClickListenner;

    public HomeAdapter(Context context, List<HomeData> data){
        this.context = context;
        this.data = data;

    }

    public void setHomeClickListenner(HomeClickListenner homeClickListenner){
        this.homeClickListenner = homeClickListenner;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context
        ).inflate(R.layout.item_home, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        holder.tv_topic.setText(data.get(i).getTopic());
        holder.tv_topic.setTag(i);
        holder.tv_topic.setOnClickListener(mOnClickListener);
        holder.tv_question.setText(data.get(i).getQuestion());
        holder.tv_question.setTag(i);
        holder.tv_question.setOnClickListener(mOnClickListener);
        holder.tv_answer.setText(data.get(i).getAnswer());
        holder.tv_answer.setTag(i);
        holder.tv_answer.setOnClickListener(mOnClickListener);
        Picasso.with(context).load(data.get(i).getTopicImg()).into(holder.iv_topic_img);
        holder.iv_topic_img.setTag(i);
        holder.iv_topic_img.setOnClickListener(mOnClickListener);
        Picasso.with(context).load(data.get(i).getHeadImg()).into(holder.iv_head);
        holder.iv_head.setTag(i);
        holder.iv_head.setOnClickListener(mOnClickListener);
        holder.tv_username.setText(data.get(i).getUsername());
        holder.tv_username.setTag(i);
        holder.tv_username.setOnClickListener(mOnClickListener);
        holder.tv_agreecount.setText(data.get(i).getAgreecount());
        holder.tv_agreecount.setTag(i);
        holder.tv_agreecount.setOnClickListener(mOnClickListener);
        holder.tv_talkcount.setText(data.get(i).getTalkcount());
        holder.tv_talkcount.setTag(i);
        holder.tv_talkcount.setOnClickListener(mOnClickListener);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_topic;
        private TextView tv_question;
        private TextView tv_answer;
        private ImageView iv_topic_img;
        private ImageView iv_head;
        private TextView tv_username;
        private TextView tv_agreecount;
        private TextView tv_talkcount;
        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                tv_topic = (TextView) itemView.findViewById(R.id.tv_topic);
                tv_question = (TextView) itemView.findViewById(R.id.tv_question);
                tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
                iv_topic_img = (ImageView) itemView.findViewById(R.id.iv_topic_img);
                iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
                tv_username = (TextView) itemView.findViewById(R.id.tv_username);
                tv_agreecount = (TextView) itemView.findViewById(R.id.tv_agreecount);
                tv_talkcount = (TextView) itemView.findViewById(R.id.tv_talkcount);
            }
        }
    }


    private  View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (homeClickListenner != null) {
                int position = (Integer) v.getTag();
                switch (v.getId()){
                    case R.id.tv_topic:
                        //话题
                        homeClickListenner.showTopic(v, position);
                        break;
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
                    case R.id.tv_agreecount:
                        //赞同
                        homeClickListenner.showAgreecount(v, position);
                        break;
                    case R.id.tv_talkcount:
                        //评论
                        homeClickListenner.showTalkcount(v, position);
                        break;

                }

            }
        }
    };

}
