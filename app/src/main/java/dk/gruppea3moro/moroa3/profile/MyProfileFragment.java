package dk.gruppea3moro.moroa3.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.gruppea3moro.moroa3.MyProfileListFragment;
import dk.gruppea3moro.moroa3.R;

public class MyProfileFragment extends Fragment {

    MyProfileListFragment myProfileListFragment = new MyProfileListFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        replaceFragment(myProfileListFragment);
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.MyProfil_change, fragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }
}