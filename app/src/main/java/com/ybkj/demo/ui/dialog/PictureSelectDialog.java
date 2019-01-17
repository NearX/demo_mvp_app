package com.ybkj.demo.ui.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ybkj.demo.BuildConfig;
import com.ybkj.demo.R;
import com.ybkj.demo.base.GlideApp;
import com.ybkj.demo.manager.ActivityManager;
import com.ybkj.demo.utils.BitmapCompressTask;
import com.ybkj.demo.utils.LogUtil;
import com.ybkj.demo.utils.RxPermissionUtils;
import com.ybkj.demo.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Description 图片选择dialog
 * **注意：
 * 1.必须在使用此dialog的Activity的onActivityResult方法中调用
 * 此dialog的onActivityResult方法，否则回调无效
 * Author Ren Xingzhi
 * Created on 2019/1/14.
 * Email 15384030400@163.com
 */
public class PictureSelectDialog {

    private static final int REQUEST_CAMERA = 1;//请求相机
    private static final int REQUEST_ALBUM = 2;//请求相册
    private static final int REQUEST_CROP = 3;//请求图片处理
    private Context mContext;
    private Dialog dialog;
    private OnSelectSuccessListener onSelectSuccessListener;//获取图片成功监听

    private String filePath = "";//拍照路径
    private File cropFile = new File(Environment.getExternalStorageDirectory(), "picture.jpg");//输出图片路径
    private Uri imageCropUri = Uri.fromFile(cropFile);//输出图片uri
    private int outputWidth = 450;//输出图片宽
    private int outputHeight = 450;//输出图片高

    private boolean crop = true;//是否裁剪，默认裁剪
    private int requestCode = 0;//请求码

    public PictureSelectDialog(Context mContext) {
        this.mContext = mContext;

        dialog = new Dialog(mContext, R.style.translucent_dialog);
        initDialog();
    }

    /**
     * 设置输出大小，默认450*450
     *
     * @param width  输出宽
     * @param height 输出高
     */
    public void setOutputSize(int width, int height) {
        outputWidth = width;
        outputHeight = height;
    }

    /**
     * 设置是否裁剪，默认裁剪
     *
     * @param crop shif裁剪
     */
    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public void setOnSelectSuccessListener(OnSelectSuccessListener onSelectSuccessListener) {
        this.onSelectSuccessListener = onSelectSuccessListener;
    }

    /**
     * 显示dialog
     */
    public void show(int requestCode) {
        this.requestCode = requestCode;
        dialog.show();
    }

    /**
     * 显示dialog
     */
    public void show() {
        dialog.show();
    }

    /**
     * 隐藏dialog
     */
    public void hide() {
        dialog.dismiss();
    }

    /**
     * 从回调获取数据
     *
     * @param requestCode 请求标记
     * @param resultCode  返回标记
     * @param data        返回数据
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA://从相机返回
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        ToastUtil.showShort("SD卡不能使用");
                        return;
                    }
                    File file = new File(filePath);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", file);//通过FileProvider创建一个content类型的Uri
                    } else {
                        imageUri = Uri.fromFile(file);
                    }
                    if (crop) {
                        startImageAction(imageUri);
                    } else {
                        decodeBitmap(imageUri);
                    }

                }
                break;
            case REQUEST_ALBUM:
                Uri uri = null;
                if (data == null)
                    return;
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ToastUtil.showShort("SD卡不能使用");
                        return;
                    }
                    uri = data.getData();
                    if (crop) {
                        startImageAction(uri);
                    } else {
                        decodeBitmap(uri);
                    }
                } else {
                    ToastUtil.showShort("获取图片失败");
                }

                break;
            case REQUEST_CROP:
                if (data == null) {
                    ToastUtil.showShort("取消选择");
                    return;
                }
                filePath = "";
                // 上传头像
                decodeBitmap(imageCropUri);
                break;
        }
    }

    private void initDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_picture, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.choose_item_dialog_animation);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        final Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = window.getAttributes();
        lParams.width = (int) (display.getWidth());
        window.setAttributes(lParams);
        window.setGravity(Gravity.BOTTOM);

        RelativeLayout layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
        RelativeLayout layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
        layout_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromCamera();
            }
        });

        layout_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromAlbum();
            }
        });
    }

    private void takePictureFromAlbum() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissionUtils.getInstance((Activity) mContext).setPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE)
                    .setOnPermissionCallBack(new RxPermissionUtils
                            .OnPermissionListener() {

                        @Override
                        public void onPermissionGranted(String name) {
                            LogUtil.i("onPermissionGranted name=" + name);
                        }

                        @Override
                        protected void onAllPermissionGranted() {
                            openAlbum();
                        }
                    }).start();
        } else {
            openAlbum();
        }
    }

    private void takePictureFromCamera() {
        int cameraPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED || storagePermission != PackageManager.PERMISSION_GRANTED) {
            RxPermissionUtils.getInstance((Activity) mContext).setPermission(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .setOnPermissionCallBack(new RxPermissionUtils
                            .OnPermissionListener() {

                        @Override
                        public void onPermissionGranted(String name) {
                            LogUtil.i("onPermissionGranted name=" + name);
                        }

                        @Override
                        protected void onAllPermissionGranted() {
                            openCamera();
                        }
                    }).start();
        } else {
            openCamera();
        }
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        File imageFile = null;
        String storagePath;
        File storageDir;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        try {
            storagePath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            storageDir = new File(storagePath);
            storageDir.mkdirs();
            imageFile = File.createTempFile(timeStamp, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filePath = imageFile.getAbsolutePath();// 获取相片的保存路径

        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", imageFile);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(imageFile);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        ActivityManager.startActivityForResult((Activity) mContext, intent, REQUEST_CAMERA);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) mContext).startActivityForResult(intent, REQUEST_ALBUM);
    }

    /**
     * 执行照片裁剪
     *
     * @param uri uri
     */
    private void startImageAction(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", outputWidth);
        intent.putExtra("aspectY", outputHeight);
        intent.putExtra("outputX", outputWidth);
        intent.putExtra("outputY", outputHeight);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        ((Activity) mContext).startActivityForResult(intent, REQUEST_CROP);
    }

    /**
     * 图片不进行裁剪时处理图片大小
     *
     * @param uri uri
     */
    private void decodeBitmap(Uri uri) {
        GlideApp.with(mContext).asBitmap().load(uri).override(outputWidth, outputHeight)
                .fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                Bitmap otherPic = resource;
                try {
                    if (otherPic == null) {
                        ToastUtil.showShort("选择的图片存在问题，请重新选择图片");
                        return;
                    }
                    //压缩图片
                    BitmapCompressTask bitmapCompressTask = new BitmapCompressTask();
                    bitmapCompressTask.setOnCompressFinishListener(bytes -> {
                        if (onSelectSuccessListener != null)
                            onSelectSuccessListener.onBytesSuccess(bytes, requestCode);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    });
                    bitmapCompressTask.execute(otherPic);

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i("图片出错=" + e.getMessage());
                } finally {
                }
            }
        });
    }

    /**
     * 获取图片成功监听
     */
    public interface OnSelectSuccessListener {
        /**
         * 获取bytes成功
         *
         * @param bytes bytes
         * @param tag   标记
         */
        void onBytesSuccess(byte[] bytes, int tag);
    }
}
