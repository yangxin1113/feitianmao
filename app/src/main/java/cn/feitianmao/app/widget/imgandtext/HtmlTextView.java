package cn.feitianmao.app.widget.imgandtext;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class HtmlTextView extends TextView {
    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHtmlFromString (String html, boolean useLocalDrawable){
        Html.ImageGetter imgGetter;
        if(useLocalDrawable){
            imgGetter = new LocalImageGetter(getContext());
        }else {
            imgGetter = new UrlImageGetter(this, getContext());
        }

        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        setText(Html.fromHtml(html, imgGetter, new HtmlTagHandler()));

        // make links work
        setMovementMethod(LinkMovementMethod.getInstance());
    }



}
