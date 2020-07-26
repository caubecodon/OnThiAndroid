package vn.edu.ntu.nguyenbuiminhhieu.ktra123;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Calendar;


public class FirstFragment extends Fragment implements View.OnClickListener {

    EditText edtDate, edtMua, edtBan;
    RadioGroup rdgTiGia;
    ImageView imvDate;
    Button btnThem, btnXem;
    Spinner spnDichVu;
    String[] dsDichVu;
    NavController navController;
    ArrayList<String> lvMoney;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        ((MainActivity)getActivity()).navController = navController;
        lvMoney = ((MainActivity)getActivity()).lvMoney;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtDate = view.findViewById(R.id.edtDate);
        edtBan = view.findViewById(R.id.edtBan);
        edtMua = view.findViewById(R.id.edtMua);

        imvDate = view.findViewById(R.id.imvDate);
        rdgTiGia = view.findViewById(R.id.rdgTiGia);
        btnThem = view.findViewById(R.id.btnThem);
        btnXem = view.findViewById(R.id.btnXem);

        btnXem.setOnClickListener(this);
        btnThem.setOnClickListener(this);
        imvDate.setOnClickListener(this);

        spnDichVu = view.findViewById(R.id.spnDichVu);
        dsDichVu = ((MainActivity)getActivity()).getResources().getStringArray(R.array.dichvu);
        spnDichVu.setAdapter(new ArrayAdapter<>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1, dsDichVu));

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imvDate: thietLapNgay(); break;
            case R.id.btnThem: them(); break;
            case R.id.btnXem: xem(); break;
        }
    }

    private void thietLapNgay() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                StringBuilder builder = new StringBuilder();
                builder.append(dayOfMonth)
                        .append("/")
                        .append(month)
                        .append("/")
                        .append(year);
                edtDate.setText(builder.toString());
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void them() {
        String ngay = edtDate.getText().toString();
        String giaMua = edtMua.getText().toString();
        String giaBan = edtBan.getText().toString();
        String loaiTiGia = "";
        String dichVu = spnDichVu.getSelectedItem().toString();

        switch (rdgTiGia.getCheckedRadioButtonId()) {
            case R.id.rbtTheGioi: loaiTiGia = "Thế giới"; break;
            case R.id.rbtSJC: loaiTiGia = "SJC"; break;
            case R.id.rbtDOJI: loaiTiGia = "DOJI"; break;
        }

        if (ngay.length() > 0 && giaMua.length() > 0 && giaBan.length() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ngày: ")
                    .append(edtDate.getText().toString())
                    .append("\n")
                    .append("Loại tỉ giá: ")
                    .append(loaiTiGia)
                    .append("\n")
                    .append("Mua vào: ")
                    .append(edtMua.getText().toString())
                    .append("\t\t")
                    .append("Bán ra: ")
                    .append(edtBan.getText().toString())
                    .append("\n")
                    .append("Loại dịch vụ: ")
                    .append(dichVu);
            lvMoney.add(stringBuilder.toString());
            Toast.makeText(getActivity(), "Thêm ngoại tệ thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Hãy nhập đủ thông tin ngoại tệ", Toast.LENGTH_SHORT).show();
        }

    }

    private void xem() {
        navController.navigate(R.id.action_FirstFragment_to_SecondFragment);
    }
}
