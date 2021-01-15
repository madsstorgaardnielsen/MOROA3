package dk.gruppea3moro.moroa3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.data.TagRepository;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class MainActivityViewModel extends AndroidViewModel {
    MutableLiveData<List<TagDTO>> moodsMLD;
    MutableLiveData<List<TagDTO>> typesMLD;
    MutableLiveData<List<TagDTO>> zonesMLD;
    MutableLiveData<Boolean> eventsAvailable;
    MutableLiveData<Boolean> tagsAvailable;
    Application application;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init(){
        if (moodsMLD ==null && typesMLD==null && zonesMLD==null){
            TagRepository.get().setAllTagDTOs(application);
            moodsMLD = TagRepository.get().getMoodsMLD();
            typesMLD = TagRepository.get().getTypesMLD();
            zonesMLD = TagRepository.get().getZonesMLD();
            tagsAvailable = TagRepository.get().getTagsAvalable();
            eventsAvailable = EventRepository.get().getEventsAvailable();
        }
    }

    public MutableLiveData<List<TagDTO>> getMoodsMLD() {
        return moodsMLD;
    }

    public MutableLiveData<List<TagDTO>> getTypesMLD() {
        return typesMLD;
    }

    public MutableLiveData<List<TagDTO>> getZonesMLD() {
        return zonesMLD;
    }

    public MutableLiveData<Boolean> getEventsAvailable() {
        return eventsAvailable;
    }

    public MutableLiveData<Boolean> getTagsAvailable() {
        return tagsAvailable;
    }

    public void tapOnTag(String tagCategory, String tag){
        List<TagDTO> tags;
        switch (tagCategory){
            case TagDTO.TYPE_CATEGORY:
                tags = typesMLD.getValue();
                break;
            case TagDTO.MOOD_CATEGORY:
                tags = moodsMLD.getValue();
                break;
            case TagDTO.ZONE_CATEGORY:
                tags = zonesMLD.getValue();
                break;
            default:
                tags=new ArrayList<>();
        }
        for (TagDTO i : tags) {
            if (i.getId().equals(tag)) {
                //invert selection
                i.setSelected(!i.isSelected());
            }
        }
    }
    public void setTagDTOs(){
        TagRepository.get().setAllTagDTOs(application);
    }

    public void setEventDTOs(){
        EventRepository.get().refreshDbInBackground(application);
    }
}
