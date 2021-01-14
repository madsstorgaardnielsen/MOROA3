package dk.gruppea3moro.moroa3.findevent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.model.TagDTO;

//TODO klassen mangler at tage høje for hvilken side man var inde på, når Fragmentet vises ved tryk på tilbageknappen
public class FindEventViewModel extends AndroidViewModel {
    private MutableLiveData<SearchCriteria> searchCriteriaMLD;
    Application application;


    public FindEventViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<SearchCriteria> getSearchCriteriaLD() {
        return searchCriteriaMLD;
    }

    public void init() {
        if (searchCriteriaMLD == null) {
            searchCriteriaMLD = new MutableLiveData<>();
            if (AppState.get().isIsKilled()) {
                searchCriteriaMLD.setValue(new SearchCriteria());
                AppState.get().setKilled(false);
                AppState.get().setFindEventVPposition(0);
            } else searchCriteriaMLD.setValue(AppState.get().getSearchCriteria());
        }
    }

    public void tapOnTag(String category, String tagId) {
        SearchCriteria sc = searchCriteriaMLD.getValue();
        List<String> selectedTags;
        switch (category) {
            case TagDTO.TYPE_CATEGORY:
                selectedTags = sc.getTypes();
                break;
            case TagDTO.MOOD_CATEGORY:
                selectedTags = sc.getMoods();
                break;
            case TagDTO.ZONE_CATEGORY:
                selectedTags = sc.getZones();
                break;
            default:
                selectedTags = new ArrayList<>();
        }

        if (selectedTags.contains(tagId)) {
            selectedTags.remove(tagId);
        } else {
            selectedTags.add(tagId);
        }
        searchCriteriaMLD.setValue(sc);
    }

    public void setResultEvents() {
        EventRepository.get().setResultEvents(searchCriteriaMLD.getValue(), application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
