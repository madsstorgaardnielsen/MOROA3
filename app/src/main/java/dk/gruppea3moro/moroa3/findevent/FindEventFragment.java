package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class FindEventFragment extends Fragment {
    TabFragmentAdapter tabFragmentAdapter;
    ViewPager2 viewPager;
    static TabLayout tabLayout;
    FindEventViewModel findEventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find_event, container, false);
        tabLayout = root.findViewById(R.id.findEventTabLayout);

        //Sets up the viewmodel
        findEventViewModel = ViewModelProviders.of(this).get(FindEventViewModel.class);
        findEventViewModel.init();
        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Sets up the tabfragment adapter and viewpager
        tabFragmentAdapter = new TabFragmentAdapter(this);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(tabFragmentAdapter);

        //Method which that handles the logic on specific pages
        TabLayout tabLayout = view.findViewById(R.id.findEventTabLayout);
        new TabLayoutMediator(tabLayout, viewPager, this::getTabText).attach();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //Sets the searchresults, when user selects the last page
                if (position == 3) {
                    findEventViewModel.setResultEvents();
                }
                changeTabLayoutColor(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });

    }

    //Adding text to the tabs.
    public void getTabText(TabLayout.Tab tab, int position) {
        switch (position) {
            case 0:
                tab.setText(getString(R.string.tab_when));
                break;
            case 1:
                tab.setText(getString(R.string.tab_what));
                break;
            case 2:
                tab.setText(getString(R.string.tab_where));
                break;
            case 3:
                tab.setText(getString(R.string.tab_show));
                break;
            default:
                break;
        }
    }

    //If the view is destroyed, the searchcriteria is saved via appstate
    //The user can return the the view and continue the search
    @Override
    public void onDestroy() {
        super.onDestroy();
        AppState.get().setSearchCriteria(findEventViewModel.getSearchCriteriaLD().getValue());
    }

    //Makes sure the tab color is correct
    public void changeTabLayoutColor(int position) {
        switch (position) {
            case 0:
                tabLayout.setBackgroundColor(getResources().getColor(R.color.moroSalmonRedBackground));
                break;
            case 1:
                tabLayout.setBackgroundColor(getResources().getColor(R.color.moroYellowBackground));
                break;
            case 2:
                tabLayout.setBackgroundColor(getResources().getColor(R.color.moroPinkBackground));
                break;
            case 3:
                tabLayout.setBackgroundColor(getResources().getColor(R.color.moroGreenBackground));
                break;
        }
    }

    public SearchCriteria getSearchCriteria() {
        return findEventViewModel.getSearchCriteriaLD().getValue();
    }
}

class TabFragmentAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

    public TabFragmentAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new WhenTabFragment();
                break;
            case 1:
                fragment = new TypeOrMoodFragment();
                break;
            case 2:
                fragment = new WhereTabFragment();
                break;
            case 3:
                fragment = new ShowResultFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}