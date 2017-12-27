package com.ly.luoyan.mylibrary.base;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ly.luoyan.mylibrary.listener.GetPictureListener;
import com.ly.luoyan.mylibrary.utils.Config;
import com.ly.luoyan.mylibrary.utils.ImageUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by luoyan on 2017/8/8.
 */

public abstract class BasePicActivity extends BaseDialogActivity implements BasePicFunction{

    /**
     * 压缩图片参数
     */
    protected int ratio = 50;
    /**
     * 是否需要裁剪
     */
    protected boolean isClip = false;
    /**
     * 是否需要压缩
     */
    protected boolean isCompress = true;

    private GetPictureListener listener;

    private File tempFile;
    private String savePicPath;
    private String savePicCutPath;
    private String saveFileName = "app";
    private File cutFile;
    private String[] perms = {"android.permission.CAMERA"};
    private String[] permRW = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
    private int permsRequestCode = 200;
    private int permsRequestCodeRW = 300;

    public void setPictureListener(GetPictureListener listener){
        this.listener = listener;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        savePicPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/app/pic/";
        savePicCutPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/app/cut/";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void takePhoto() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (permissionCheck==-1){
            if (Integer.parseInt(Build.VERSION.SDK)>=23){
                if (Integer.parseInt(Build.VERSION.SDK)<23){
                    photo();
                }else{
                    requestPermissions(perms, permsRequestCode);
                }
            }
        }else{
            photo();
        }


    }

    protected void photo(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(savePicPath);
        if (!file.exists()){
            file.mkdirs();
        }
        tempFile = new File(file, saveFileName+".jpg");
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities = "com.ly.luoyan.mylibrary.fileprovider";
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            //通过FileProvider创建一个content类型的Uri
            Uri imageUri = FileProvider.getUriForFile(this, authorities, tempFile);
            // 指定调用相机拍照后照片的储存路径
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        }else{
            // 指定调用相机拍照后照片的储存路径
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(cameraIntent, Config.CAMERA);
        Log.d("获取SDK版本--->", Build.VERSION.SDK);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void goGallery() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck==-1){
            if (Integer.parseInt(Build.VERSION.SDK)<23){
                gallery();
            }else{
                requestPermissions(permRW, permsRequestCodeRW);
            }
        }else{
            gallery();
        }


    }

    private void gallery() {
        File file = new File(savePicPath);
        if (!file.exists()){
            file.mkdirs();
        }
        tempFile = new File(file, saveFileName+".jpg");
//        String authorities = "com.ly.luoyan.mylibrary.fileprovider";
//        Uri imageUri = FileProvider.getUriForFile(this, authorities, tempFile);
        Intent intent;
        intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, Config.GALLERY);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch(permsRequestCode){
            case 200:{
                boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if(cameraAccepted){
                    //授权成功之后，调用系统相机进行拍照操作等
                    photo();
                }else{
                    showToast("您已拒绝该（相机）权限，请自行设置");
                    //用户授权拒绝之后，友情提示一下就可以了
                }
                break;
            }
            case 300:{
                boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if(cameraAccepted){
                        //授权成功之后，调用系统相机进行拍照操作等
                        gallery();
                }else{
                    showToast("您已拒绝该（存储）权限，请自行设置");
                    //用户授权拒绝之后，友情提示一下就可以了
                }
                break;
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case Config.GALLERY:
                    if (isClip){
                        clipPhoto(data.getData());
                    }else{
                        try {
                            Uri uriGallary;
                            if (isCompress){
                                uriGallary = compressImage(data.getData(),500);
                            }else{
                                uriGallary = compressImage(data.getData(),-1);
                            }
                            sentPicToNext(uriGallary);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Config.CAMERA:
                    if (isClip){
                        clipCa(Uri.fromFile(tempFile));
                    }else{
                        try {
                            Uri uriTakephoto;
                            if (isCompress){
                                uriTakephoto = compressImage(Uri.fromFile(tempFile),500);
                            }else{
                                uriTakephoto = compressImage(Uri.fromFile(tempFile),-1);
                            }
                            sentPicToNext(uriTakephoto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Config.CUT_PIC:
                    if (listener!=null){
                        bitmap = ImageUtils.comp(tempFile.getAbsolutePath(),600);
                        saveImageToGallery(bitmap);
                        listener.getPicture(bitmap,cutFile.getAbsolutePath());
                        tempFile = null;
                    }
                    break;
            }
        }
    }

    private void saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(savePicCutPath);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        cutFile = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(cutFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, ratio, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),cutFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(cutFile.getAbsolutePath())));
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri 图片来源
     */
    private void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, Config.CUT_PIC);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri 图片来源
     */
    private void clipCa(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Uri uri2 = getUri();
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri

            intent.setDataAndType(uri2, "image/*");
            // 指定调用相机拍照后照片的储存路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri2);
            //将存储图片的uri读写权限授权给剪裁工具应用
            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, uri2, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }else{
            intent.setDataAndType(uri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, Config.CUT_PIC);
    }

    private Uri getUri() {
//        File path = new File(Environment.getExternalStorageDirectory(), "app/pic/");
//        if (!path.exists()) {
//            path.mkdirs();
//        }
//        File file = new File(path, "cut.jpg");
        //由于一些Android 7.0以下版本的手机在剪裁保存到URI会有问题，所以根据版本处理下兼容性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(this, "com.ly.luoyan.mylibrary.fileprovider", tempFile);
        } else {
            return Uri.fromFile(tempFile);
        }
    }


    /**
     * 压缩图片
     * @param uri
     * @param ratio
     * @return
     * @throws IOException
     */
    private Uri compressImage(Uri uri,int ratio) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(getRealFilePath(this, uri), options); //此时返回bm为空
        options.inJustDecodeBounds = false;
        //计算缩放比
        int be = (int) (options.outHeight / (float) ratio);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
        bitmap = BitmapFactory.decodeFile(getRealFilePath(this, uri), options);
        Uri uriN = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uriN));
        return uriN;
    }

    private void sentPicToNext(Uri picdata) {
//        new HandlePhoto(picdata).start();
        String path  = getRealFilePath(this, picdata);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (listener!=null){
            listener.getPicture(bitmap,path);
            tempFile = null;
        }
//        if (VerifyUtil.isEmpty(operateLicenseUrl)) {
//            ivBusinessLicense.setImageResource(R.mipmap.ic_launcher);
//        } else {
//            LogUtil.e("执行上传图片地址为", operateLicenseUrl.toString());
//            RequestVo reqVo = mReqParams.upLoadImage(user.getToken(), operateLicenseUrl);
//            LogUtil.d("TAG", reqVo.toString());
//            super.getDataFromServer(reqVo, this);
//
//        }
    }


    /**
     * 获取图片的真实路径
     * @param context
     * @param uri
     * @return
     */
    private static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public String getSavePicPath() {
        return savePicPath;
    }

    public void setSavePicPath(String savePicPath) {
        this.savePicPath = savePicPath;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getSavePicCutPath() {
        return savePicCutPath;
    }

    public void setSavePicCutPath(String savePicCutPath) {
        this.savePicCutPath = savePicCutPath;
    }

    @Override
    public abstract void setContentView();

}
