package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.MainAktivityViewModel;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.adapters.GridAdapter;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.model.TagDTO;


public class WhereTabFragment extends Fragment implements View.OnClickListener {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;
    TextView[] textViews;
    private FindEventViewModel findEventViewModel;
    private GridView gridView;
    MainAktivityViewModel mainAktivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_where_tab, container, false);

        //MainAcitivityViewModel----------------------------------------------------------------
        mainAktivityViewModel = ((MainActivity)getActivity()).getMainAktivityViewModel();
        //Create adapter
        GridAdapter gridAdapter = new GridAdapter(getContext(),mainAktivityViewModel);
        mainAktivityViewModel.getZonesMLD().observe(this, new Observer<List<TagDTO>>() {
            @Override
            public void onChanged(List<TagDTO> tagDTOs) {
                gridAdapter.notifyDataSetChanged();
            }
        });

        //Setup GridView
        gridView=(GridView)root.findViewById(R.id.where_tab_grid_view);

        //Setup adapter
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {;
                System.out.println("DU TRYKKEDE");

            }
        });



        //FindEventViewModel----------------------------------------------------------------
        findEventViewModel = ViewModelProviders.of(getParentFragment()).get(FindEventViewModel.class);

        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
                /*
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
                */
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