package com.dts.muk_demontrasi_jmp.Mahasiswa.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.dts.muk_demontrasi_jmp.Mahasiswa.Model.MahasiswaModel;

import java.util.ArrayList;

public class MahasiswaRepository extends SQLiteOpenHelper {
    public MahasiswaRepository(Context context) {
        super(context, "db_mahasiswa", null, 1);
    }

    private static final String TABLE_NAME = "mahasiswa";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NIP = "nip";
    private static final String COLUMN_BIRTH_DATE = "birth_date";
    private static final String COLUMN_JENIS_KELAMIN = "jenis_kelamin";
    private static final String COLUMN_ALAMAT = "alamat";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NIP + " TEXT, " +
                COLUMN_BIRTH_DATE + " TEXT, " +
                COLUMN_JENIS_KELAMIN + " TEXT, " +
                COLUMN_ALAMAT + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<MahasiswaModel> getMahasiswa() {
        ArrayList<MahasiswaModel> listMahasiswa = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                MahasiswaModel mahasiswaModel = new MahasiswaModel();
                mahasiswaModel.setId(Integer.parseInt(cursor.getString(0)));
                mahasiswaModel.setName(cursor.getString(1));
                mahasiswaModel.setNip(cursor.getString(2));
                mahasiswaModel.setBirthDate(cursor.getString(3));
                mahasiswaModel.setJenisKelamin(cursor.getString(4));
                mahasiswaModel.setAlamat(cursor.getString(5));

                listMahasiswa.add(mahasiswaModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listMahasiswa;
    }

    public void insertMahasiswa(@NonNull MahasiswaModel mahasiswaModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_NAME + ", " +
                COLUMN_NIP + ", " +
                COLUMN_BIRTH_DATE + ", " +
                COLUMN_JENIS_KELAMIN + ", " +
                COLUMN_ALAMAT + ") VALUES ('" +
                mahasiswaModel.getName() + "', '" +
                mahasiswaModel.getNip() + "', '" +
                mahasiswaModel.getBirthDate() + "', '" +
                mahasiswaModel.getJenisKelamin() + "', '" +
                mahasiswaModel.getAlamat() + "')");
        db.close();
    }

    public void updateMahasiswa(@NonNull MahasiswaModel mahasiswaModel, @NonNull int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " +
                COLUMN_NAME + " = '" + mahasiswaModel.getName() + "', " +
                COLUMN_NIP + " = '" + mahasiswaModel.getNip() + "', " +
                COLUMN_BIRTH_DATE + " = '" + mahasiswaModel.getBirthDate() + "', " +
                COLUMN_JENIS_KELAMIN + " = '" + mahasiswaModel.getJenisKelamin() + "', " +
                COLUMN_ALAMAT + " = '" + mahasiswaModel.getAlamat() + "' WHERE " +
                COLUMN_ID + " = " + id);
        db.close();
    }

    public void deleteMahasiswa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id);
        db.close();
    }

    public MahasiswaModel getMahasiswaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id, null);
        MahasiswaModel mahasiswaModel = new MahasiswaModel();
        if (cursor.moveToFirst()) {
            mahasiswaModel.setId(Integer.parseInt(cursor.getString(0)));
            mahasiswaModel.setName(cursor.getString(1));
            mahasiswaModel.setNip(cursor.getString(2));
            mahasiswaModel.setBirthDate(cursor.getString(3));
            mahasiswaModel.setJenisKelamin(cursor.getString(4));
            mahasiswaModel.setAlamat(cursor.getString(5));
        }
        cursor.close();
        db.close();

        return mahasiswaModel;
    }
}
