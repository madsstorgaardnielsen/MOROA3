package dk.gruppea3moro.moroa3.burgermenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.R;


public class ContactUsFragment extends Fragment implements View.OnClickListener {
    TextView tipus_TextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);
        tipus_TextView = root.findViewById(R.id.tipus_textView);
        tipus_TextView.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v == tipus_TextView) {
            TipUsFragment tipUsFragment = new TipUsFragment();
            replaceFragment(tipUsFragment);
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFL, fragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }
}