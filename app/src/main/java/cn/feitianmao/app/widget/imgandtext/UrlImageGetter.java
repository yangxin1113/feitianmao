package cn.feitianmao.app.widget.imgandtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class UrlImageGetter implements Html.ImageGetter {

    Context c;
    TextView container;
    int width;

    public UrlImageGetter(TextView t, Context c){
        this.c = c;
        this.container = t;
        width = c.getResources().getDisplayMetrics().widthPixels;
    }
    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        ImageLoader.getInstance().loadImage(source, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // 计算缩放比例
                float scaleWidth = ((float) width)/loadedImage.getWidth();
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleWidth);
                loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), matrix,
                        true);
                urlDrawable.bitmap = loadedImage;
                urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                container.invalidate();
                container.setText(container.getText()); // 解决图文重叠
            }
        });
        return urlDrawable;
    }

    public class UrlDrawable extends BitmapDrawable{
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if(bitmap != null){
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
