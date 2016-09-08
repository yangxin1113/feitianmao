package cn.feitianmao.app.widget.imgandtext;

import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.TypefaceSpan;

import org.xml.sax.XMLReader;

import java.util.Vector;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class HtmlTagHandler implements Html.TagHandler{

    private int mListItemCount = 0;
    private final Vector<String> mListParents = new Vector<String>();

    private static class Code{

    }

    private static class Center{

    }
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if(opening){
            //opening tag
            if(tag.equalsIgnoreCase("ul") || tag.equalsIgnoreCase("ol")
                    || tag.equalsIgnoreCase("dd")){
                mListParents.add(tag);
                mListItemCount = 0;

            }else if(tag.equalsIgnoreCase("code")){
                start(output, new Code());
            }else if (tag.equalsIgnoreCase("center")) {
                start(output, new Center());
            }
        } else {
            // closing tag
            if (tag.equalsIgnoreCase("ul") || tag.equalsIgnoreCase("ol")
                    || tag.equalsIgnoreCase("dd")) {
                mListParents.remove(tag);
                mListItemCount = 0;
            } else if (tag.equalsIgnoreCase("li")) {
                handleListTag(output);
            } else if (tag.equalsIgnoreCase("code")) {
                end(output, Code.class, new TypefaceSpan("monospace"), false);
            } else if (tag.equalsIgnoreCase("center")) {
                end(output, Center.class,
                        new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), true);
            }
        }
    }


    private void start(Editable output, Object mark){
        int len = output.length();
        output.setSpan(mark, len, len, Spanned.SPAN_MARK_MARK);
    }

    private void end(Editable output, Class kind, Object repl, boolean paragraphStytle){
        Object obj = getLast(output, kind);
        //start of the tag
        int where = output.getSpanStart(obj);
        //end of the tag
        int len = output.length();

        output.removeSpan(obj);

        if(where != len){
            if(paragraphStytle){
                output.append("\n");
                len++;
            }
            output.setSpan(repl, where, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * Get last marked position of a specific tag kind (private class)
     *
     * @param text
     * @param kind
     * @return
     */
    private Object getLast(Editable text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);
        if (objs.length == 0) {
            return null;
        } else {
            for (int i = objs.length; i > 0; i--) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1];
                }
            }
            return null;
        }
    }

    private void handleListTag(Editable output) {
        if (mListParents.lastElement().equals("ul")) {
            output.append("\n");
            String[] split = output.toString().split("\n");

            int lastIndex = split.length - 1;
            int start = output.length() - split[lastIndex].length() - 1;
            output.setSpan(new BulletSpan(15 * mListParents.size()), start, output.length(), 0);
        } else if (mListParents.lastElement().equals("ol")) {
            mListItemCount++;

            output.append("\n");
            String[] split = output.toString().split("\n");

            int lastIndex = split.length - 1;
            int start = output.length() - split[lastIndex].length() - 1;
            output.insert(start, mListItemCount + ". ");
            output.setSpan(new LeadingMarginSpan.Standard(15 * mListParents.size()), start,
                    output.length(), 0);
        }
    }
}
