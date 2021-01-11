package dk.gruppea3moro.moroa3.data;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.model.TagDTO;

public class TagRepository {
    private SheetReader sheetReader = new SheetReader();
    private final MutableLiveData<List<TagDTO>> moodsMLD = new MutableLiveData<>();
    private final MutableLiveData<List<TagDTO>> typesMLD = new MutableLiveData<>();
    private final MutableLiveData<List<TagDTO>> zonesMLD = new MutableLiveData<>();

    private TagRepository(){
        //Initialze list in MLD's
        moodsMLD.setValue(new ArrayList<>());
        typesMLD.setValue(new ArrayList<>());
        zonesMLD.setValue(new ArrayList<>());
    }

    private static TagRepository instance;
    public static TagRepository get() {
        if (instance == null) {
            instance = new TagRepository();
        }
        return instance;
    }

    public void setAllTagDTOs(){

        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {
            ArrayList<TagDTO> allTags = new ArrayList<>();
            try {
                allTags = sheetReader.getAllTags();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<TagDTO> finalAllTags = allTags;
            uiThread.post(() -> {
                for (TagDTO tagDto : finalAllTags) {
                    System.out.println(tagDto);
                    String cat = tagDto.getCategory();
                    if (cat.equals(TagDTO.MOOD_CATEGORY)){
                        moodsMLD.getValue().add(tagDto);
                    } else if (cat.equals(TagDTO.TYPE_CATEGORY)){
                        typesMLD.getValue().add(tagDto);
                    } else if (cat.equals(TagDTO.ZONE_CATEGORY)){
                        zonesMLD.getValue().add(tagDto);
                    }
                }
            });
        });
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
