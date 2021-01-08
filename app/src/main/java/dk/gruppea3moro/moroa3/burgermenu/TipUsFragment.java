package dk.gruppea3moro.moroa3.burgermenu;

import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.datepicker.MaterialDatePicker;

import dk.gruppea3moro.moroa3.R;


public class TipUsFragment extends Fragment implements View.OnClickListener {
    Button chooseDate, saveEvent;

    //TODO Knappen som skal gemme variablerne mangler at blive implementeret

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tip_os, container, false);
        // Inflate the layout for this fragment

        chooseDate = root.findViewById(R.id.eventChooseDate);
        chooseDate.setOnClickListener(this);

        /*
        TODO Skal muligvis bare slettes afhænig af hvad MORO gerne vil have af valg af dato metode
        //Inputs data into the 3 Spinners
        //TODO Som det er nu vil man kunne vælge d. 31 februar. Der bliver ikke taget højde fro hvilken måned du har valgt.
        for (int i = 0; i < 3; i++) {
            int n = R.id.dateDay;
            int t = R.array.date;
            if(i==1){
                n=R.id.dateMonth;
                t = R.array.month;
            }else if(i==2){
                n=R.id.dateYear;
                t = R.array.year;
            }
            Spinner spinner = (Spinner)root.findViewById(n);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    t, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
        }
*/

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v == chooseDate) {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            MaterialDatePicker<Long> picker = builder.build();
            picker.show(getFragmentManager(), picker.toString());
        }
    }
}