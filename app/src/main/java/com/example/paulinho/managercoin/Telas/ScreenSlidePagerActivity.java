package com.example.paulinho.managercoin.Telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.paulinho.managercoin.R;


/**
 * Created by PauLinHo on 10/09/2017.
 */

public class ScreenSlidePagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0: // Fragment # 0 - This will show FirstFragment
                    return new CompraFragment();
                case 1: // Fragment # 0 - This will show FirstFragment
                    return new VendaFragment();
                case 2: // Fragment # 0 - This will show FirstFragment
                    return new DepositoFragment();
                case 3: // Fragment # 0 - This will show FirstFragment
                    return new SaqueFragment();
                case 4: // Fragment # 0 - This will show FirstFragment
                    return new ScreenSlidePageFragment();
                case 5: // Fragment # 0 - This will show FirstFragment different title
                    return new ScreenSlidePageFragment2();
                default:
                    return new ScreenSlidePageFragment();

            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
