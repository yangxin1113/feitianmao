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
import cn.feitianmao.app.bean.CaogaoData;
import cn.feitianmao.app.bean.ShoucangData;
import cn.feitianmao.app.callback.CaogaoClickListenner;
import cn.feitianmao.app.callback.ShoucangClickListenner;
import cn.feitianmao.app.utils.LSUtils;

import static android.view.View.OnClickListener;

/**
 * 我的草稿
 * Created by Administrator on 2016/8/30 0030.
 */
public class ShoucangAdapter extends RecyclerView.Adapter<ShoucangAdapter.MyViewHolder> {
    private Context context;
    private List<ShoucangData> data;
    private ShoucangClickListenner shoucangClickListenner;

    public ShoucangAdapter(Context context, List<ShoucangData> data){
        this.context = context;
        this.data = data;

    }

    public void setShoucangClickListenner(ShoucangClickListenner shoucangClickListenner){
        this.shoucangClickListenner = shoucangClickListenner;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shoucang, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        holder.tv_shoucang.setText(data.get(i).getShoucang());

        holder.tv_content.setText(data.get(i).getContent());

        holder.tv_answercount.setText(data.get(i).getAnswercount()+"");

        if(shoucangClickListenner != null){
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    shoucangClickListenner.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    shoucangClickListenner.onItemLongClick(holder.itemView, pos);
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

        private TextView tv_shoucang;
        private TextView tv_content;
        private TextView tv_answercount;

        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                tv_shoucang = (TextView) itemView.findViewById(R.id.tv_shoucang);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                tv_answercount = (TextView) itemView.findViewById(R.id.tv_answercount);

            }
        }
    }


}
