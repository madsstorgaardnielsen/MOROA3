package dk.gruppea3moro.moroa3.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.gruppea3moro.moroa3.data.EventRepository;
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
        shownEvent = EventRepository.get().getLastViewedEventMLD();

    }

}
