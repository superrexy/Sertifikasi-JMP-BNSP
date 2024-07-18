package com.dts.muk_demontrasi_jmp.Mahasiswa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.muk_demontrasi_jmp.Mahasiswa.Model.MahasiswaModel;
import com.dts.muk_demontrasi_jmp.Mahasiswa.Repository.MahasiswaRepository;
import com.dts.muk_demontrasi_jmp.R;

import java.util.Calendar;

public class FormMahasiswa extends AppCompatActivity {

    MahasiswaRepository mahasiswaRepository = new MahasiswaRepository(this);

    static public final String PAGE_STATE = "PAGE_STATE";
    static public final String MAHASISWA_ID = "MAHASISWA_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_mahasiswa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Input Data");

        EditText etNIP = findViewById(R.id.etNIP);
        EditText etNama = findViewById(R.id.etNama);
        EditText etBirthDate = findViewById(R.id.etBirthDate);
        RadioButton rbLaki = findViewById(R.id.rbLaki);
        RadioButton rbPerempuan = findViewById(R.id.rbPerempuan);
        EditText etAlamat = findViewById(R.id.etAlamat);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        CardView cardView = findViewById(R.id.cardView);

        etBirthDate.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(FormMahasiswa.this, (datePicker, year1, month1, day1) -> {
                month1 = month1 + 1;
                String date = day1 + "/" + month1 + "/" + year1;
                etBirthDate.setText(date);
            }, year, month, day);

            datePickerDialog.show();
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String pageState = extras.getString(PAGE_STATE);
            if (pageState.equals("detail") || pageState.equals("update")) {

                MahasiswaModel mahasiswa = mahasiswaRepository.getMahasiswaById(extras.getInt(MAHASISWA_ID));
                if (mahasiswa == null) {
                    Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                etNIP.setText(mahasiswa.getNip());
                etNama.setText(mahasiswa.getName());
                etBirthDate.setText(mahasiswa.getBirthDate());
                etAlamat.setText(mahasiswa.getAlamat());
                if (mahasiswa.getJenisKelamin().equals("Laki-laki")) {
                    rbLaki.setChecked(true);
                } else {
                    rbPerempuan.setChecked(true);
                }

                if (pageState.equals("detail")) {
                    tvTitle.setText("Detail Data");

                    etNIP.setEnabled(false);
                    etNama.setEnabled(false);
                    etBirthDate.setEnabled(false);
                    rbLaki.setEnabled(false);
                    rbPerempuan.setEnabled(false);
                    etAlamat.setEnabled(false);

                    btnSimpan.setVisibility(Button.GONE);
                    cardView.setVisibility(CardView.GONE);

                    etBirthDate.setOnClickListener(null);
                }

                if (pageState.equals("update")) {
                    tvTitle.setText("Update Data");
                }
            }
        }

        btnSimpan.setOnClickListener(view -> {
            String nip = etNIP.getText().toString();
            String nama = etNama.getText().toString();
            String birthDate = etBirthDate.getText().toString();
            String jenisKelamin = rbLaki.isChecked() ? "Laki-laki" : "Perempuan";
            String alamat = etAlamat.getText().toString();

            if (nip.isEmpty()) {
                etNIP.setError("NIP tidak boleh kosong");
                etNIP.requestFocus();
                return;
            }

            if (nama.isEmpty()) {
                etNama.setError("Nama tidak boleh kosong");
                etNama.requestFocus();
                return;
            }

            if (birthDate.isEmpty()) {
                etBirthDate.setError("Tanggal lahir tidak boleh kosong");
                etBirthDate.requestFocus();
                return;
            }

            if (jenisKelamin.isEmpty()) {
                Toast.makeText(this, "Jenis kelamin tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            if (alamat.isEmpty()) {
                etAlamat.setError("Alamat tidak boleh kosong");
                etAlamat.requestFocus();
                return;
            }

            MahasiswaModel mahasiswa = new MahasiswaModel();
            mahasiswa.setNip(nip);
            mahasiswa.setName(nama);
            mahasiswa.setBirthDate(birthDate);
            mahasiswa.setJenisKelamin(jenisKelamin);
            mahasiswa.setAlamat(alamat);

            if (extras != null) {
                mahasiswaRepository.updateMahasiswa(mahasiswa, extras.getInt(MAHASISWA_ID));
                Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();

            } else {
                // Save data to database
                mahasiswaRepository.insertMahasiswa(mahasiswa);
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}