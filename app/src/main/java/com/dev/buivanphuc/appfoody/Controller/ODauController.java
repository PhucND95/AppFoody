package com.dev.buivanphuc.appfoody.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.dev.buivanphuc.appfoody.Adapter.AdapterRecyclerViewODau;
import com.dev.buivanphuc.appfoody.Controller.Interfaces.ODauInterface;
import com.dev.buivanphuc.appfoody.Model.QuanAnModel;
import com.dev.buivanphuc.appfoody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ODauController {

    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerViewODau adapterRecyclerViewODau;
    int iteamdaco = 30;

    public ODauController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(Context context, NestedScrollView nestedScrollView, RecyclerView recyclerViewODau, final ProgressBar progress_bar_odau, final Location vitrihientai) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewODau.setLayoutManager(layoutManager);

        adapterRecyclerViewODau = new AdapterRecyclerViewODau(quanAnModelList, R.layout.custom_layout_recyclerview_odau, context);
        recyclerViewODau.setAdapter(adapterRecyclerViewODau);
        adapterRecyclerViewODau.notifyDataSetChanged();

        progress_bar_odau.setVisibility(View.VISIBLE);
        final ODauInterface oDauInterface = new ODauInterface() {
            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {
                final List<Bitmap> bitmapList = new ArrayList<>();
                for (String linkHinh : quanAnModel.getHinhanhquanans()) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmapList.add(bitmap);
                            quanAnModel.setBitmapList(bitmapList);
                            if (quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhquanans().size()) {
                                quanAnModelList.add(quanAnModel);
                                adapterRecyclerViewODau.notifyDataSetChanged();
                                progress_bar_odau.setVisibility(View.GONE);
                            }

                        }
                    });
                }

            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) !=null){
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        iteamdaco += 30;
                        quanAnModel.getDanhSachQuanAn(oDauInterface,vitrihientai,iteamdaco,iteamdaco-30);
                    }
                }
            }
        });
        quanAnModel.getDanhSachQuanAn(oDauInterface, vitrihientai, iteamdaco, 0);
    }
}
