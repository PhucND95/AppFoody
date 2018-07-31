package com.dev.buivanphuc.appfoody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.buivanphuc.appfoody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class KhoiPhucMatKhauActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtDangKyKP;
    Button btnGuiEmailKP;
    EditText edEmailKP;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);

        firebaseAuth = FirebaseAuth.getInstance();
        txtDangKyKP = findViewById(R.id.txtDangKyKP);
        btnGuiEmailKP = findViewById(R.id.btnGuiEmailKP);
        edEmailKP = findViewById(R.id.edEmailKP);

        btnGuiEmailKP.setOnClickListener(this);
        txtDangKyKP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.txtDangKyKP:
                Intent iDangKy = new Intent(KhoiPhucMatKhauActivity.this,DangKyActivity.class);
                startActivity(iDangKy);
                break;
            case R.id.btnGuiEmailKP:
                String email = edEmailKP.getText().toString().trim();
                if(KiemTraEmail(email))
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(KhoiPhucMatKhauActivity.this,"Gửi Email thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"Email không hợp lê",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private boolean KiemTraEmail(String email)
    {
       return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
}
