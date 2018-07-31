package com.dev.buivanphuc.appfoody.View;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dev.buivanphuc.appfoody.Adapter.AdapterTrangChu;
import com.dev.buivanphuc.appfoody.R;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    ViewPager viewPagerTrangChu;
    RadioButton rd_ODau, rd_AnGi;
    ImageView imgThemQuanAn;
    RadioGroup gr_oDau_anGi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        viewPagerTrangChu = findViewById(R.id.viewpager_trangchu);
        rd_ODau = findViewById(R.id.rdODau);
        rd_AnGi = findViewById(R.id.rdAnGi);
        gr_oDau_anGi = findViewById(R.id.gr_oDau_anGi);
        imgThemQuanAn = findViewById(R.id.imgThemQuanAn);
        AdapterTrangChu adapterTrangChu = new AdapterTrangChu(getSupportFragmentManager());

        viewPagerTrangChu.setAdapter(adapterTrangChu);
        viewPagerTrangChu.addOnPageChangeListener(this);
        gr_oDau_anGi.setOnCheckedChangeListener(this);
        imgThemQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iThemQuanAn = new Intent(TrangChuActivity.this,ThemQuanAnActivity.class);
                startActivity(iThemQuanAn);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rd_ODau.setChecked(true);
                break;
            case 1:
                rd_AnGi.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rdAnGi:
                viewPagerTrangChu.setCurrentItem(1);
                break;
            case R.id.rdODau:
                viewPagerTrangChu.setCurrentItem(0);
                break;
        }
    }
}
