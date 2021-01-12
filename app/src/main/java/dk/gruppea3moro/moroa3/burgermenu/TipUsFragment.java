package dk.gruppea3moro.moroa3.burgermenu;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventTipDTO;


public class TipUsFragment extends Fragment implements TextWatcher {
    Button sendEventTip;
    TextView titleTv, whereTv, descriptionTv, emailContactInfo, eventLinkTv, whenTv, phoneContactInfo;
    EventTipDTO eventTipDTO;
    TipUsGSheetWriter writeTip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tip_us, container, false);

        eventTipDTO = new EventTipDTO();
        titleTv = root.findViewById(R.id.eventTitle);
        descriptionTv = root.findViewById(R.id.eventDescription);
        whereTv = root.findViewById(R.id.eventWhere);
        whenTv = root.findViewById(R.id.eventChooseDate);
        emailContactInfo = root.findViewById(R.id.emailContactInfo);
        eventLinkTv = root.findViewById(R.id.eventLink);
        phoneContactInfo = root.findViewById(R.id.phoneContactInfo);

        //Hvis en bruger forlader formularen inden den er afsendt, vil det allerede indtastede data blive hentet fra appstate.
        if (AppState.get().getEventTipDTO() != null) {
            titleTv.setText(AppState.get().getEventTipDTO().getEventTitle());
            descriptionTv.setText(AppState.get().getEventTipDTO().getEventDescription());
            whereTv.setText(AppState.get().getEventTipDTO().getEventAddress());
            whenTv.setText(AppState.get().getEventTipDTO().getEventDate());
            emailContactInfo.setText(AppState.get().getEventTipDTO().getContactEmail());
            eventLinkTv.setText(AppState.get().getEventTipDTO().getEventLink());
            phoneContactInfo.setText(AppState.get().getEventTipDTO().getContactPhoneNumber());
        }

        titleTv.addTextChangedListener(this);
        descriptionTv.addTextChangedListener(this);
        whereTv.addTextChangedListener(this);
        whenTv.addTextChangedListener(this);
        emailContactInfo.addTextChangedListener(this);
        eventLinkTv.addTextChangedListener(this);
        phoneContactInfo.addTextChangedListener(this);

        whenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String chosenDate = selectedyear + "/" + (selectedmonth + 1) + "/" + selectedday;
                        eventTipDTO.setEventDate(chosenDate);
                        String shownDate = selectedday + "/" + (selectedmonth + 1) + "/" + selectedyear;
                        whenTv.setText(shownDate);
                        System.out.println(eventTipDTO.getEventDate());
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        sendEventTip = root.findViewById(R.id.eventSendButton);
        sendEventTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeTip = new TipUsGSheetWriter(eventTipDTO);
                writeTip.postTip();
                Toast.makeText(getActivity(), "Tippet er sendt!", Toast.LENGTH_LONG).show();
                clearText();

                //TODO fejlbesked hvis tippet ikke bliver gemt
            }
        });

        return root;
    }

    public void clearText() {
        titleTv.setText("");
        descriptionTv.setText("");
        whereTv.setText("");
        whenTv.setText("");
        emailContactInfo.setText("");
        eventLinkTv.setText("");
        phoneContactInfo.setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //skal ikke implementeres
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //skal ikke implementeres
    }

    @Override
    public void afterTextChanged(Editable s) {
        eventTipDTO.setEventTitle(titleTv.getText().toString());
        eventTipDTO.setEventDescription(descriptionTv.getText().toString());
        eventTipDTO.setEventAddress(whereTv.getText().toString());
        eventTipDTO.setEventDate(whenTv.getText().toString());
        eventTipDTO.setEventLink(eventLinkTv.getText().toString());
        eventTipDTO.setContactPhoneNumber(phoneContactInfo.getText().toString());
        eventTipDTO.setContactEmail(emailContactInfo.getText().toString());
        AppState.get().setTip(eventTipDTO);
        System.out.println(eventTipDTO);
    }
}