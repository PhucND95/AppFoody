package com.dev.buivanphuc.appfoody.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.dev.buivanphuc.appfoody.Controller.Interfaces.ODauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanAnModel implements Parcelable {
    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich, hinhanhquanans;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    List<ThucDonModel> thucDons;
    DatabaseReference nodeRoot;
    List<Bitmap> bitmapList;
    long luotthich;
    long giatoida,giatoithieu;


    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanans = in.createStringArrayList();
        luotthich = in.readLong();
        giatoithieu = in.readLong();
        giatoida = in.readLong();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
    }

    public List<ThucDonModel> getThucDons() {
        return thucDons;
    }

    public void setThucDons(List<ThucDonModel> thucDons) {
        this.thucDons = thucDons;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public QuanAnModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public List<String> getHinhanhquanans() {
        return hinhanhquanans;
    }

    public void setHinhanhquanans(List<String> hinhanhquanans) {
        this.hinhanhquanans = hinhanhquanans;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataRoot;

    public void getDanhSachQuanAn(final ODauInterface oDauInterface, final Location vitrihientai, final int itemTiepTheo, final int itemDaCo) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot, oDauInterface, vitrihientai, itemTiepTheo, itemDaCo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if (dataRoot != null) {
            LayDanhSachQuanAn(dataRoot, oDauInterface, vitrihientai, itemTiepTheo, itemDaCo);
        } else {
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }

    }

    private void LayDanhSachQuanAn(DataSnapshot dataSnapshot, ODauInterface oDauInterface, final Location vitrihientai, int itemTiepTheo, int itemDaCo) {
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");

        int i = 0;
        // Láy danh sách quán ăn
        for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
            if (i == itemTiepTheo) {
                break;
            }
            if (i < itemDaCo) {
                i++;
                continue;
            }
            i++;
            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(valueQuanAn.getKey());

            // Lấy danh sách hình ảnh quán ăn theo mã
            DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
            List<String> hinhAnhList = new ArrayList<>();
            for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                hinhAnhList.add(valueHinhQuanAn.getValue(String.class));
            }
            quanAnModel.setHinhanhquanans(hinhAnhList);

            // Lấy danh sách bình luận của quán ăn
            List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);
                binhLuanModel.setMabinhluan(valueBinhLuan.getKey());

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                for (DataSnapshot valueHinhAnhBinhLuan : snapshotHinhAnhBinhLuan.getChildren()) {
                    hinhanhBinhLuanList.add(valueHinhAnhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhAnhBinhLuanList(hinhanhBinhLuanList);
                binhLuanModelList.add(binhLuanModel);
            }
            quanAnModel.setBinhLuanModelList(binhLuanModelList);

            // Lấy chi nhánh quán ăn

            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());

            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()) {
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);

                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());

                double khoangcach = (vitrihientai.distanceTo(vitriquanan)) / 1000;
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);

                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }
            quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);

            oDauInterface.getDanhSachQuanAnModel(quanAnModel);

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanans);
        dest.writeLong(luotthich);
        dest.writeLong(giatoithieu);
        dest.writeLong(giatoida);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(binhLuanModelList);
    }
}
