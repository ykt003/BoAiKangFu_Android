package me.zhangls.rilintech.fragment;

import android.app.Fragment;

import me.zhangls.rilintech.R;


/**
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.rb_attention:
                fragment = new PatientInfoFragment();
                break;
            case R.id.rb_mylist:
                fragment = new SettingFragment();
                break;
            case R.id.rb_global:
                fragment = new TableFragment();
                break;
        }
        return fragment;
    }
}
