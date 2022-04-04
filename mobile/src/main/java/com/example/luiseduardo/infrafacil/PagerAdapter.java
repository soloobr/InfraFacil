package com.example.luiseduardo.infrafacil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private  int numOfTabs;

    PagerAdapter(FragmentManager fm, int numOfTabs){

        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ServicoFragment();
            case 1:
                return new PecaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
