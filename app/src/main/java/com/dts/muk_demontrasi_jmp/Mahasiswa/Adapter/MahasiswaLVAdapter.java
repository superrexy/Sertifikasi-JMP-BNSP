package com.dts.muk_demontrasi_jmp.Mahasiswa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dts.muk_demontrasi_jmp.Mahasiswa.Model.MahasiswaModel;
import com.dts.muk_demontrasi_jmp.R;

import java.util.ArrayList;

public class MahasiswaLVAdapter extends ArrayAdapter<MahasiswaModel> {
    public MahasiswaLVAdapter(Context context, ArrayList<MahasiswaModel> mahasiswaModels) {
        super(context, 0, mahasiswaModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_name, parent, false);
        }

        MahasiswaModel currentMahasiswa = getItem(position);
        TextView name = listItemView.findViewById(R.id.tvNama);

        assert currentMahasiswa != null;
        name.setText(currentMahasiswa.getName());

        return listItemView;
    }
}
