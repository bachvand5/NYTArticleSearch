package com.vanistudio.a2_nytarticlesearch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by thuynh6 on 3/20/2016.
 */
public class dfSearchFilter extends DialogFragment {
    private EditText etBeginDate;
    private CheckBox cbDeskArts;
    private CheckBox cbDesksStyle;
    private CheckBox cbDeskSports;
    private RadioGroup rgSort;
    OnClickListener eOnClickListener;
    final Calendar myCalendar = Calendar.getInstance();

    public dfSearchFilter() {
    }

    public interface OnClickListener {
        public void onSaveClick(SearchFilter filter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            eOnClickListener = (OnClickListener) activity;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_setting, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        etBeginDate = (EditText) view.findViewById(R.id.etBeginDate);
        cbDeskArts = (CheckBox) view.findViewById(R.id.cbDeskArts);
        cbDesksStyle = (CheckBox) view.findViewById(R.id.cbDeskStyle);
        cbDeskSports = (CheckBox) view.findViewById(R.id.cbDeskSports);
        rgSort = (RadioGroup) view.findViewById(R.id.rgSort);
        Button btSave = (Button) view.findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = rgSort.getCheckedRadioButtonId();
                String sortNewest = "newest";
                if (radioButtonID != R.id.rbSortNewest) {
                    sortNewest = "oldest";
                }
                String newsDesk = "news_desk:(";
                if (cbDeskArts.isChecked()) {
                    newsDesk = newsDesk + "\"Arts\" ";
                }
                if (cbDesksStyle.isChecked()) {
                    newsDesk = newsDesk + "\"Fashion & Style\" ";
                }
                if (cbDeskSports.isChecked()) {
                    newsDesk = newsDesk + "\"Sports\" ";
                }
                newsDesk = newsDesk + ")";

                Date x=new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM");
                String month = sdf.format(x);
                String today = String.valueOf(x.getYear()+ 1900) + month + String.valueOf(x.getDate());

                SearchFilter filter = new SearchFilter(etBeginDate.getText().toString().replace("/",""), today, sortNewest, newsDesk );
                eOnClickListener.onSaveClick(filter);
                getDialog().dismiss();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public static dfSearchFilter newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        dfSearchFilter fragment = new dfSearchFilter();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etBeginDate.setText(sdf.format(myCalendar.getTime()));
    }

}
