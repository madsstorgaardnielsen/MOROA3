package dk.gruppea3moro.moroa3.burgermenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.EventTipDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class TipUsViewModel extends ViewModel {

    private MutableLiveData<EventTipDTO> eventTipFormula;

    public void setEventTipFormula(EventTipDTO eventTipDTO) {
        eventTipFormula.setValue(eventTipDTO);
    }

    public LiveData<EventTipDTO> getEventTipFormula() {
        return eventTipFormula;
    }

    public void init() {
        if (eventTipFormula != null) {
            return;
        }
        eventTipFormula = new MutableLiveData<>(new EventTipDTO());
    }
}
