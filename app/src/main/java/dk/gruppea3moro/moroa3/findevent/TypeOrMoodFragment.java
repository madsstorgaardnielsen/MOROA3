package dk.gruppea3moro.moroa3.findevent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import dk.gruppea3moro.moroa3.R;


public class TypeOrMoodFragment extends Fragment implements View.OnClickListener {

    TextView type_textView, stemning_textView;
    TypeTabFragment typeTabFragment = new TypeTabFragment();
    MoodTabFragment moodTabFragment = new MoodTabFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_type_or_mood, container, false);
        type_textView = root.findViewById(R.id.type_textView);
        stemning_textView = root.findViewById(R.id.stemning_textView);

        type_textView.setOnClickListener(this);
        stemning_textView.setOnClickListener(this);

        replaceFragment(typeTabFragment);
        type_textView.setTypeface(null, Typeface.BOLD);
        stemning_textView.setTypeface(null, Typeface.NORMAL);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v == type_textView) {
            replaceFragment(typeTabFragment);
            type_textView.setTypeface(null, Typeface.BOLD);
            stemning_textView.setTypeface(null, Typeface.NORMAL);

        } else if (v == stemning_textView) {
            replaceFragment(moodTabFragment);
            stemning_textView.setTypeface(null, Typeface.BOLD);
            type_textView.setTypeface(null, Typeface.NORMAL);
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_change, fragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }
}