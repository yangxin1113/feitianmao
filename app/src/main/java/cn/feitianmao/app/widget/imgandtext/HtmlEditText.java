package cn.feitianmao.app.widget.imgandtext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class HtmlEditText extends LinearLayout {

    private OnChoosePicListenner onChoosePicListenner;

    public interface OnChoosePicListenner{
        void onChoose();
    }


    public void setOnChoosePicListenner(OnChoosePicListenner onChoosePicListenner){
        this.onChoosePicListenner = onChoosePicListenner;
    }
    
    public HtmlEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HtmlEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HtmlEditText(Context context) {
        super(context);
        init(context);
    }

    //private Context co
    private void init(Context context) {

    }


}
