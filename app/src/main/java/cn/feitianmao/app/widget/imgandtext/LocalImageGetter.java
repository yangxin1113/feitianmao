package cn.feitianmao.app.widget.imgandtext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class LocalImageGetter implements Html.ImageGetter {

    Context c;

    public LocalImageGetter(Context c){
        this.c = c;
    }
    @Override
    public Drawable getDrawable(String source) {
        int id = c.getResources().getIdentifier(source, "drawable", c.getPackageName());
        if(id == 0){
            //the drawable resources wasn`t found in our package, maybe it is a stock android drawable?
            id = c.getResources().getIdentifier(source, "drawable", "android");
        }
        if(id == 0){
            Log.e("LocalImageGetter", "source could not be found: " + source);
            return null;
        }else {
            Drawable d = c.getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }

    }
}
