package dk.gruppea3moro.moroa3.burgermenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;


public class MenuFragment extends Fragment implements View.OnClickListener {
    TextView contact_TextView, about_TextView, tip_Textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        contact_TextView = root.findViewById(R.id.kontakt_textView);
        about_TextView = root.findViewById(R.id.om_textView);
        tip_Textview = root.findViewById(R.id.tip_textView);

        contact_TextView.setOnClickListener(this);
        about_TextView.setOnClickListener(this);
        tip_Textview.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        MainActivity ma = ((MainActivity) getActivity());
        if (v == contact_TextView) {
            AppState.get().pushToBackstackDequeTop(R.id.fragment_contact_us);
            ContactUsFragment contactUsFragment = new ContactUsFragment();
            ma.loadFragmentRightEntering(contactUsFragment);
        } else if (v == about_TextView) {
            AppState.get().pushToBackstackDequeTop(R.id.fragment_about_us);
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            ma.loadFragmentRightEntering(aboutUsFragment);
        } else if (v == tip_Textview) {
            AppState.get().pushToBackstackDequeTop(R.id.fragment_tip_us);
            TipUsFragment tipUsFragment = new TipUsFragment();
            ma.loadFragmentRightEntering(tipUsFragment);
        }
    }
}