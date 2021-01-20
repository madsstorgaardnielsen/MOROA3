package dk.gruppea3moro.moroa3.burgermenu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.data.TipUsGSheetWriter;
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

        //If a user started filling the "tip us" formula, and left the fragment,
        //the data is saved, when the fragment is recreated the view is initialized with this data
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

        //Onclick method for the date textfield, opens a datepicker dialog and saves userinput in the eventtipdto object
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
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        sendEventTip = root.findViewById(R.id.eventSendButton);

        //Toast to let the user know the tip was submitted
        sendEventTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeTip = new TipUsGSheetWriter(eventTipDTO);
                writeTip.postTip();
                Toast.makeText(getActivity(), "Tippet er sendt!", Toast.LENGTH_LONG).show();
                clearText();
            }
        });
        return root;
    }

    //Used to clear text after the tip has been submitted
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
        //does not need implementation
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //does not need implementation
    }

    //Saves userinput in appstate, so it isnt lost if the user accidentally leaves the fragment before submitting the tip
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
    }
}