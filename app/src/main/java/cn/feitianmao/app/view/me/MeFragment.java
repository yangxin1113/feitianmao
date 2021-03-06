package cn.feitianmao.app.view.me;

import android.Manifest;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.feitianmao.app.R;
import cn.feitianmao.app.base.BaseFragment;
import cn.feitianmao.app.http.UpLoadListener;
import cn.feitianmao.app.utils.FileUtil;
import cn.feitianmao.app.utils.LSUtils;
import cn.feitianmao.app.utils.Parse;
import cn.feitianmao.app.utils.UploadManager;
import cn.feitianmao.app.view.application.MyApplication;
import cn.feitianmao.app.widget.CircleImageView;
import cn.feitianmao.app.widget.MyTitleBar;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.mtb_title)
    MyTitleBar myTitleBar;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.ll_logined)
    LinearLayout llLogined;
    @BindView(R.id.iv_xiaoxi)
    ImageView ivXiaoxi;
    @BindView(R.id.iv_chengjiu)
    ImageView ivChengjiu;
    @BindView(R.id.tv_chengjiu)
    TextView tvChengjiu;
    @BindView(R.id.iv_guanzhu)
    ImageView ivGuanzhu;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.iv_shoucang)
    ImageView ivShoucang;
    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;
    @BindView(R.id.iv_caogao)
    ImageView ivCaogao;
    @BindView(R.id.tv_caogao)
    TextView tvCaogao;
    @BindView(R.id.iv_shezhi)
    ImageView ivShezhi;
    @BindView(R.id.tv_shezhi)
    TextView tvShezhi;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.tv_qianming)
    TextView tvQianming;

    @BindView(R.id.ll_chengjiu)
    RelativeLayout llChengjiu;
    @BindView(R.id.ll_guanzhu)
    RelativeLayout llGuanzhu;
    @BindView(R.id.ll_shoucang)
    RelativeLayout llShoucang;
    @BindView(R.id.ll_caogao)
    RelativeLayout llCaogao;
    @BindView(R.id.ll_shezhi)
    RelativeLayout llShezhi;
    @BindView(R.id.ll_xiaoxi)
    RelativeLayout llXiaoxi;


    private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
    // 上传服务器的路径【一般不硬编码到程序中】
    private String imgUrl = "";
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
    private String urlpath;            // 图片本地路径
    private String resultStr = "";    // 服务端返回结果集
    private static ProgressDialog pd;// 等待进度圈
    private static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;    // 图片裁切标记

    @Override
    protected void init() {
        setLayoutRes(R.layout.fragment_me);
        parse = Parse.getInstance();
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        llChengjiu.setOnClickListener(this);
        llXiaoxi.setOnClickListener(this);
        llGuanzhu.setOnClickListener(this);
        llShoucang.setOnClickListener(this);
        llCaogao.setOnClickListener(this);
        llShezhi.setOnClickListener(this);
    }

    @Override
    protected void setInitData() {
        myTitleBar.setText("我的");
        myTitleBar.setLeftVisibility(View.GONE);
        myTitleBar.setRightImage(R.drawable.select_xinxi0);
        myTitleBar.setRightImgClickable(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                Intent i = new Intent(getActivity(), LoginActivity1.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.iv_head:
                menuWindow = new SelectPicPopupWindow(getContext(), itemsOnClick);
                menuWindow.showAtLocation(mainLayout,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.ll_xiaoxi:

                break;
            case R.id.ll_chengjiu:
                if(TextUtils.isEmpty(tvNick.getText())){
                    LSUtils.showToast(getContext(),"请登录");
                }else {
                    i = new Intent(getActivity(), AchievedActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                break;
            case R.id.ll_guanzhu:
                if(TextUtils.isEmpty(tvNick.getText())){
                    LSUtils.showToast(getContext(),"请登录");
                }else {
                    i = new Intent(getActivity(), GuanZhuActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                break;
            case R.id.ll_shoucang:
                if(TextUtils.isEmpty(tvNick.getText())){
                    LSUtils.showToast(getContext(),"请登录");
                }else {
                    i = new Intent(getActivity(), ShoucangActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                break;
            case R.id.ll_caogao:
                if(TextUtils.isEmpty(tvNick.getText())){
                    LSUtils.showToast(getContext(),"请登录");
                }else {
                    i = new Intent(getActivity(), CaoGaoActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                break;
            case R.id.ll_shezhi:
                i = new Intent(getActivity(), ShezhiActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }

    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                // 相册选择图片
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(getContext(), "temphead.jpg", photo);
            iv_head.setImageDrawable(drawable);
            LSUtils.i("URLPATH", urlpath + "xxxx");
            uploadHead(urlpath);
            // 新线程后台上传服务端
            //pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
            //new Thread(uploadImageRunnable).start();
        }
    }

    private void uploadHead(String path) {
        UploadManager.getInstance().uploadImage(path);
        UploadManager.getInstance().setUpLoadListener(new UpLoadListener() {
            @Override
            public void upLoading(long currentSize, long totalSize) {

            }

            @Override
            public void upLoadSuccess(Object result, String uploadPath) {
                LSUtils.d("zzz", " uploadPath:" + uploadPath);
            }

            @Override
            public void upLoadSuccess(Object result, String thumbnailPath, String uploadPath) {
                LSUtils.d("zzz", "thumbnailPath:" + thumbnailPath + "   uploadPath:" + uploadPath);
            }

            @Override
            public void upLoadError(String msg) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo(((MyApplication) getActivity().getApplication()).getUserInfo());
    }

    public void setUserInfo(Map<String, Object> userInfo){
        if(userInfo != null){
            String user_head = parse.isNull(userInfo.get("avator"));
           if(!user_head.equals("") || user_head != null){
               Picasso.with(getContext()).load(parse.isNull(userInfo.get("avator").toString().replace("http://flycat.oss-cn-hangzhou.aliyuncs.com/",""))).into(iv_head);
           }
            String user_name = parse.isNull(userInfo.get("name"));
            if(user_name != null){
                llLogined.setVisibility(View.VISIBLE);
                tv_login.setVisibility(View.GONE);
                tvNick.setText(parse.isNull(userInfo.get("name")));
                tvQianming.setText(parse.isNull(userInfo.get("signature")));
            }else {
                llLogined.setVisibility(View.GONE);
                tv_login.setVisibility(View.VISIBLE);
            }

        }

    }
}
