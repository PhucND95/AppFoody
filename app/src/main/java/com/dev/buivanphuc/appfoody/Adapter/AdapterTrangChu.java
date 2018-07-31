package com.dev.buivanphuc.appfoody.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dev.buivanphuc.appfoody.View.Fragments.AnGiFragment;
import com.dev.buivanphuc.appfoody.View.Fragments.ODauFragment;

public class AdapterTrangChu extends FragmentStatePagerAdapter {
    AnGiFragment anGiFragment;
    ODauFragment oDauFragment;

    public AdapterTrangChu(FragmentManager fm) {
        super(fm);

        anGiFragment = new AnGiFragment();
        oDauFragment = new ODauFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return oDauFragment;
            case 1:
                return anGiFragment;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
