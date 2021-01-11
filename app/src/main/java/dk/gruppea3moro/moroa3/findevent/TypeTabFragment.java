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

import java.util.ArrayList;
import java.util.List;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.MainAktivityViewModel;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.adapters.GridAdapter;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class TypeTabFragment extends Fragment implements View.OnClickListener {
    FindEventViewModel findEventViewModel;
    MainAktivityViewModel mainActivityViewModel;
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_type_tab, container, false);

        //MainAcitivityViewModel----------------------------------------------------------------
        mainActivityViewModel = ((MainActivity)getActivity()).getMainAktivityViewModel();
        //Create adapter
        GridAdapter gridAdapter = new GridAdapter(getContext(), mainActivityViewModel,"type");
        mainActivityViewModel.getTypesMLD().observe(this, new Observer<List<TagDTO>>() {
            @Override
            public void onChanged(List<TagDTO> tagDTOs) {
                gridAdapter.notifyDataSetChanged();
            }
        });

        //Setup GridView
        gridView=(GridView)root.findViewById(R.id.type_tab_grid_view);

        //Setup adapter
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView textView =(TextView)((ViewGroup) view).getChildAt(0);
                String type = textView.getHint().toString();
                findEventViewModel.tapOnType(type);
            }
        });

        findEventViewModel = ViewModelProviders.of(getParentFragment())
                .get(FindEventViewModel.class);

        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
                int gridSize = mainActivityViewModel.getTypesMLD().getValue().size();
                TextView[] textViews = new TextView[gridSize];
                //TODO her opstår der problemer, fordi gridView.getChildCount() kun er de views, der er oprettet, dvs. dem som kan ses på skærmen
                //Mens vi er interesserede i at opdatere alle firkanterne - også dem uden for synsfeltet
                if (gridView.getChildCount()==0){
                    return;
                }

                for (int i = 0; i < gridSize; i++) {
                    ViewGroup gridItem = (ViewGroup) gridView.getChildAt(i);
                    textViews[i] = (TextView) gridItem.getChildAt(0);
                }

                //Update all search criteria related to Types
                String typeTextView;
                ArrayList<String> greenBoxes = new ArrayList<>();

                //Find all textViews that need to be green
                for (TextView textView : textViews) {
                    typeTextView = textView.getHint().toString();
                    for (String type:searchCriteria.getTypes()) {
                        if (type.equals(typeTextView)) {
                            greenBoxes.add(type);
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
            String type = ((TextView) v).getHint().toString();
            findEventViewModel.tapOnType(type);
        }
    }
}