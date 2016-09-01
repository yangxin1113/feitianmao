package cn.feitianmao.app.utils;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import cn.feitianmao.app.http.UpLoadListener;
import cn.feitianmao.app.view.application.MyApplication;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class UploadManager {
    private static final String TAG = UploadManager.class.getName();
    private static UploadManager instance;
    private UpLoadListener upLoadListener;

    public static UploadManager getInstance() {
        if (null == instance) {
            instance = new UploadManager();
        }
        return instance;
    }

    /**
     * 上传图片
     *
     * @param filePath
     */
    public void uploadImage(final String filePath) {


        PutObjectRequest put = new PutObjectRequest(MyApplication.bucketName, "CDN/image/android_"+System.currentTimeMillis() + ".jpg", filePath);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.i(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
                if (null != upLoadListener) {
                    upLoadListener.upLoading(currentSize, totalSize);
                }
            }
        });
        MyApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.i(TAG, result.getETag());
                Log.i(TAG, result.getRequestId());
                Log.i(TAG, "request.getObjectKey():" + request.getObjectKey());
                if (null != upLoadListener) {
                    upLoadListener.upLoadSuccess(request, "http://flycat.oss-cn-hangzhou.aliyuncs.com/" + request.getObjectKey());
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.d(TAG, "clientExcepion====" + clientExcepion);
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                    if (null != upLoadListener) {
                        upLoadListener.upLoadError(clientExcepion.getMessage());
                    }
                }
                if (serviceException != null) {
                    Log.e(TAG, serviceException.getErrorCode());
                    Log.e(TAG, serviceException.getRequestId());
                    Log.e(TAG, serviceException.getHostId());
                    Log.e(TAG, serviceException.getRawMessage());
                    if (null != upLoadListener) {
                        upLoadListener.upLoadError(clientExcepion.getMessage());
                    }
                }
            }
        });
    }


    /**
     * 上传视频
     *
     * @param filePath
     */
    public void uploadVideo(final String filePath) {
        byte[] buffer = ImageUtils.transformBitmapToBytes(ImageUtils.createVideoThumbnail(filePath));

        PutObjectRequest put = new PutObjectRequest(MyApplication.bucketName, System.currentTimeMillis() + ".jpg", buffer);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(buffer.length);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.i(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
                if (null != upLoadListener) {
                    upLoadListener.upLoading(currentSize, totalSize);
                }
            }
        });
        MyApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.i(TAG, result.getETag());
                Log.i(TAG, result.getRequestId());
                Log.i(TAG, "request.getObjectKey()=" + request.getObjectKey());
                uploadVideoToOss(request.getObjectKey(), filePath);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.d(TAG, "clientExcepion====" + clientExcepion);
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e(TAG, serviceException.getErrorCode());
                    Log.e(TAG, serviceException.getRequestId());
                    Log.e(TAG, serviceException.getHostId());
                    Log.e(TAG, serviceException.getRawMessage());
                }
            }
        });
    }

    private void uploadVideoToOss(final String thumbnailPath, String filePath) {
        PutObjectRequest put = new PutObjectRequest(MyApplication.bucketName, System.currentTimeMillis() + ".mp4", filePath);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.i(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        MyApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.i(TAG, result.getETag());
                Log.i(TAG, result.getRequestId());
                Log.i(TAG, "request.getObjectKey():" + request.getObjectKey());
                Log.i(TAG, "==========" + result.getServerCallbackReturnBody());
                if (null != upLoadListener) {
                    upLoadListener.upLoadSuccess(request, "http://flycat.oss-cn-hangzhou.aliyuncs.com/" + thumbnailPath, "http://flycat.oss-cn-hangzhou.aliyuncs.com/" + request.getObjectKey());
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.d(TAG, "clientExcepion====" + clientExcepion);
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                    if (null != upLoadListener) {
                        upLoadListener.upLoadError(clientExcepion.getMessage());
                    }
                }
                if (serviceException != null) {
                    Log.e(TAG, serviceException.getErrorCode());
                    Log.e(TAG, serviceException.getRequestId());
                    Log.e(TAG, serviceException.getHostId());
                    Log.e(TAG, serviceException.getRawMessage());
                    if (null != upLoadListener) {
                        upLoadListener.upLoadError(clientExcepion.getMessage());
                    }
                }
            }
        });
    }

    public void setUpLoadListener(UpLoadListener upLoadListener) {
        this.upLoadListener = upLoadListener;
    }
}
