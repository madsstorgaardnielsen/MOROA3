package dk.gruppea3moro.moroa3.findevent;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Date;
import java.util.List;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.DateTime;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class WhenTabFragment extends Fragment implements DatePicker.OnDateChangedListener {
    DatePicker picker;
    FindEventViewModel findEventViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_when_tab, container, false);
        picker = (DatePicker) root.findViewById(R.id.when_datePicker);
        picker.setMinDate(System.currentTimeMillis() - 1000);
        picker.setOnDateChangedListener(this);
        findEventViewModel = ViewModelProviders.of(getParentFragment())
                .get(FindEventViewModel.class);

        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
                DateTime selectedDate = DateTime.getDateFromTimeMillis(System.currentTimeMillis());
                if (findEventViewModel.getSearchCriteriaLD().getValue().getFromDate() != null){
                    selectedDate = findEventViewModel.getSearchCriteriaLD().getValue().getFromDate();
                }
                picker.updateDate(selectedDate.getYearInt(),selectedDate.getMonthInt()-1,selectedDate.getDayInt());
            }
        });

        return root;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //Set fromDate and toDate to the same day, but the first and last minute respectively
        DateTime fromDate = new DateTime("" + dayOfMonth, "" + (monthOfYear+1), "" + year, "00", "00");
        DateTime toDate = new DateTime("" + dayOfMonth, "" + (monthOfYear+1), "" + year, "23", "59");
        findEventViewModel.setSCFromDate(fromDate);
        findEventViewModel.setSCToDate(toDate);
    }
}