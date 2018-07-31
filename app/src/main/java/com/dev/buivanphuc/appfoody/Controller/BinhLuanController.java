package com.dev.buivanphuc.appfoody.Controller;

import com.dev.buivanphuc.appfoody.Model.BinhLuanModel;

import java.util.List;

public class BinhLuanController {
    BinhLuanModel binhLuanModel ;
    public BinhLuanController() {
        binhLuanModel = new BinhLuanModel();
    }
    public void ThemBinhLuan(String maQuanAn, BinhLuanModel binhLuanModel, List<String > listHinh)
    {
        binhLuanModel.ThemBinhLuan(maQuanAn,binhLuanModel,listHinh);
    }
}
