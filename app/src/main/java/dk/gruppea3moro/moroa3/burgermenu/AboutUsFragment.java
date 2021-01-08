package dk.gruppea3moro.moroa3.burgermenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dk.gruppea3moro.moroa3.R;


public class AboutUsFragment extends Fragment {

    ImageView omOsBillede;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);

        //omOsBillede.setImageResource(R.drawable.omoms_temp);

        return v;
    }
}