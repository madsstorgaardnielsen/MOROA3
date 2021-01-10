package dk.gruppea3moro.moroa3.home;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.DateTime;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class ShowEventViewModel extends ViewModel {

    private MutableLiveData<EventDTO> shownEvent;

    public LiveData<EventDTO> getShownEvent(){
        return shownEvent;
    }
    public void init(){
        if (shownEvent!=null){
            return;
        }
        //TODO overvej om det bør være fra EventReposioty i stedet
        shownEvent = EventRepository.get().getLastViewedEvent();

    }

}
