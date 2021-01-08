package dk.gruppea3moro.moroa3.findevent;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Date;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;

public class WhenTabFragment extends Fragment implements DatePicker.OnDateChangedListener {
    DatePicker picker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_when_tab, container, false);
        picker = (DatePicker) root.findViewById(R.id.when_datePicker);
        picker.setMinDate(System.currentTimeMillis() - 1000);

        picker.setOnDateChangedListener(this);
        return root;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Date date = new Date(year - 1900, monthOfYear, dayOfMonth, 0, 0);
        Date date2 = new Date(year - 1900, monthOfYear, dayOfMonth, 23, 59);

        AppState.get().getSearchCriteria().setFromDate(date);
        //System.out.println("FRA DATO=" + AppState.get().getSearchCriteria().getFromDate());

        AppState.get().getSearchCriteria().setToDate(date2);
        //System.out.println("TIL DATO=" + AppState.get().getSearchCriteria().getToDate());
    }
}