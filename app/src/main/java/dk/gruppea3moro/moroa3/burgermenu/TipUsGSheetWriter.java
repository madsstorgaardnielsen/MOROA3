package dk.gruppea3moro.moroa3.burgermenu;

import android.util.Log;

import dk.gruppea3moro.moroa3.model.EventTipDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class TipUsGSheetWriter {
    EventTipDTO eventTipDTO;

    public TipUsGSheetWriter(EventTipDTO eventTipDTO) {
        this.eventTipDTO = eventTipDTO;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/e/")
            .build();

    final QuestionsSpreadsheetWebService spreadsheetWebService = retrofit.create(QuestionsSpreadsheetWebService.class);

    public void postTip() {
        Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeQuestionnaire(
                eventTipDTO.getEventTitle()
                , eventTipDTO.getEventDescription()
                , eventTipDTO.getEventAddress()
                , eventTipDTO.getEventDate()
                , eventTipDTO.getContactEmail()
                , eventTipDTO.getContactPhoneNumber()
                , eventTipDTO.getEventLink());

        completeQuestionnaireCall.enqueue(callCallback);
    }

    private final Callback<Void> callCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Log.d("", "Submitted. " + response);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("", "Failed", t);
        }
    };
}


//Interface bruges til at POST til sheet
interface QuestionsSpreadsheetWebService {
    @POST("1FAIpQLSdMpKKViQR4iGKqPoQX6EhUhZitLIacGGrMYGyKaHKJL9SJfw/formResponse")
    @FormUrlEncoded
    Call<Void> completeQuestionnaire(
            @Field("entry.227105424") String title,
            @Field("entry.1628213198") String description,
            @Field("entry.1967743796") String where,
            @Field("entry.466442718") String when,
            @Field("entry.518533699") String email,
            @Field("entry.1064937375") String tlf,
            @Field("entry.117298309") String link
    );
}

/*
titel = entry.227105424
beskrivelse = entry.1628213198
hvor = entry.1967743796
hvornår = entry.466442718
email = entry.518533699
tlf = entry.1064937375
link = entry.117298309

sheet = https://docs.google.com/forms/u/0/d/e/1FAIpQLSdMpKKViQR4iGKqPoQX6EhUhZitLIacGGrMYGyKaHKJL9SJfw/formResponse

 */