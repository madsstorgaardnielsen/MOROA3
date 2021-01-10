package dk.gruppea3moro.moroa3.findevent;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class FindEventViewModel extends ViewModel {
    private MutableLiveData<SearchCriteria> searchCriteriaMLD;

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



}
