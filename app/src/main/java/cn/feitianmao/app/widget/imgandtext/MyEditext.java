package cn.feitianmao.app.widget.imgandtext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class MyEditext extends EditText {
    public MyEditext(Context context) {
        super(context);
    }

    public MyEditext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}