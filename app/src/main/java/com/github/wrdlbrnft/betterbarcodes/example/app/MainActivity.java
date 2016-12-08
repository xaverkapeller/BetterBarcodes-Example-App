package com.github.wrdlbrnft.betterbarcodes.example.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.github.wrdlbrnft.betterbarcodes.example.app.databinding.ActivityMainBinding;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 04/12/2016
 */
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.tabLayout.addOnTabSelectedListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ReaderFragment.newInstance())
                .commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {

            case 0:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.container, ReaderFragment.newInstance())
                        .commit();
                break;

            case 1:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.container, ViewerFragment.newInstance())
                        .commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
