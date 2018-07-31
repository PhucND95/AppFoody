package com.dev.buivanphuc.appfoody.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.buivanphuc.appfoody.Controller.DangKyController;
import com.dev.buivanphuc.appfoody.Model.ThanhVienModel;
import com.dev.buivanphuc.appfoody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDangKy;
    EditText edtEmail, edtMatKhau, edtNhapLaiMatKhau;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DangKyController dangKyController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        firebaseAuth = FirebaseAuth.getInstance();

        btnDangKy = findViewById(R.id.btnDangKy);
        edtEmail = findViewById(R.id.edEmailDK);
        edtMatKhau = findViewById(R.id.edPasswordDK);
        edtNhapLaiMatKhau = findViewById(R.id.edNhapLaiPasswordDK);

        progressDialog = new ProgressDialog(this);
        btnDangKy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage(getString(R.string.dangxuly));
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        final String email = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        String nhapLaiMatKhau = edtNhapLaiMatKhau.getText().toString().trim();
        String thongbaoloi = getString(R.string.thongbaoloidangky);

        if (email.isEmpty()) {
            Toast.makeText(this,"Email không chính xác",Toast.LENGTH_SHORT).show();
        }else if(matKhau.isEmpty())
        {
            Toast.makeText(this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
        }else if(!nhapLaiMatKhau.equals(matKhau))
        {
            Toast.makeText(this,getString(R.string.thongbaonhaplaimatkhau),Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(email,matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();

                        ThanhVienModel thanhVienModel = new ThanhVienModel();
                        thanhVienModel.setHoten(email);
                        thanhVienModel.setHinhanh("user.png");
                        String uid= task.getResult().getUser().getUid();
                        dangKyController = new DangKyController();
                        dangKyController.ThemThongTinThanhVienController(thanhVienModel,uid);
                        Toast.makeText(DangKyActivity.this,getString(R.string.dangkythanhcong),Toast.LENGTH_SHORT).show();

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(DangKyActivity.this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
