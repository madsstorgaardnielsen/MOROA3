package dk.gruppea3moro.moroa3.findevent;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
//TODO klassen mangler at tage høje for hvilken side man var inde på, når Fragmentet vises ved tryk på tilbageknappen
public class FindEventViewModel extends AndroidViewModel {
    private MutableLiveData<SearchCriteria> searchCriteriaMLD;
    Application application;

    public FindEventViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public LiveData<SearchCriteria> getSearchCriteriaLD(){
        return searchCriteriaMLD;
    }
    public void init(){
        if (searchCriteriaMLD !=null){
            return;
        }
        searchCriteriaMLD = new MutableLiveData<>(new SearchCriteria());
    }

    public void tapOnZone(String zone){
        SearchCriteria sc = searchCriteriaMLD.getValue();
        List<String> selectedZones = sc.getZones();
        if (selectedZones.contains(zone)){
            selectedZones.remove(zone);
        } else {
            selectedZones.add(zone);
        }
        searchCriteriaMLD.setValue(sc);
    }

    public void tapOnMood(String mood){
        SearchCriteria sc = searchCriteriaMLD.getValue();
        List<String> selectedMoods = sc.getMoods();
        if (selectedMoods.contains(mood)){
            selectedMoods.remove(mood);
        } else {
            selectedMoods.add(mood);
        }
        searchCriteriaMLD.setValue(sc);
    }

    public void tapOnType(String type){
        SearchCriteria sc = searchCriteriaMLD.getValue();
        List<String> selectedTypes = sc.getTypes();
        if (selectedTypes.contains(type)){
            selectedTypes.remove(type);
        } else {
            selectedTypes.add(type);
        }
        searchCriteriaMLD.setValue(sc);
    }

    public void setResultEvents(){
        EventRepository.get().setResultEvents(searchCriteriaMLD.getValue(),application);
    }
}
