package me.zhangls.rilintech.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;

import com.zhangls.swipemenulistview.SwipeMenu;
import com.zhangls.swipemenulistview.SwipeMenuCreator;
import com.zhangls.swipemenulistview.SwipeMenuItem;

import me.zhangls.rilintech.R;

/**
 * Created by zsn on 2015/8/5.
 * 获取滑动删除的creator对象的类
 */
public class GetSwipeMenuCreator implements SwipeMenuCreator {

    private Context mContext;
    public GetSwipeMenuCreator(Context pContext){
        mContext=pContext;
    }
    @Override
    public void create(SwipeMenu menu) {
        // create "open" item
//        SwipeMenuItem openItem = new SwipeMenuItem(
//                mContext);
//        // set item background
//        openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                0xCE)));
//        // set item width
//        openItem.setWidth(dp2px(90));
//        // set item title
//        openItem.setTitle("打开");
//        // set item title fontsize
//        openItem.setTitleSize(18);
//        // set item title font color
//        openItem.setTitleColor(Color.WHITE);
//        // add to menu
//        menu.addMenuItem(openItem);

        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(
                mContext);
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                0x3F, 0x25)));
        // set item width
        deleteItem.setWidth(dp2px(90));
        // set a icon
        deleteItem.setIcon(R.drawable.ic_delete);
        // add to menu
        menu.addMenuItem(deleteItem);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }
}
