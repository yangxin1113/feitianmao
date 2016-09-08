package cn.feitianmao.app.view.application;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

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
}
