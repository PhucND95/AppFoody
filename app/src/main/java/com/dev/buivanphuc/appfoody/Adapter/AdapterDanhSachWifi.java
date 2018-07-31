package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Model.WifiQuanAnModel;
import com.dev.buivanphuc.appfoody.R;

import java.util.List;

public class AdapterDanhSachWifi extends RecyclerView.Adapter<AdapterDanhSachWifi.ViewholderWifi> {
    Context context;
    int resource;
    List<WifiQuanAnModel> wifiQuanAnModelList;

    public AdapterDanhSachWifi(Context context, int resource, List<WifiQuanAnModel> wifiQuanAnModelList) {
        this.context = context;
        this.resource = resource;
        this.wifiQuanAnModelList = wifiQuanAnModelList;
    }

    public class ViewholderWifi extends RecyclerView.ViewHolder {
        TextView txtTenWifi, txtMatKhauWifi, txtNgayDang;

        public ViewholderWifi(View itemView) {
            super(itemView);
            txtTenWifi = itemView.findViewById(R.id.txtTenWifi);
            txtMatKhauWifi = itemView.findViewById(R.id.txtMatKhauWifi);
            txtNgayDang = itemView.findViewById(R.id.txtNgayDangWifi);
        }
    }

    @NonNull
    @Override
    public ViewholderWifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewholderWifi viewholderWifi = new ViewholderWifi(view);

        return viewholderWifi;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderWifi holder, int position) {
        WifiQuanAnModel wifiQuanAnModel = wifiQuanAnModelList.get(position);

        holder.txtTenWifi.setText(wifiQuanAnModel.getTen());
        holder.txtMatKhauWifi.setText(wifiQuanAnModel.getMatkhau());
        holder.txtNgayDang.setText(wifiQuanAnModel.getNgaydang());
    }

    @Override
    public int getItemCount() {
        return wifiQuanAnModelList.size();
    }
}
