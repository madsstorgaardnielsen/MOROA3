package dk.gruppea3moro.moroa3.burgermenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class TipUsViewModel extends ViewModel {

    private MutableLiveData<EventDTO> shownEvent;

    public LiveData<EventDTO> getShownEvent(){
        return shownEvent;
    }
    public void init(){
        if (shownEvent!=null){
            return;
        }
        //TODO overvej om det bør være fra EventReposioty i stedet
        shownEvent = EventRepository.get().getLastViewedEventMLD();

    }
}
