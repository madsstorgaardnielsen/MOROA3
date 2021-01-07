package dk.gruppea3moro.moroa3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.data.DataController;
import dk.gruppea3moro.moroa3.model.AppState;


public class MenuFragment extends Fragment implements View.OnClickListener {
    TextView contact_TextView, about_TextView, tip_Textview;
    Button readDbButton;//TODO remove this

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        contact_TextView = root.findViewById(R.id.kontakt_textView);
        about_TextView = root.findViewById(R.id.om_textView);
        tip_Textview = root.findViewById(R.id.tip_textView);

        contact_TextView.setOnClickListener(this);
        about_TextView.setOnClickListener(this);
        tip_Textview.setOnClickListener(this);

        readDbButton=root.findViewById(R.id.readDbButton);//TODO delete this
        readDbButton.setOnClickListener(this);//TODO delete this

        return root;
    }

    @Override
    public void onClick(View v) {
        MainActivity ma = ((MainActivity) getActivity());
        if (v == contact_TextView) {
            AppState.get().pushToBackstackDequeTop(R.id.fragment_contact_us);
            ContactUsFragment contactUsFragment = new ContactUsFragment();
            ma.loadFragment(contactUsFragment);
        } else if (v == about_TextView) {
            AppState.get().pushToBackstackDequeTop(R.id.fragment_about_us);
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            ma.loadFragment(aboutUsFragment);
        } else if (v == tip_Textview) {
            AppState.get().pushToBackstackDequeTop(R.id.fragment_tip_us);
            TipUsFragment tipUsFragment = new TipUsFragment();
            ma.loadFragment(tipUsFragment);
        } else if (v==readDbButton){//TODO delete this
            DataController.get().refreshDbInBackground(getContext());
        }
    }
}