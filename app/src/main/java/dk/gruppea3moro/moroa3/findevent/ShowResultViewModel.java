package dk.gruppea3moro.moroa3.findevent;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class ShowResultViewModel  extends AndroidViewModel {
    private MutableLiveData<List<EventDTO>> resultEventsMLD;
    private Application application;

    public ShowResultViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public void init(SearchCriteria sc){
        if (resultEventsMLD !=null){
            return;
        }
        setResultEvents(sc);
    }

    public void setResultEvents(SearchCriteria searchCriteria){
        //Initialze result
        List<EventDTO> eventDTOs = new ArrayList<>();

        //Get events with EventRepository from BackgroundThread
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {
            //Gets event from searchCriteria via. EventRepository
             EventRepository.get().searchEvents(application, searchCriteria,eventDTOs);

            uiThread.post(() -> {
                resultEventsMLD.setValue(eventDTOs);
            });
        });
    }

    public LiveData<List<EventDTO>> getResultEventsLD(){
        return resultEventsMLD;
    }

}
