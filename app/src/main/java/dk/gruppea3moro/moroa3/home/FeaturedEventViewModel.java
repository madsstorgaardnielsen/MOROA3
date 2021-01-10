package dk.gruppea3moro.moroa3.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class FeaturedEventViewModel extends ViewModel {

    private MutableLiveData<EventDTO> featuredEvent;


    public LiveData<EventDTO> getFeaturedEvent(){
        return featuredEvent;
    }
    public void init(){
        if (featuredEvent!=null){
            return;
        }
        featuredEvent = EventRepository.get().getFeaturedEvent();

    }

    public void setLastViewedEvent(){
        EventRepository.get().setLastViewedEvent(featuredEvent.getValue());
    }

}
