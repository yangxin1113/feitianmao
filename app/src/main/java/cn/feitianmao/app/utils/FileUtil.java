package cn.feitianmao.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.feitianmao.app.http.Contants;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class FileUtil {

    public static String saveFile(Context c, String fileName, Bitmap bitmap){
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap){
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/ZYX/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    public static String picassoImg( final Context context, String url){

        final String imageName = "temphead.jpg";

        Target target = new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                //String imageName = System.currentTimeMillis() + ".jpg";


                File dcimFile = MyUtils.getInstance().getCache(context,
                        Contants.USER_PATH_IMAGE, imageName,
                        false);

                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(context,"图片下载至:"+dcimFile,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        //Picasso下载
        Picasso.with(context).load(url).into(target);
        LSUtils.i("image", imageName);

        String img_url = context.getExternalCacheDir().getPath() + "/"
                + Contants.USER_PATH_IMAGE+"/"+imageName;
        //FileUtil.returnBitmap(img_url);
        return img_url;

    }

    /**
     * 根据图片的url路径获得Bitmap对象
     * @param url
     * @return
     */
    private static Bitmap returnBitmap(String url) {

        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}