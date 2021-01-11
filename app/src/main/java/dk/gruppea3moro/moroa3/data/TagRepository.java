package dk.gruppea3moro.moroa3.data;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class TagRepository {
    private SheetReader sheetReader = new SheetReader();
    private final MutableLiveData<List<TagDTO>> moodMLDs = new MutableLiveData<>();
    private final MutableLiveData<List<TagDTO>> typeMLDs = new MutableLiveData<>();
    private final MutableLiveData<List<TagDTO>> zoneMLDs = new MutableLiveData<>();

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
            /*
            try {
                allTags = sheetReader.getAllTags();
            } catch (IOException e) {
                e.printStackTrace();
            }

             */
            uiThread.post(() -> {
                for (TagDTO tagDto : allTags) {
                    String cat = tagDto.getCategory();
                    if (cat.equals(TagDTO.MOOD_CATEGORY)){

                    }
                }
            });
        });
    }








}
