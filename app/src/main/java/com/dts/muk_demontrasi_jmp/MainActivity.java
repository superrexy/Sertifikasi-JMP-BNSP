package com.dts.muk_demontrasi_jmp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.muk_demontrasi_jmp.Informasi.Informasi;
import com.dts.muk_demontrasi_jmp.Login.Login;
import com.dts.muk_demontrasi_jmp.Mahasiswa.FormMahasiswa;
import com.dts.muk_demontrasi_jmp.Mahasiswa.Mahasiswa;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton btnLihatData = findViewById(R.id.btnLihatData);
        btnLihatData.setOnClickListener(view -> {
            Intent intent = new Intent(this, Mahasiswa.class);
            startActivity(intent);
        });

        MaterialButton btnInputData = findViewById(R.id.btnInputData);
        btnInputData.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormMahasiswa.class);
            startActivity(intent);
        });

        MaterialButton btnInformasi = findViewById(R.id.btnInformasi);
        btnInformasi.setOnClickListener(view -> {
            Intent intent = new Intent(this, Informasi.class);
            startActivity(intent);
        });
    }
}