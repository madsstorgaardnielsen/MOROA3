package dk.gruppea3moro.moroa3.findevent;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Date;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class WhenTabFragment extends Fragment implements View.OnClickListener, DatePicker.OnDateChangedListener {
    //MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder;
    DatePicker picker;
    Button pickDate_button;
    TextView chosenDate_TextView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_when_tab, container, false);
        //pickDate_button = root.findViewById(R.id.pickDate_button);
        picker = (DatePicker) root.findViewById(R.id.when_datePicker);
        chosenDate_TextView = root.findViewById(R.id.chosenDate_TextView);
        //pickDate_button.setOnClickListener(this);

        /*materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        final MaterialDatePicker<?> materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");*/
        picker.setOnDateChangedListener(this);
        return root;
    }

    //TODO find ud af om kalender understøtter at man kan vælge et interval
    @Override
    public void onClick(View v) {
/*        Date date = new Date();
        if (v == pickDate_button) {
            int month = picker.getMonth() + 1;
            int day = picker.getDayOfMonth();
            int year = picker.getYear();
            chosenDate_TextView.setText(picker.getDayOfMonth() + "/" + month + "/" + picker.getYear());
            System.out.println("dag: "+picker.getDayOfMonth()+", måned: "+month+", år: "+picker.getYear());

            date.setDate(day);
            date.setMonth(month);
            date.setYear(year);

            AppState.get().getSearchCriteria().setFromDate(date);
            AppState.get().getSearchCriteria().setToDate(date);

            System.out.println("DATO="+AppState.get().getSearchCriteria().getFromDate());*/

            // CategoryTabFragment categoryTabFragment = new CategoryTabFragment();
            // replaceFragment(categoryTabFragment);
        //}
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFL, fragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Date date = new Date();
            int month = picker.getMonth() + 1;
            int day = picker.getDayOfMonth();
            //int year = picker.getYear();
            //chosenDate_TextView.setText(picker.getDayOfMonth() + "/" + month + "/" + picker.getYear());
            //System.out.println("dag: "+picker.getDayOfMonth()+", måned: "+month+", år: "+picker.getYear());
            //date.setTime((long) (86400000-1));
            date.setDate(dayOfMonth);
            date.setMonth(monthOfYear);
            date.setYear(year);

            AppState.get().getSearchCriteria().setFromDate(date);
        System.out.println("FRA DATO="+AppState.get().getSearchCriteria().getFromDate());

            AppState.get().getSearchCriteria().setToDate(date);

            System.out.println("TIL DATO="+AppState.get().getSearchCriteria().getFromDate());
    }
}