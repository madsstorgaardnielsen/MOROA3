package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.model.TagDTO;


public class WhereTabFragment extends Fragment implements View.OnClickListener {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;
    TextView[] textViews;
    FindEventViewModel findEventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_where_tab, container, false);

        textView1 = root.findViewById(R.id.textView1);
        textView2 = root.findViewById(R.id.textView2);
        textView3 = root.findViewById(R.id.textView3);
        textView4 = root.findViewById(R.id.textView4);
        textView5 = root.findViewById(R.id.textView5);
        textView6 = root.findViewById(R.id.textView6);
        textView7 = root.findViewById(R.id.textView7);
        textView8 = root.findViewById(R.id.textView8);
        textView9 = root.findViewById(R.id.textView9);
        textView10 = root.findViewById(R.id.textView10);
        textView11 = root.findViewById(R.id.textView11);
        textView12 = root.findViewById(R.id.textView12);
        textViews = new TextView[]{textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
                textView9, textView10, textView11, textView12};

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        textView9.setOnClickListener(this);
        textView10.setOnClickListener(this);
        textView11.setOnClickListener(this);
        textView12.setOnClickListener(this);

        for (TagDTO tag :((MainActivity)getActivity()).getMainAktivityViewModel().getZonesMLD().getValue()) {
            System.out.println(tag.getText());
        }


        findEventViewModel = ViewModelProviders.of(getParentFragment()).get(FindEventViewModel.class);

        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
                //Update all search criteria related to Zones
                String zoneTextView;
                ArrayList<String> greenBoxes = new ArrayList<>();

                //Find all textViews that need to be green
                for (TextView textView : textViews) {
                    zoneTextView = textView.getHint().toString();
                    for (String zone:searchCriteria.getZones()) {
                        if (zone.equals(zoneTextView)) {
                            greenBoxes.add(zone);
                        }
                    }
                }

                //Make them green
                for (TextView textView:textViews) {
                    if (greenBoxes.contains(textView.getHint().toString())){
                        textView.setBackgroundResource(R.drawable.greenborder);
                    } else {
                        textView.setBackgroundResource(R.drawable.blackborder);

                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView){
            String zone = ((TextView) v).getHint().toString();
            findEventViewModel.tapOnZone(zone);
        }
    }
}