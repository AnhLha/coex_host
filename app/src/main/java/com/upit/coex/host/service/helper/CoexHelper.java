package com.upit.coex.host.service.helper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.upit.coex.host.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.host.service.runnable.CoexRunnable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexHelper {

    public static String parseDateToddMMyyyy(String inputPattern, String outputPattern, String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        java.util.Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    //from resource to uri
    private static Uri resourceIdToUri(Context context, int resourceId) {
        String ANDROID_RESOURCE = "android.resource://";
        String FOREWARD_SLASH = "/";
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    public static void loadImageFromUrl(Activity act, String url, View container) {
        Glide.with(act).load(url).into((ImageView) container);
    }

    public static void loadImageFromFile(Activity act, String path, View container) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), path);
        Glide.with(act).load(file).into((ImageView) container);
    }

    public static void loadImageFromResource(Activity act, String drawableName, final View container,String nameDisposal) {

        CoexRunnable.runFunctionSingle(()->{
            Glide.with(act).load(getImage(act, drawableName)).into(new CustomTarget<Drawable>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    container.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
            return null;
        }).subscribe(new SingleObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                CoexCommonCompositeDisposal.getInstance().addDisposal(nameDisposal,d);
            }

            @Override
            public void onSuccess(Object value) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });


        //Glide.with(act).load(getImage(act, drawableName)).into((ImageView) container);
    }

    public static String showPrice(String price){
        int length = price.length();
        String s = "";
        int count = 0;
        for (int i = length - 1; i >= 0; i--){
            s = price.charAt(i) + s;
            count ++;
            if (count == 3 && i != 0){
                s = "." + s;
                count = 0;
            }
        }
        return s;
    }

    public static String showTime(long time){
        String formatType = "HH:mm dd-MM-yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
        Date date = new Date(time);

        return dateFormat.format(date);
    }

    public static int getImage(Context context, String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }

    public static void exampleGetFullDateTime() {
        //        CoexDate m = new CoexDate.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getFullDateTime();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getFullTime();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getDate();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getMonth();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getYear();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getMinimalDayOfFirstWeek();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getHour();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getMinute();
    }

    public static String getStringResource(Context context, int id) {
        //int stringResourceId = context.getResources().getIdentifier(name, "string", context.getPackageName());
        return context.getResources().getString(id);
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static File saveBitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + System.currentTimeMillis() + "jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

//    public class BlurTransformation extends BitmapTransformation {
//
//        private RenderScript rs;
//
//        public BlurTransformation(Context context) {
//            //super(context);
//            rs = RenderScript.create(context);
//        }
//
//        @Override
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);
//
//            // Allocate memory for Renderscript to work with
//            Allocation request = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
//            Allocation output = Allocation.createTyped(rs, request.getType());
//
//            // Load up an instance of the specific script that we want to use.
//            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//            script.setInput(request);
//
//            // Set the blur radius
//            script.setRadius(10);
//
//            // Start the ScriptIntrinisicBlur
//            script.forEach(output);
//
//            // Copy the output to the blurred bitmap
//            output.copyTo(blurredBitmap);
//
//            return blurredBitmap;
//        }
//
//        @Override
//        public void updateDiskCacheKey(MessageDigest messageDigest) {
//            messageDigest.update("blur transformation".getBytes());
//        }
//
//    }
}
