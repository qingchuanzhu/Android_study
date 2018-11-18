package com.example.qingchuanzhu.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_CRIME_DATE = "crime_date";
    public static final String EXTRA_DATE = "com.example.qingchuanzhu.criminalintent.date";

    private Date crimeDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_DATE,date );
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        crimeDate = (Date) args.getSerializable(ARG_CRIME_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View datePicker = inflater.inflate(R.layout.dialog_date,null ,false );
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(crimeDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        ((DatePicker)datePicker).init(year,month ,day ,null );
        return new AlertDialog.Builder(getActivity())
                .setView(datePicker)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    DatePicker mDatePicker = (DatePicker)datePicker;
                    int yearPick = mDatePicker.getYear();
                    int monthPick = mDatePicker.getMonth();
                    int dayPick = mDatePicker.getDayOfMonth();
                    Date date = new GregorianCalendar(yearPick, monthPick, dayPick).getTime();
                    sendResult(Activity.RESULT_OK,date );
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date );
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode ,intent );
    }

}
