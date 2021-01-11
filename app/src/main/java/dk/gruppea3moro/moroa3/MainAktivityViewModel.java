package dk.gruppea3moro.moroa3;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import dk.gruppea3moro.moroa3.data.TagRepository;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class MainAktivityViewModel extends ViewModel {
    MutableLiveData<List<TagDTO>> moodsMLD;
    MutableLiveData<List<TagDTO>> typesMLD;
    MutableLiveData<List<TagDTO>> zonesMLD;

    public void init(){
        if (moodsMLD ==null && typesMLD==null && zonesMLD==null){
            TagRepository.get().setAllTagDTOs();
            moodsMLD = TagRepository.get().getMoodsMLD();
            typesMLD = TagRepository.get().getTypesMLD();
            zonesMLD = TagRepository.get().getZonesMLD();
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
}
