package com.dts.muk_demontrasi_jmp.Mahasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.muk_demontrasi_jmp.Mahasiswa.Adapter.MahasiswaLVAdapter;
import com.dts.muk_demontrasi_jmp.Mahasiswa.Model.MahasiswaModel;
import com.dts.muk_demontrasi_jmp.Mahasiswa.Repository.MahasiswaRepository;
import com.dts.muk_demontrasi_jmp.R;

import java.util.ArrayList;

public class Mahasiswa extends AppCompatActivity {

    MahasiswaRepository mahasiswaRepository = new MahasiswaRepository(this);
    ArrayList<MahasiswaModel> listMahasiswa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mahasiswa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
    
    private void getData() {
        listMahasiswa = mahasiswaRepository.getMahasiswa();

        MahasiswaLVAdapter adapter = new MahasiswaLVAdapter(this, listMahasiswa);
        ListView listMahasiswaView = findViewById(R.id.listMahasiswaView);
        listMahasiswaView.setAdapter(adapter);
        listMahasiswaView.setOnItemClickListener((parent, view, position, id) -> {
            MahasiswaModel mahasiswa = listMahasiswa.get(position);
            Bundle extras = new Bundle();


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pilih Aksi");
            builder.setItems(new CharSequence[]{"Lihat Data", "Ubah Data", "Hapus Data" }, (dialog, which) -> {
                if (which == 0) {
                    Intent intent = new Intent(this, FormMahasiswa.class);
                    extras.putString(FormMahasiswa.PAGE_STATE, "detail");
                    extras.putInt(FormMahasiswa.MAHASISWA_ID, mahasiswa.getId());
                    intent.putExtras(extras);
                    startActivity(intent);
                } else if (which == 1) {
                    Intent intent = new Intent(this, FormMahasiswa.class);
                    extras.putString(FormMahasiswa.PAGE_STATE, "update");
                    extras.putInt(FormMahasiswa.MAHASISWA_ID, mahasiswa.getId());
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    builder.create().dismiss();

                    AlertDialog.Builder builderWarningDelete = new AlertDialog.Builder(this);
                    builderWarningDelete.setTitle("Apakah Anda Yakin?");
                    builderWarningDelete.setMessage("Data yang dihapus tidak dapat dikembalikan.");
                    builderWarningDelete.setPositiveButton("Ya", (dialog1, which1) -> {
                        mahasiswaRepository.deleteMahasiswa(mahasiswa.getId());
                        getData();
                    });

                    builderWarningDelete.setNegativeButton("Tidak", (dialog1, which1) -> dialog1.dismiss());

                    builderWarningDelete.create().show();
                }
            });

            builder.create().show();
        });
    }
}