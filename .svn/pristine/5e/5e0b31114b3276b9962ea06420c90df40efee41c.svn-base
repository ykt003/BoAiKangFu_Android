package me.zhangls.rilintech.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.view.ChartBaseView;
import me.zhangls.rilintech.view.LineChartDistanceView;
import me.zhangls.rilintech.view.LineChartSpeedView;
import me.zhangls.rilintech.view.RadarChartView;

/**
 * Created by YANG on 15/11/27.
 */
public class DataStatisticalActivity extends BaseActivity {

    private RadarChartView radarChartView;

    private int mSelected = 0;

    private ChartBaseView[] mCharts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE); //设置没标题

        //setContentView(R.layout.activity_statistical_data);

        //initView();

        mCharts = new ChartBaseView[]{
                new RadarChartView(this),    //蜘蛛网雷达图
                new LineChartDistanceView(this),
                new LineChartSpeedView(this)
        };

        mSelected = getIntent().getIntExtra("selected",0);

        if (mSelected > mCharts.length - 1) {
            setContentView(R.layout.activity_statistical_data);
            initView();
        } else if (mSelected == 0) {
            initActivity();
        } else if (mSelected == 1) {
            initActivity2();
        }

    }


    private void initView() {
        radarChartView = (RadarChartView) findViewById(R.id.radar_chart_view);
    }

    private void initActivity() {
        //图表的使用方法:
        //使用方式一:
        // 1.新增一个Activity
        // 2.新增一个View,继承Demo中的GraphicalView或DemoView都可，依Demo中View目录下例子绘制图表.
        // 3.将自定义的图表View放置入Activity对应的XML中，将指明其layout_width与layout_height大小.
        // 运行即可看到效果. 可参考非ChartsActivity的那几个图的例子，都是这种方式。

        //使用方式二:
        //代码调用 方式有下面二种方法:
        //方法一:
        //在xml中的FrameLayout下增加图表和ZoomControls,这是利用了现有的xml文件.
        // 1. 新增一个View，绘制图表.
        // 2. 通过下面的代码得到控件，addview即可
        //LayoutInflater factory = LayoutInflater.from(this);
        //View content = (View) factory.inflate(R.layout.activity_multi_touch, null);


        //方法二:
        //完全动态创建,无须XML文件.
        FrameLayout content = new FrameLayout(this);

        //缩放控件放置在FrameLayout的上层，用于放大缩小图表
        FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        frameParm.gravity = Gravity.BOTTOM | Gravity.RIGHT;

		   /*
          //缩放控件放置在FrameLayout的上层，用于放大缩小图表
	       mZoomControls = new ZoomControls(this);
	       mZoomControls.setIsZoomInEnabled(true);
	       mZoomControls.setIsZoomOutEnabled(true);
		   mZoomControls.setLayoutParams(frameParm);
		   */

        //图表显示范围在占屏幕大小的90%的区域内
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 0.9);
        int scrHeight = (int) (dm.heightPixels * 0.9);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                scrWidth, scrHeight);

        //居中显示
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        //图表view放入布局中，也可直接将图表view放入Activity对应的xml文件中
        final RelativeLayout chartLayout = new RelativeLayout(this);

        chartLayout.setBackgroundColor(Color.WHITE);
        chartLayout.addView(mCharts[mSelected], layoutParams);

        //增加控件
        ((ViewGroup) content).addView(chartLayout);
        //((ViewGroup) content).addView(mZoomControls);
        setContentView(content);
        //放大监听
        //  mZoomControls.setOnZoomInClickListener(new OnZoomInClickListenerImpl());
        //缩小监听
        //  mZoomControls.setOnZoomOutClickListener(new OnZoomOutClickListenerImpl());
    }

    private void initActivity2() {

        FrameLayout content = new FrameLayout(this);

        //缩放控件放置在FrameLayout的上层，用于放大缩小图表
        FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        frameParm.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        //图表显示范围在占屏幕大小的90%的区域内
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 0.9);
        int scrHeight = (int) (dm.heightPixels * 0.45);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                scrWidth, scrHeight);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                scrWidth, scrHeight);

        //居中显示
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //图表view放入布局中，也可直接将图表view放入Activity对应的xml文件中
        final RelativeLayout chartLayout = new RelativeLayout(this);

        chartLayout.setBackgroundColor(Color.WHITE);
        chartLayout.addView(mCharts[1], layoutParams);
        chartLayout.addView(mCharts[2], layoutParams2);
        //上边距60
        chartLayout.setPadding(0, 60, 0, 0);
        //增加控件
        ((ViewGroup) content).addView(chartLayout);

        setContentView(content);

    }


}
