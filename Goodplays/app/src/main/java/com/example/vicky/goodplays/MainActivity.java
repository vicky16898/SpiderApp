package com.example.vicky.goodplays;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    ViewPager vppager;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vppager = (ViewPager)findViewById(R.id.vpPager);

        adapterViewPager = new Mypager(getSupportFragmentManager());
        vppager.setAdapter(adapterViewPager);

    }











    public class Mypager extends FragmentPagerAdapter{


        private  int NUM_ITEMS = 4;

        public Mypager(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(0, "Page # 1");
                case 1:
                    return SecondFragment.newInstance(1, "Page # 2");

                case 2:
                    return  ThirdFragment.newInstance(2 , "Page # 3");

                case 3:
                    return FinalFragment.newInstance(3 , "Page # 4");

                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position+1);
        }













    }



}
