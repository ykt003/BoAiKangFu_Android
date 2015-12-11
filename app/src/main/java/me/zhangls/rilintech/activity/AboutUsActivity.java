package me.zhangls.rilintech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/12/7.
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back_image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initView();
    }

    private void initView(){
        back_image_view = (ImageView) findViewById(R.id.back);
        back_image_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
        }
    }
}
