package cn.feitianmao.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.feitianmao.app.R;
import cn.feitianmao.app.bean.Question;
import cn.feitianmao.app.callback.GuanzhuWentiClickListenner;

import static android.view.View.OnClickListener;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class GuanzhuWentiAdapter extends RecyclerView.Adapter<GuanzhuWentiAdapter.MyViewHolder> {
    private Context context;
    private List<Question> data;
    private GuanzhuWentiClickListenner guzhuWntiClickListenner;

    public GuanzhuWentiAdapter(Context context, List<Question> data){
        this.context = context;
        this.data = data;

    }

    public void setGuzhuWentiClickListenner(GuanzhuWentiClickListenner guzhuWntiClickListenner){
        this.guzhuWntiClickListenner = guzhuWntiClickListenner;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context
        ).inflate(R.layout.item_wenti, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.tv_question.setText(data.get(i).getQuestion());
        holder.tv_question.setTag(i);
        holder.tv_question.setOnClickListener(mOnClickListener);
        holder.tv_guanzhucount.setText(data.get(i).getGuanzhucount()+"");
        holder.ll_guanzhu.setTag(i);
        holder.ll_guanzhu.setOnClickListener(mOnClickListener);
        holder.tv_answercount.setText(data.get(i).getAnswercount()+"");
        holder.ll_answer.setTag(i);
        holder.ll_answer.setOnClickListener(mOnClickListener);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView tv_question;
        private TextView tv_guanzhucount;
        private TextView tv_answercount;
        private LinearLayout ll_answer;
        private LinearLayout ll_guanzhu;
        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                tv_question = (TextView) itemView.findViewById(R.id.tv_question);
                tv_guanzhucount = (TextView) itemView.findViewById(R.id.tv_guanzhucount);
                tv_answercount = (TextView) itemView.findViewById(R.id.tv_answercount);
                ll_answer = (LinearLayout) itemView.findViewById(R.id.ll_answer);
                ll_guanzhu = (LinearLayout) itemView.findViewById(R.id.ll_guanzhu);
            }
        }
    }


    private  OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (guzhuWntiClickListenner != null) {
                int position = (Integer) v.getTag();
                switch (v.getId()){

                    case R.id.tv_question:
                        //问题
                        guzhuWntiClickListenner.showQuestion(v, position);
                        break;
                    case R.id.ll_answer:
                        //回答
                        guzhuWntiClickListenner.showAnswer(v, position);
                        break;
                    case R.id.ll_guanzhu:
                        //赞同
                        guzhuWntiClickListenner.showGuanzhu(v, position);
                        break;

                }

            }
        }
    };

}
