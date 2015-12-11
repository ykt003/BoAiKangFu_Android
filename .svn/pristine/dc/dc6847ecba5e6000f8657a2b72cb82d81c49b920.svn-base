package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/8/27.
 */
public class SpinnerBaseAdapter extends ArrayAdapter{

    private Context context;
    private int resource;
    private List<String> objects;
    private Spinner spinner;

    public SpinnerBaseAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    public SpinnerBaseAdapter(Context context, int resource,Spinner spinner, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.spinner = spinner;
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = convertView.inflate(context, R.layout.item_spinner_layout,
                null);
        TextView label = (TextView) view
                .findViewById(R.id.spinner_item_label);
        ImageView check = (ImageView) view
                .findViewById(R.id.spinner_item_checked_image);
        label.setText(objects.get(position));
        if (spinner.getSelectedItemPosition() == position) {
            view.setBackgroundColor(context.getResources().getColor(
                    R.color.white));
            check.setImageResource(R.drawable.check_selected);
        } else {
            view.setBackgroundColor(context.getResources().getColor(
                    R.color.white));
            check.setImageResource(R.drawable.check_unselect);
        }

        return view;
    }
}
