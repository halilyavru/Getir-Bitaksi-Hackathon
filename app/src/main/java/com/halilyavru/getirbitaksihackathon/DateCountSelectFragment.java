package com.halilyavru.getirbitaksihackathon;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by halilmac on 10/02/2018.
 */

public class DateCountSelectFragment extends Fragment {

    private final String TAG = "DateCountSelectFragment";
    private int yearStart= -1,monthStart= -1,dayStart= -1,yearEnd= -1,monthEnd= -1,dayEnd= -1;
    private TextView tvStartDate,tvEndDate;

    public interface Listener{
        public void getRecordsListener(String startDate, String endDate, int minCount, int maxCount);
    }
    public Listener listener=null;
    public void setListener(Listener l){this.listener = l;};
    private String myFormat = "yyyy-MM-dd";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    private EditText etEnDusukDeger,etEnYuksekDeger;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_date_count_select,container,false);

        view.findViewById(R.id.btn_get_records).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean durum = false;
                if(yearStart == -1){
                    tvStartDate.setError("");
                    durum = true;
                }else{
                    tvStartDate.setError(null);
                }
                if(yearEnd == -1){
                    tvEndDate.setError("");
                    durum = true;
                }else{
                    tvEndDate.setError(null);
                }
                if( etEnDusukDeger.getText().toString().trim().length()==0 ){
                    etEnDusukDeger.setError("Bir değer giriniz.");
                    durum = true;
                }else{
                    etEnDusukDeger.setError(null);
                }
                if( etEnYuksekDeger.getText().toString().trim().length()==0 ){
                    etEnYuksekDeger.setError("Bir değer giriniz.");
                    durum = true;
                }else{
                    etEnYuksekDeger.setError(null);
                }
                if(!durum){
                    listener.getRecordsListener(tvStartDate.getText().toString(),tvEndDate.getText().toString(),
                            Integer.parseInt(etEnDusukDeger.getText().toString()),
                            Integer.parseInt(etEnYuksekDeger.getText().toString()));
                }

            }
        });

        tvStartDate = (TextView) view.findViewById(R.id.tv_baslangic_tarihi);
        tvEndDate = (TextView) view.findViewById(R.id.tv_bitis_tarihi);
        etEnDusukDeger = (EditText) view.findViewById(R.id.et_en_dusuk_deger);
        etEnYuksekDeger = (EditText) view.findViewById(R.id.et_en_yuksek_deger);

        view.findViewById(R.id.ll_baslangic_tarihi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCalendar(0);
            }
        });

        view.findViewById(R.id.ll_bitis_tarihi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCalendar(1);
            }
        });

        return view;


    }


    private void dialogCalendar(final int dateType){
        Calendar calendar = Calendar.getInstance();

        int year = -1,month = -1,day = -1;

        if(dateType == 0){
            if(yearStart == -1 || monthStart == -1 || dayStart == -1){
                yearStart = calendar.get(Calendar.YEAR);
                monthStart = calendar.get(Calendar.MONTH);
                dayStart = calendar.get(Calendar.DAY_OF_MONTH);
            }
            year = yearStart;
            month = monthStart;
            day = dayStart;

        }else if(dateType == 1){
            if(yearEnd == -1 || monthEnd == -1 || dayEnd == -1){
                yearEnd = calendar.get(Calendar.YEAR);
                monthEnd = calendar.get(Calendar.MONTH);
                dayEnd = calendar.get(Calendar.DAY_OF_MONTH);
            }
            year = yearEnd;
            month = monthEnd;
            day = dayEnd;

        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMnth, int selectedDayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(Calendar.YEAR, selectedYear);
                selectedCalendar.set(Calendar.MONTH, selectedMnth);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

                if(dateType == 0){
                    yearStart = selectedYear;
                    monthStart = selectedMnth;
                    dayStart = selectedDayOfMonth;
                    tvStartDate.setText(sdf.format(selectedCalendar.getTime()));
                    tvStartDate.setError(null);
                }else if(dateType == 1){
                    yearEnd = selectedYear;
                    monthEnd = selectedMnth;
                    dayEnd = selectedDayOfMonth;
                    tvEndDate.setText(sdf.format(selectedCalendar.getTime()));
                    tvEndDate.setError(null);
                }


            }
        }, year,month,day);
        datePickerDialog.getDatePicker();
        datePickerDialog.setTitle(null);
        datePickerDialog.show();
    }


}
