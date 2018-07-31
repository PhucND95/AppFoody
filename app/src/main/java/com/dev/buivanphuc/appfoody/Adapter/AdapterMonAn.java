package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Model.DatMon;
import com.dev.buivanphuc.appfoody.Model.MonAnModel;
import com.dev.buivanphuc.appfoody.R;
import com.dev.buivanphuc.appfoody.View.ChiTietQuanAnActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.ViewholderMonAn> {
    Context context;
    List<MonAnModel> monAnModels;
    public static List<DatMon> datMonList = new ArrayList<>();

    public AdapterMonAn(Context context, List<MonAnModel> monAnModels) {
        this.context = context;
        this.monAnModels = monAnModels;
    }

    public class ViewholderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn, txtSoLuong;
        ImageView imgGiamSoLuong, imgTangSoLuong;

        public ViewholderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgGiamSoLuong = itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong = itemView.findViewById(R.id.imgTangSoLuong);
        }
    }

    @NonNull
    @Override
    public ViewholderMonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_monan, parent, false);
        ViewholderMonAn viewholderMonAn = new ViewholderMonAn(view);
        return viewholderMonAn;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewholderMonAn holder, int position) {
        final MonAnModel monAnModel = monAnModels.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());

        holder.txtSoLuong.setTag(0);
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = (int) holder.txtSoLuong.getTag();
                dem++;
                holder.txtSoLuong.setTag(dem);
                holder.txtSoLuong.setText(dem + "");

                DatMon datMonTag = (DatMon) holder.imgGiamSoLuong.getTag();
                if (datMonTag != null) {
                    AdapterMonAn.datMonList.remove(datMonTag);
                }
                DatMon datMon = new DatMon();
                datMon.setSoLuong(dem);
                datMon.setTenMonAn(monAnModel.getTenmon());

                holder.imgGiamSoLuong.setTag(datMon);

                AdapterMonAn.datMonList.add(datMon);
            }
        });
        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = (int) holder.txtSoLuong.getTag();
                if (dem != 0) {
                    dem--;
                    if (dem == 0) {
                        DatMon datMon = (DatMon) v.getTag();
                        AdapterMonAn.datMonList.remove(datMon);
                    }
                }
                holder.txtSoLuong.setTag(dem);
                holder.txtSoLuong.setText(dem + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnModels.size();
    }
}
