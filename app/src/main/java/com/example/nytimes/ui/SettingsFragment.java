package com.example.nytimes.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nytimes.R;
import com.example.nytimes.utils.Preferences;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends DialogFragment {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
    private static final int REQUEST_DATE = 0;

    @BindView(R.id.btnDatePicker)
    TextView mBtnDatePicker;
    @BindView(R.id.sSortOrder)
    Spinner mSSortOrder;
    @BindView(R.id.cbArts)
    CheckBox mCbArts;
    @BindView(R.id.cbFashionAndStyle)
    CheckBox mCbFashionAndStyle;
    @BindView(R.id.cbSports)
    CheckBox mCbSports;
    @BindColor(R.color.colorAccent)
    int mAccentColor;

    private Date mBeginDate;
    private Context mContext;

    public static SettingsFragment newInstance() {
        SettingsFragment frag = new SettingsFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        long beginDateInMillis = Preferences.getFilterBeginDate(mContext);
        if (beginDateInMillis != 0) {
            mBeginDate = new Date(beginDateInMillis);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_DATE) {
            Date pickedDate = DatePickerFragment.getPickedDate(data);

            if (pickedDate != null) {
                mBeginDate = pickedDate;
                mBtnDatePicker.setText(dateFormatter.format(mBeginDate));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getAlertDialogView();

        builder.setView(view);
        builder.setTitle(mContext.getString(R.string.search_filter));
        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (mBeginDate == null) {
                    Preferences.clearFilterBeginDate(mContext);
                } else {
                    Preferences.setFilterBeginDate(mContext, mBeginDate.getTime());
                }

                Preferences.setFilterSortOrder(
                        mContext, (String) mSSortOrder.getSelectedItem());
                Preferences.setIsFilterNewsDeskValueArts(mContext, mCbArts.isChecked());
                Preferences.setIsFilterNewsDeskValueFashionAndStyle(
                        mContext, mCbFashionAndStyle.isChecked());
                Preferences.setIsFilterNewsDeskValueSports(mContext, mCbSports.isChecked());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mBeginDate = null;

                mSSortOrder.setSelection(0);

                mCbArts.setChecked(false);
                mCbFashionAndStyle.setChecked(false);
                mCbSports.setChecked(false);
            }
        });


        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);

                negativeButton.setTextColor(mAccentColor);
                positiveButton.setTextColor(mAccentColor);
            }
        });

        return dialog;
    }

    @NonNull
    private View getAlertDialogView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_settings, null);
        ButterKnife.bind(this, view);

        final Date defaultDate = mBeginDate == null ? new Date() : mBeginDate;

        mBtnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment frag = DatePickerFragment.newInstance(defaultDate);
                frag.setTargetFragment(SettingsFragment.this, REQUEST_DATE);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                frag.show(fm, "DatePicker");
            }
        });

        if (mBeginDate != null) {
            mBtnDatePicker.setText(dateFormatter.format(mBeginDate));
        }

        ArrayAdapter<CharSequence> sortOrderAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.article_search_filter_sort_order_array,
                android.R.layout.simple_spinner_dropdown_item);
        mSSortOrder.setAdapter(sortOrderAdapter);

        int selectedSortOrder = 0;
        String storedSortOrder = Preferences.getFilterSortOrder(getActivity());
        if (!TextUtils.isEmpty(storedSortOrder)) {
            selectedSortOrder = Arrays.asList(
                    getResources().getStringArray(
                            R.array.article_search_filter_sort_order_array))
                    .indexOf(storedSortOrder);
            if (selectedSortOrder == -1) {
                selectedSortOrder = 0;
            }
        }
        mSSortOrder.setSelection(selectedSortOrder);


        mCbArts.setChecked(Preferences.isFilterNewsDeskValueArts(mContext));
        mCbFashionAndStyle.setChecked(Preferences.isFilterNewsDeskValueArts(mContext));
        mCbSports.setChecked(Preferences.isFilterNewsDeskValueSports(mContext));

        return view;
    }
}
