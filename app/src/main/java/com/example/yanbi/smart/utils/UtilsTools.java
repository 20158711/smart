package com.example.yanbi.smart.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by yanbi on 2017/9/10.
 */

public class UtilsTools {

    //设置自定义字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    public static boolean isEmpty(String text){
        if (text==null || text.length()==0){
            return true;
        }else {
            return false;
        }
    }

    //保存图片到ShareUtils
    public static void putImageToShare(Context mContext, ImageView imageView){
        //保存图片
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //1,将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //2,利用base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //3,将String保存ShareUtils
        ShareUtils.putString(mContext, StaticClass.IMAGE_TITLE,imgString);
    }

    //从ShareUtils读取图片
    public static void getImageFromShareUtils(Context mContext, ImageView imageView){
        String imageString = ShareUtils.getString(mContext, StaticClass.IMAGE_TITLE, "");
        if(!TextUtils.isEmpty(imageString)){
            byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }

}
