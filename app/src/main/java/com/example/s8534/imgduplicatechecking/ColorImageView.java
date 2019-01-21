package com.example.s8534.imgduplicatechecking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.s8534.imgduplicatechecking.path.path_String;

public class ColorImageView extends AppCompatImageView {
    //颜色值
    int color;
    Bitmap bitmap;
    Drawable drawable;
    private String TAG = "Colorimageview";

    public ColorImageView(Context context) {
        super(context);
        init();
    }

    public ColorImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //Drawable包括color和Drawable
        if (getBackground() != null && getDrawable() == null) {
            //只设置了背景图片
            drawable = getBackground();
        } else if (getBackground() == null && getDrawable() != null) {
            //只设置了资源图片
            drawable = getDrawable();
        } else {
            //即设置了背景图片，又设置了资源图片，这样无法准确确认颜色
            //未设置背景图片，以及资源图片src
            return;
        }
        //Drawable包括color和Drawable
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            color = colorDrawable.getColor();
        } else {
            String img_path = path_String.imagePathListname.get(1);
            bitmap= BitmapFactory.decodeFile(img_path);
//            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (bitmap != null) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            for(int i=x;i<bitmap.getWidth();i+=5) {
                if (i < 0 || i > bitmap.getWidth() || y < 0 || y > bitmap.getHeight()) {
                    return false;
                }
                color = bitmap.getPixel(i, y);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int a=Color.alpha(color);
                Log.i(TAG, "x=" + i + "y=" + y + "r=" + r + ",g=" + g + ",b=" + b+",a"+a);
            }
        }
        if (color == 0) {
            return false;
        } else {
            if (mOnColorSelectedListener != null) {
                mOnColorSelectedListener.onColorSelectedL(color);
            }
        }
        return true;
    }

    //回调接口
    public interface OnColorSelectedListener {
        void onColorSelectedL(int color);
    }

    private OnColorSelectedListener mOnColorSelectedListener;
    //set方法方便外部调用接口

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.mOnColorSelectedListener = onColorSelectedListener;
    }
}
