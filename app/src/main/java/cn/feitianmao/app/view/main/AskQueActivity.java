package cn.feitianmao.app.view.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragmentActivity;
import cn.feitianmao.app.http.UpLoadListener;
import cn.feitianmao.app.utils.BitmapUtil;
import cn.feitianmao.app.utils.CropHandler;
import cn.feitianmao.app.utils.CropHelper;
import cn.feitianmao.app.utils.CropParams;
import cn.feitianmao.app.utils.GetUri;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.StatusBarUtil;
import cn.feitianmao.app.utils.UploadManager;
import cn.feitianmao.app.widget.imgandtext.HtmlEditText;
import cn.feitianmao.app.widget.imgandtext.HtmlFile;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class AskQueActivity extends BaseFragmentActivity implements CropHandler {


    @BindView(R.id.html_edt)
    HtmlEditText html_edt;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ln_pic)
    LinearLayout lnPic;


    private String TAG = "AskQueActivity";
    public static Bitmap bimap;
    private ProgressDialog dialog;
    private TreeMap<String, String> map;
    private String[] items = {"相册", "拍照"};
    private ArrayList<String> paths = new ArrayList<>();
    CropParams mCropParams;
    private Uri uri;
    private String path;//视频地址
    private static final int REQUEST_CODE_PICK_IMAGE = 0, REQUEST_CODE_CAPTURE_CAMEIA = 1, VIDEO_SEARCH = 2, PHOTO_REQUEST_CUT = 3;
    private Bitmap bitmap;



    @Override
    protected void init(Bundle arg0) {
        setContentView(R.layout.activity_ask);
        mCropParams = new CropParams(this);
    }

    @Override
    protected void setInitData() {
        //StatusBarUtil.setStatusBarColor(AskQueActivity.this,  R.color.white);//设置状态栏颜色
    }

    @Override
    protected void initEvent() {
        ivLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);

        html_edt.setOnChoosePicListener(new HtmlEditText.OnChoosePicListener() {
            @Override
            public void onChoose() {
                showDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
            case R.id.tv_right:
                break;

        }
    }


    //获取相片的Dialog处理
    private void showDialog() {
        new AlertDialog.Builder(this).setTitle("添加图片").setItems(this.items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                mCropParams.refreshUri();
                switch (paramAnonymousInt) {
                    case 0://相册
                        mCropParams.enable = false;
                        mCropParams.compress = true;
                        Intent intent = CropHelper.buildGalleryIntent(mCropParams);
                        startActivityForResult(intent, CropHelper.REQUEST_CROP);
                        break;
                    case 1://拍照
                        mCropParams.enable = false;
                        mCropParams.compress = true;
                        intent = CropHelper.buildCameraIntent(mCropParams);
                        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                        break;
                }

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
            }
        }).show();
    }

    /**
     * Intent回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        paths.clear();
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_SEARCH) {
                if (data != null && data.getData() != null) {
                    uri = data.getData();
                    //调用自己写的uri工具获取到真实的路径
                    path = GetUri.getPath(this, uri);
                    //代码提取
                    //setVideo(path);
                }
            } else if (requestCode == 200) {//拍摄视频
                if (path == null) {
                    LSUtils.showToast(getApplicationContext(), "内存空间不足，请清理内存空间");
                    return;
                }
                // 成功
                uri = Uri.parse(path);
                //代码提取
                //setVideo(path);
            } else {//图片处理
                CropHelper.handleResult(this, requestCode, resultCode, data);
            }

        }
    }

    /**
     * 插入图片
     */
    private void insertPic(final String imgSDCard, final Bitmap bitmap) {
        if (dialog == null) {//放在外面是因为fragment开始加载请求了好几个界面的数据很容易赵成dialog多个但是只关闭了一个
            dialog = new ProgressDialog(this);
        }
        dialog.setMessage("上传中。。。。");
        dialog.show();
        map = new TreeMap<>();
        UploadManager.getInstance().uploadImage(imgSDCard);
        UploadManager.getInstance().setUpLoadListener(new UpLoadListener() {
            @Override
            public void upLoading(long currentSize, long totalSize) {

            }

            @Override
            public void upLoadSuccess(Object result, String uploadPath) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                LSUtils.d("zzz",  " uploadPath:" + uploadPath);
                HtmlFile htmlFile = new HtmlFile(imgSDCard, uploadPath);
                html_edt.setUploadPath(htmlFile);
            }

            @Override
            public void upLoadSuccess(Object result, String thumbnailPath, String uploadPath) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                LSUtils.d("zzz", "thumbnailPath:" + thumbnailPath + "   uploadPath:" + uploadPath);
                HtmlFile htmlFile = new HtmlFile(imgSDCard, uploadPath);
                html_edt.setUploadPath(htmlFile);
            }

            @Override
            public void upLoadError(String msg) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
        String path = GetUri.getPath(this, uri);
        bitmap = BitmapUtil.decodeUriAsBitmap(this, uri);
        Log.d(TAG, "Crop Uri in path: " + uri.getPath() + "=====" + path);
        if (bitmap != null) {
            insertPic(path, bitmap);
        }
    }


    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
        String path = GetUri.getPath(this, uri);
        bitmap = BitmapUtil.decodeUriAsBitmap(this, uri);
        Log.d(TAG, "Crop Uri in path: " + uri.getPath() + "=====" + path);
        if (bitmap != null) {
            insertPic(path, bitmap);
        }
        // Compressed uri
        // mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Crop canceled!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, "Crop failed: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }
}
