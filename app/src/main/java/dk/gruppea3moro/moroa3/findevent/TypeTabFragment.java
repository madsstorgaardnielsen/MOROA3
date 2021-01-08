package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;

public class TypeTabFragment extends Fragment implements View.OnClickListener {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //TODO Man skal vente efter at have skiftet mellem Hvad og Hvor før man kan klikke
        View root = inflater.inflate(R.layout.fragment_type_tab, container, false);

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

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v == textView1) {
            if (textView1.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView1.getHint() + "");
                textView1.setBackgroundResource(R.drawable.greenborder);
                textView1.setTag("green");
            } else {
                textView1.setBackgroundResource(R.drawable.yellowborder);
                textView1.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView1.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView2) {
            if (textView2.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView2.getHint() + "");
                textView2.setBackgroundResource(R.drawable.greenborder);
                textView2.setTag("green");
            } else {
                textView2.setBackgroundResource(R.drawable.yellowborder);
                textView2.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView2.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView3) {
            if (textView3.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView3.getHint() + "");
                textView3.setBackgroundResource(R.drawable.greenborder);
                textView3.setTag("green");
            } else {
                textView3.setBackgroundResource(R.drawable.yellowborder);
                textView3.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView3.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView4) {
            if (textView4.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView4.getHint() + "");
                textView4.setBackgroundResource(R.drawable.greenborder);
                textView4.setTag("green");
            } else {
                textView4.setBackgroundResource(R.drawable.yellowborder);
                textView4.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView4.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView5) {
            if (textView5.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView5.getHint() + "");
                textView5.setBackgroundResource(R.drawable.greenborder);
                textView5.setTag("green");
            } else {
                textView5.setBackgroundResource(R.drawable.yellowborder);
                textView5.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView5.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView6) {
            if (textView6.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView6.getHint() + "");
                textView6.setBackgroundResource(R.drawable.greenborder);
                textView6.setTag("green");
            } else {
                textView6.setBackgroundResource(R.drawable.yellowborder);
                textView6.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView6.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView7) {
            if (textView7.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView7.getHint() + "");
                textView7.setBackgroundResource(R.drawable.greenborder);
                textView7.setTag("green");
            } else {
                textView7.setBackgroundResource(R.drawable.yellowborder);
                textView7.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView7.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView8) {
            if (textView8.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView8.getHint() + "");
                textView8.setBackgroundResource(R.drawable.greenborder);
                textView8.setTag("green");
            } else {
                textView8.setBackgroundResource(R.drawable.yellowborder);
                textView8.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView8.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView9) {
            if (textView9.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView9.getHint() + "");
                textView9.setBackgroundResource(R.drawable.greenborder);
                textView9.setTag("green");
            } else {
                textView9.setBackgroundResource(R.drawable.yellowborder);
                textView9.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView9.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView10) {
            if (textView10.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView10.getHint() + "");
                textView10.setBackgroundResource(R.drawable.greenborder);
                textView10.setTag("green");
            } else {
                textView10.setBackgroundResource(R.drawable.yellowborder);
                textView10.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView10.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView11) {
            if (textView11.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView11.getHint() + "");
                textView11.setBackgroundResource(R.drawable.greenborder);
                textView11.setTag("green");
            } else {
                textView11.setBackgroundResource(R.drawable.yellowborder);
                textView11.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView11.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }

        if (v == textView12) {
            if (textView12.getTag().equals("yellow")) {
                AppState.get().getSearchCriteria().addType(textView12.getHint() + "");
                textView12.setBackgroundResource(R.drawable.greenborder);
                textView12.setTag("green");
            } else {
                textView12.setBackgroundResource(R.drawable.yellowborder);
                textView12.setTag("yellow");
                AppState.get().getSearchCriteria().removeType(textView12.getHint() + "");
            }
            System.out.println("Type valgt: " + AppState.get().getSearchCriteria().getTypes());
        }
    }
}