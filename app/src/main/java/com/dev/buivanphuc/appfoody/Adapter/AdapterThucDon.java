package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Model.ThucDonModel;
import com.dev.buivanphuc.appfoody.R;

import java.util.List;

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.ViewHolderThucDon> {
    Context context;
    List<ThucDonModel> thucDonModelList;
    public AdapterThucDon(Context context, List<ThucDonModel> thucDonModelList)
    {
        this.context = context;
        this.thucDonModelList = thucDonModelList;
    }
    public class ViewHolderThucDon extends RecyclerView.ViewHolder {
        TextView txtTenThucDon;
        RecyclerView recyclerMonAn;
        public ViewHolderThucDon(View itemView) {
            super(itemView);
            txtTenThucDon = itemView.findViewById(R.id.txtTenThucDon);
            recyclerMonAn = itemView.findViewById(R.id.recyclerMonAn);
        }
    }
    @NonNull
    @Override
    public ViewHolderThucDon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_thucdon,parent,false);
        ViewHolderThucDon viewHolderThucDon = new ViewHolderThucDon(view);
        return viewHolderThucDon;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderThucDon holder, int position) {
        ThucDonModel thucDonModel = thucDonModelList.get(position);
        holder.txtTenThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn = new AdapterMonAn(context,thucDonModel.getMonAnModelList());
        holder.recyclerMonAn.setAdapter(adapterMonAn);


    }

    @Override
    public int getItemCount() {
        return thucDonModelList.size();
    }


}
