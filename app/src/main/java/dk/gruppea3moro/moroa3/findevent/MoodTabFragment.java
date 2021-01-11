package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.SearchCriteria;


public class MoodTabFragment extends Fragment implements View.OnClickListener {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;
    TextView[] textViews;
    FindEventViewModel findEventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mood_tab, container, false);
        textView1 = root.findViewById(R.id.textView1);
        textView2 = root.findViewById(R.id.textView2);
        textView3 = root.findViewById(R.id.textView3);
        textView4 = root.findViewById(R.id.textView4);
        textView5 = root.findViewById(R.id.textView5);
        textView6 = root.findViewById(R.id.textView6);
        textView7 = root.findViewById(R.id.textView7);
        textView8 = root.findViewById(R.id.textView8);
        textView9 = root.findViewById(R.id.textView9);
        textView10 = root.findViewById(R.id.textView10);
        textView11 = root.findViewById(R.id.textView11);
        textView12 = root.findViewById(R.id.textView12);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        textView9.setOnClickListener(this);
        textView10.setOnClickListener(this);
        textView11.setOnClickListener(this);
        textView12.setOnClickListener(this);

        textViews = new TextView[]{textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
                textView9, textView10, textView11, textView12};

        findEventViewModel = ViewModelProviders.of(getParentFragment())
                .get(FindEventViewModel.class);

        findEventViewModel.getSearchCriteriaLD().observe(this, new Observer<SearchCriteria>() {
            @Override
            public void onChanged(SearchCriteria searchCriteria) {
                //Update all search criteria related to Moods
                String moodTextView;
                ArrayList<String> greenBoxes = new ArrayList<>();

                //Find all textViews that need to be green
                for (TextView textView : textViews) {
                    moodTextView = textView.getHint().toString();
                    for (String mood:searchCriteria.getMoods()) {
                        if (mood.equals(moodTextView)) {
                            greenBoxes.add(mood);
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
            String mood = ((TextView) v).getHint().toString();
            findEventViewModel.tapOnMood(mood);
        }
    }

    /*
    @Override
    public void onClick(View v) {
        if (v == textView1) {
            if (textView1.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView1.getHint() + "");
                textView1.setBackgroundResource(R.drawable.greenborder);
                textView1.setTag("green");
            } else {
                textView1.setBackgroundResource(R.drawable.yellowborder);
                textView1.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView1.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView2) {
            if (textView2.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView2.getHint() + "");
                textView2.setBackgroundResource(R.drawable.greenborder);
                textView2.setTag("green");
            } else {
                textView2.setBackgroundResource(R.drawable.yellowborder);
                textView2.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView2.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView3) {
            if (textView3.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView3.getHint() + "");
                textView3.setBackgroundResource(R.drawable.greenborder);
                textView3.setTag("green");
            } else {
                textView3.setBackgroundResource(R.drawable.yellowborder);
                textView3.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView3.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView4) {
            if (textView4.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView4.getHint() + "");
                textView4.setBackgroundResource(R.drawable.greenborder);
                textView4.setTag("green");
            } else {
                textView4.setBackgroundResource(R.drawable.yellowborder);
                textView4.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView4.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView5) {
            if (textView5.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView5.getHint() + "");
                textView5.setBackgroundResource(R.drawable.greenborder);
                textView5.setTag("green");
            } else {
                textView5.setBackgroundResource(R.drawable.yellowborder);
                textView5.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView5.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView6) {
            if (textView6.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView6.getHint() + "");
                textView6.setBackgroundResource(R.drawable.greenborder);
                textView6.setTag("green");
            } else {
                textView6.setBackgroundResource(R.drawable.yellowborder);
                textView6.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView6.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView7) {
            if (textView7.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView7.getHint() + "");
                textView7.setBackgroundResource(R.drawable.greenborder);
                textView7.setTag("green");
            } else {
                textView7.setBackgroundResource(R.drawable.yellowborder);
                textView7.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView7.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView8) {
            if (textView8.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView8.getHint() + "");
                textView8.setBackgroundResource(R.drawable.greenborder);
                textView8.setTag("green");
            } else {
                textView8.setBackgroundResource(R.drawable.yellowborder);
                textView8.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView8.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView9) {
            if (textView9.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView9.getHint() + "");
                textView9.setBackgroundResource(R.drawable.greenborder);
                textView9.setTag("green");
            } else {
                textView9.setBackgroundResource(R.drawable.yellowborder);
                textView9.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView9.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView10) {
            if (textView10.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView10.getHint() + "");
                textView10.setBackgroundResource(R.drawable.greenborder);
                textView10.setTag("green");
            } else {
                textView10.setBackgroundResource(R.drawable.yellowborder);
                textView10.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView10.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView11) {
            if (textView11.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView11.getHint() + "");
                textView11.setBackgroundResource(R.drawable.greenborder);
                textView11.setTag("green");
            } else {
                textView11.setBackgroundResource(R.drawable.yellowborder);
                textView11.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView11.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }

        if (v == textView12) {
            if (textView12.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addMood(textView12.getHint() + "");
                textView12.setBackgroundResource(R.drawable.greenborder);
                textView12.setTag("green");
            } else {
                textView12.setBackgroundResource(R.drawable.yellowborder);
                textView12.setTag("yellow");
                AppState.get().getSearchCriteria().removeMood(textView12.getHint() + "");
            }
            System.out.println("Stemning valgt: " + AppState.get().getSearchCriteria().getMoods());
        }
    }

     */

}