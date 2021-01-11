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

import java.util.Calendar;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.EventTipDTO;


public class TipUsFragment extends Fragment implements View.OnClickListener, TextWatcher {
    Button sendEventTip;
    //DatePicker datePicker;
    TextView titleTv, whereTv, descriptionTv, emailContactInfo, eventLinkTv, whenTv, phoneContactInfo;
    EventTipDTO eventTipDTO;
    TextWatcher textWatcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tip_us, container, false);
        //datePicker = root.findViewById(R.id.eventChooseDate);
        //datePicker.setMinDate(System.currentTimeMillis() - 1000);
        eventTipDTO = new EventTipDTO();
        titleTv = root.findViewById(R.id.eventTitle);
        descriptionTv = root.findViewById(R.id.eventDescription);
        whereTv = root.findViewById(R.id.eventWhere);
        whenTv = root.findViewById(R.id.eventChooseDate);
        emailContactInfo = root.findViewById(R.id.emailContactInfo);
        eventLinkTv = root.findViewById(R.id.eventLink);
        phoneContactInfo = root.findViewById(R.id.phoneContactInfo);



        titleTv.addTextChangedListener(this);
        descriptionTv.addTextChangedListener(this);
        whereTv.addTextChangedListener(this);
        whenTv.addTextChangedListener(this);
        emailContactInfo.addTextChangedListener(this);
        eventLinkTv.addTextChangedListener(this);
        phoneContactInfo.addTextChangedListener(this);




        sendEventTip = root.findViewById(R.id.eventSendButton);
        sendEventTip.setOnClickListener(this);

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
        return root;
    }

    @Override
    public void onClick(View v) {
        //gem data
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

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
        System.out.println(eventTipDTO);
    }
}