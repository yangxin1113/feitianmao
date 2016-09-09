package cn.feitianmao.app.view.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import cn.feitianmao.app.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class AskQueActivity extends BaseFragmentActivity {



    private String[] items = {"相册", "拍照"};
    @Override
    protected void init(Bundle arg0) {

    }

    @Override
    protected void setInitData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }


    //获取相片的Dialog处理
    /*private void showDialog() {
        new AlertDialog.Builder(this).setTitle("添加图片").setItems(this.items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                mCropParams.refreshUri();
                switch (paramAnonymousInt) {
                    case 0://相册
                        mCropParams.enable = false;
                        mCropParams.compress = true;

                        intent = CropHelper.buildGalleryIntent(mCropParams);
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
    }*/
}
