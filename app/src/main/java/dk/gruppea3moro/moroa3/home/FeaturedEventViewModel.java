package dk.gruppea3moro.moroa3.home;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.DateTime;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class FeaturedEventViewModel extends ViewModel {

    private MutableLiveData<EventDTO> featuredEvent;
    private EventRepository eventRepository;


    public LiveData<EventDTO> getFeaturedEvent(){
        return featuredEvent;
    }
    public void init(){
        if (featuredEvent!=null){
            return;
        }
        eventRepository = EventRepository.get();
        featuredEvent = eventRepository.getFeaturedEvent();

        /*
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {

            //uiThread.post(() -> {});
        });

         */

    }

}
