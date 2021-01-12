package dk.gruppea3moro.moroa3;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
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
}
