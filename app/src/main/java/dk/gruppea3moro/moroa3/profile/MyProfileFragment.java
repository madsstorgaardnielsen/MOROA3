package dk.gruppea3moro.moroa3.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.findevent.ShowResultFragment;

public class MyProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ShowResultFragment showResultFragment = new ShowResultFragment();
        replaceFragment(showResultFragment);
        showResultFragment.setSavedEvents(true);
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.MyProfil_change, fragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }
}