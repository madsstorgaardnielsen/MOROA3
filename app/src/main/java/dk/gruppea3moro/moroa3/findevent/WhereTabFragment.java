package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;


public class WhereTabFragment extends Fragment implements View.OnClickListener {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_where_tab, container, false);

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
            if (textView1.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView1.getHint() + "");
                textView1.setBackgroundResource(R.drawable.greenborder);
                textView1.setTag("green");
            } else {
                textView1.setBackgroundResource(R.drawable.blackborder);
                textView1.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView1.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
            System.out.println(AppState.get().getSearchCriteria().toString());
        }

        if (v == textView2) {
            if (textView2.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView2.getHint() + "");
                textView2.setBackgroundResource(R.drawable.greenborder);
                textView2.setTag("green");
            } else {
                textView2.setBackgroundResource(R.drawable.blackborder);
                textView2.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView2.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView3) {
            if (textView3.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView3.getHint() + "");
                textView3.setBackgroundResource(R.drawable.greenborder);
                textView3.setTag("green");
            } else {
                textView3.setBackgroundResource(R.drawable.blackborder);
                textView3.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView3.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView4) {
            if (textView4.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView4.getHint() + "");
                textView4.setBackgroundResource(R.drawable.greenborder);
                textView4.setTag("green");
            } else {
                textView4.setBackgroundResource(R.drawable.blackborder);
                textView4.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView4.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView5) {
            if (textView5.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView5.getHint() + "");
                textView5.setBackgroundResource(R.drawable.greenborder);
                textView5.setTag("green");
            } else {
                textView5.setBackgroundResource(R.drawable.blackborder);
                textView5.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView5.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView6) {
            if (textView6.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView6.getHint() + "");
                textView6.setBackgroundResource(R.drawable.greenborder);
                textView6.setTag("green");
            } else {
                textView6.setBackgroundResource(R.drawable.blackborder);
                textView6.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView6.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView7) {
            if (textView7.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView7.getHint() + "");
                textView7.setBackgroundResource(R.drawable.greenborder);
                textView7.setTag("green");
            } else {
                textView7.setBackgroundResource(R.drawable.blackborder);
                textView7.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView7.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView8) {
            if (textView8.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView8.getHint() + "");
                textView8.setBackgroundResource(R.drawable.greenborder);
                textView8.setTag("green");
            } else {
                textView8.setBackgroundResource(R.drawable.blackborder);
                textView8.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView8.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView9) {
            if (textView9.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView9.getHint() + "");
                textView9.setBackgroundResource(R.drawable.greenborder);
                textView9.setTag("green");
            } else {
                textView9.setBackgroundResource(R.drawable.blackborder);
                textView9.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView9.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView10) {
            if (textView10.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView10.getHint() + "");
                textView10.setBackgroundResource(R.drawable.greenborder);
                textView10.setTag("green");
            } else {
                textView10.setBackgroundResource(R.drawable.blackborder);
                textView10.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView10.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView11) {
            if (textView11.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView11.getHint() + "");
                textView11.setBackgroundResource(R.drawable.greenborder);
                textView11.setTag("green");
            } else {
                textView11.setBackgroundResource(R.drawable.blackborder);
                textView11.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView11.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }

        if (v == textView12) {
            if (textView12.getTag().equals("red")) {
                AppState.get().getSearchCriteria().addZone(textView12.getHint() + "");
                textView12.setBackgroundResource(R.drawable.greenborder);
                textView12.setTag("green");
            } else {
                textView12.setBackgroundResource(R.drawable.blackborder);
                textView12.setTag("red");
                AppState.get().getSearchCriteria().removeZone(textView12.getHint() + "");
            }
            System.out.println(AppState.get().getSearchCriteria().getZone());
        }
    }
}