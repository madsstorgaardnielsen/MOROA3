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

    Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl("https://docs.google.com/forms/d/e/")
            .build();

    final ITipUsSheetService postToSheet = retrofit.create(ITipUsSheetService.class);

    public void postTip() {
        Call<Void> completeQuestionnaireCall = postToSheet
                .completeQuestionnaire(
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
            Log.d("", "POST Submitted. " + response);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("", "POST Failed", t);
        }
    };
}

//Interface bruges til at POST til sheet
interface ITipUsSheetService {
    //id til sheet
    @POST("1FAIpQLSdMpKKViQR4iGKqPoQX6EhUhZitLIacGGrMYGyKaHKJL9SJfw/formResponse")
    @FormUrlEncoded
    Call<Void> completeQuestionnaire(
            //entries til kolonnerne
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

hvis sheet skal ændres: https://docs.google.com/forms/d/1V_Np7J_I_upvCS_wy6x64ASIEBkE56AvHF9iRj-FevI/edit

hvis enstries skal findes igen: https://docs.google.com/forms/d/e/1FAIpQLSdMpKKViQR4iGKqPoQX6EhUhZitLIacGGrMYGyKaHKJL9SJfw/viewform

guide :https://blog.blundellapps.co.uk/tut-send-app-data-to-a-web-spreadsheet-google-sheets/
 */