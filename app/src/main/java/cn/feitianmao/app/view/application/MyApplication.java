package cn.feitianmao.app.view.application;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.Map;

import cn.feitianmao.app.base.BaseApplication;
import cn.feitianmao.app.http.Contants;
import cn.feitianmao.app.utils.MyUtils;
import cn.feitianmao.app.utils.SaveListObject;


/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class MyApplication extends BaseApplication{

    private static MyApplication myApplication = null;
    private Map<String, Object> apis;// apis
    private Map<String, Object> userInfo;// 用户名
    //oss阿里云存储
    private static final String accessKeyId = "1B7sjWW5nwg6srnv";
    private static final String accessKeySecret = "qLcYJD8sUnehCXeVY6hjbrsGFyIyEX";
    public static final String bucketName = "flycat";
    public static final String endPoint = "http://oss-cn-hangzhou.aliyuncs.com";
    public static OSSClient oss;

    @Override
    protected void setConfig() {
        super.setConfig();
        myApplication = this;
        initOSSConfig();
        initImgConfig();
    }

    private void initImgConfig() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0")))
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new FadeInBitmapDisplayer(1000)) // 设置图片渐显的时间
//                .delayBeforeLoading(300)  // 下载前的延迟时间
                .build();

        int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        int memCacheSize = 1024 * 1024 * memClass / 8;

        File cacheDir = new File(Environment.getExternalStorageDirectory().getPath() + "/fetitianmao/cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new UsingFreqLimitedMemoryCache(memCacheSize)) // You can pass your own memory cache implementation/
                .memoryCacheSize(memCacheSize) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .defaultDisplayImageOptions(options)
//                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public String getCachePath() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = getExternalCacheDir();
        else
            cacheDir = getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }


    private void initOSSConfig() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endPoint, credentialProvider, conf);
    }

    public Map<String, Object> getApis(){
        if(apis == null){
            File file = MyUtils.getInstance().getCache(getApplicationContext(),
                    Contants.USER_PATH_PRIVATE, Contants.CONFIG_APIS, true);
            apis = (Map<String, Object>) SaveListObject.getInstance()
                    .openObject(file);
        }
        return apis;
    }

    public void setApis(Map<String, Object> apis) {
        if (apis == null) {
            MyUtils.getInstance()
                    .getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.CONFIG_APIS,
                            true).delete();
        } else {
            SaveListObject.getInstance().saveObject(
                    MyUtils.getInstance().getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.CONFIG_APIS,
                            true), apis);
        }
        this.apis = apis;
    }

    /**
     * 用户信息
     * @return
     */
    public Map<String, Object> getUserInfo(){
        if(userInfo == null){
            File file = MyUtils.getInstance().getCache(getApplicationContext(),
                    Contants.USER_PATH_PRIVATE, Contants.USERINFO, true);
            userInfo = (Map<String, Object>) SaveListObject.getInstance()
                    .openObject(file);
        }
        return userInfo;
    }

    public void setUserInfo(Map<String, Object> userInfo) {
        if (userInfo == null) {
            MyUtils.getInstance()
                    .getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.USERINFO,
                            true).delete();
        } else {
            SaveListObject.getInstance().saveObject(
                    MyUtils.getInstance().getCache(getApplicationContext(),
                            Contants.USER_PATH_PRIVATE, Contants.USERINFO,
                            true), userInfo);
        }
        this.userInfo = userInfo;
    }
}
