package com.dev.buivanphuc.appfoody.Controller;

import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Controller.Interfaces.ChiTietQuanAnInterface;
import com.dev.buivanphuc.appfoody.Model.WifiQuanAnModel;

import java.util.ArrayList;
import java.util.List;

public class ChiTietQuanAnController {
    WifiQuanAnModel wifiQuanAnModel;
    List<WifiQuanAnModel> wifiQuanAnModelList;
    public ChiTietQuanAnController()
    {
        wifiQuanAnModel = new WifiQuanAnModel();
        wifiQuanAnModelList= new ArrayList<>();
    }
    public void HienThiDanhSachWifiQuanAn(String maQuanAn, final TextView txtTenWifi, final TextView txtMatKhauWifi, final TextView txtNgayDang)
    {

        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                txtTenWifi.setText(wifiQuanAnModel.getTen());
                txtMatKhauWifi.setText(wifiQuanAnModel.getMatkhau());
                txtNgayDang.setText(wifiQuanAnModel.getNgaydang());
            }
        };

        wifiQuanAnModel.LayDanhSachWifiQuanAn(maQuanAn,chiTietQuanAnInterface);
    }
}
