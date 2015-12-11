package me.zhangls.rilintech.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import me.zhangls.rilintech.R;

/**
 * Created by zsn on 15/11/26.
 */
public class GetTextImage extends View{
    private int i;
    private float x = 20, y = 40;
    private static float windowWidth;
    private static float windowHeight;
    private static float left = 0;      //图片在屏幕中位置X坐标
    private static float top = 0;       //图片在屏幕中位置Y坐标
    private String str = "";
    private DisplayMetrics dm = new DisplayMetrics();  //用于获取屏幕的高度和宽度
    private WindowManager windowManager;
    private Bitmap newbitmap;

    public GetTextImage(Context context, float x, float y, int i) {
        super(context);
        this.x = x;
        this.y = y;
        this.i = i;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //屏幕的宽度
        windowWidth = windowManager.getDefaultDisplay().getWidth();
        //屏幕的高度
        windowHeight = windowManager.getDefaultDisplay().getHeight();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.mcgill).copy(Bitmap.Config.ARGB_8888, true);
        i++;
        if (i <= 10) {
            if (newbitmap != null) {
                newbitmap = getTextImage(newbitmap, str, x, y);
            } else {
                newbitmap = getTextImage(bmp, str, x, y);
            }
        }
        canvas.drawBitmap(newbitmap, 0, 0, null);
    }

    /**
     * 返回值: Bitmap 参数：原图片,文字 功能: 根据给定的文字生成相应图片
     *
     * @param originalMap
     * @param text        文字
     * @param x           点击的X坐标
     * @param y           点击的Y坐标
     * @return
     */
    public static Bitmap getTextImage(Bitmap originalMap, String text, float x,
                                      float y) {
        float bitmapWidth = originalMap.getWidth();
        float bitmapHeight = originalMap.getHeight();
        // 定义画布
        Canvas canvas = new Canvas(originalMap);
        // 定义画笔
        Paint paint = new Paint();


        //获得文本的长度（像素）
        //float textWidth = paint.measureText(text);
        float textWidth = 15;
        canvas.drawBitmap(originalMap, 0, 0, null);

        // 如果图片宽度小于屏幕宽度
        if (left + bitmapWidth < windowWidth) {
            // 右边界
            if (x >= left + bitmapWidth - textWidth) {
                x = left + bitmapWidth - textWidth;
            }
            // 左边界
            else if (x <= left) {
                x = left;
            }
        } else {
            // 右边界
            if (x >= windowWidth - textWidth) {
                x = windowWidth - textWidth;
            }
            // 左边界
            else if (x <= 0) {
                x = 0;
            }
        }
        // 如果图片高度小于屏幕高度
        if (top + bitmapHeight < windowHeight) {
            // 下
            if (y >= top + bitmapHeight) {
                y = top + bitmapHeight;
            }
            // 上
            else if (y <= top + 10) {
                y = top + 10;
            }
        } else {
            if (y >= windowHeight) {
                y = windowHeight;
            } else if (y <= 0) {
                y = 0;
            }
        }


        paint.setColor(Color.RED);// 设置红色
        canvas.drawCircle(x, y, 15, paint);// 分别是 圆心的x，y坐标，15为半径
        paint.setAntiAlias(true);// 设置画笔的锯齿效果

        return originalMap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            // 重绘
            invalidate();
        }
        return true;
    }
}