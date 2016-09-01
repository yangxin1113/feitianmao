package cn.feitianmao.app.http;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public interface UpLoadListener<T> {

    void upLoading(long currentSize, long totalSize);

    void upLoadSuccess(T result, String uploadPath);

    void upLoadSuccess(T result, String thumbnailPath, String uploadPath);

    void upLoadError(String msg);

}