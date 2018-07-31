package com.dev.buivanphuc.appfoody.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dev.buivanphuc.appfoody.Controller.ODauController;
import com.dev.buivanphuc.appfoody.Model.QuanAnModel;
import com.dev.buivanphuc.appfoody.R;

public class ODauFragment extends Fragment {
    ODauController oDauController;
    RecyclerView RecyclerView_ODau;
    ProgressBar progress_bar_odau;
    SharedPreferences sharedPreferences;
    NestedScrollView NestedScrollView_ODau;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);
        RecyclerView_ODau = view.findViewById(R.id.RecyclerView_ODau);
        progress_bar_odau = view.findViewById(R.id.progress_bar_odau);
        NestedScrollView_ODau = view.findViewById(R.id.NestedScrollView_ODau);

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);

        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latidude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longtitude", "0")));

        oDauController = new ODauController(getContext());
        oDauController.getDanhSachQuanAnController(getContext(), NestedScrollView_ODau, RecyclerView_ODau, progress_bar_odau, vitrihientai);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
