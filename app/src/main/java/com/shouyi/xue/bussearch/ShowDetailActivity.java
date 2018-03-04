package com.shouyi.xue.bussearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.shouyi.xue.bussearch.fragment.BusChangeFragment;
import com.shouyi.xue.bussearch.fragment.BusLineFragment;
import com.shouyi.xue.bussearch.fragment.PointInfoFragment;

/**
 * Created by asia on 2018/3/4.
 */

public class ShowDetailActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    public static final String TYPE_KEY = "type_key";
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        mFrameLayout = (FrameLayout) this.findViewById(R.id.container_framelayout);


        int key = getIntent().getIntExtra(TYPE_KEY, -1);
        mFragmentManager = this.getSupportFragmentManager();
        openFragment(key);
    }

    private void openFragment(int key) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Bundle mBundle = null;

        switch (key) {
            case 0:
                ft.add(R.id.container_framelayout, PointInfoFragment.getInstance(mBundle = new Bundle()));
                break;
            case 1:
                ft.add(R.id.container_framelayout, BusLineFragment.getInstance(mBundle = new Bundle()));
                break;
            case 2:
                ft.add(R.id.container_framelayout, BusChangeFragment.getInstance(mBundle = new Bundle()));
                break;
            default:
                break;
        }

        ft.commit();
    }
}
