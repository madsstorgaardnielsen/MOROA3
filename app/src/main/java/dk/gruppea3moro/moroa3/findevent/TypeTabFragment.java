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
import dk.gruppea3moro.moroa3.MainActivityViewModel;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.adapters.GridAdapter;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class TypeTabFragment extends Fragment implements View.OnClickListener {
    FindEventViewModel findEventViewModel;
    MainActivityViewModel mainActivityViewModel;
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_type_tab, container, false);

        //MainAcitivityViewModel----------------------------------------------------------------
        mainActivityViewModel = ((MainActivity) getActivity()).getMainActivityViewModel();
        //Create adapter
        GridAdapter gridAdapter = new GridAdapter(getContext(), mainActivityViewModel, TagDTO.TYPE_CATEGORY);
        mainActivityViewModel.getTypesMLD().observe(this, new Observer<List<TagDTO>>() {
            @Override
            public void onChanged(List<TagDTO> tagDTOs) {
                gridAdapter.notifyDataSetChanged();
            }
        });

        //Setup GridView
        gridView = root.findViewById(R.id.type_tab_grid_view);

        //Setup adapter
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) ((ViewGroup) view).getChildAt(0);
                String type = textView.getHint().toString();
                findEventViewModel.tapOnTag(TagDTO.TYPE_CATEGORY, type);
                mainActivityViewModel.tapOnTag(TagDTO.TYPE_CATEGORY, type);
            }
        });

        findEventViewModel = ViewModelProviders.of(getParentFragment()).get(FindEventViewModel.class);

        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
                int gridSize = gridView.getChildCount();
                TextView[] textViews = new TextView[gridSize];

                if (gridSize == 0) {
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
                    for (String type : searchCriteria.getTypes()) {
                        if (type.equals(typeTextView)) {
                            greenBoxes.add(type);
                        }
                    }
                }

                //Changes border thickness if selected/unselected
                for (TextView textView : textViews) {
                    if (greenBoxes.contains(textView.getHint().toString())) { //If selected
                        textView.setBackgroundResource(R.drawable.thickyellowborder);
                    } else {//If not selected
                        textView.setBackgroundResource(R.drawable.yellowborder);
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {

    }
}