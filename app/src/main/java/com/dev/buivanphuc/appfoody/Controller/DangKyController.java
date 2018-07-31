package com.dev.buivanphuc.appfoody.Controller;

import com.dev.buivanphuc.appfoody.Model.ThanhVienModel;

public class DangKyController {
    ThanhVienModel thanhVienModel;
    public DangKyController() {

    }
    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel,String uid)
    {
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel,uid);
    }
}
