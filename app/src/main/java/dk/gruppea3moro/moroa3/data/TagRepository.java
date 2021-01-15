package dk.gruppea3moro.moroa3.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.model.TagDTO;

import static android.content.Context.MODE_PRIVATE;

public class TagRepository {
    private SheetReader sheetReader = new SheetReader();
    private final MutableLiveData<List<TagDTO>> moodsMLD = new MutableLiveData<>();
    private final MutableLiveData<List<TagDTO>> typesMLD = new MutableLiveData<>();
    private final MutableLiveData<List<TagDTO>> zonesMLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> couldRefresh = new MutableLiveData<>();
    private final MutableLiveData<Boolean> tagsAvalable = new MutableLiveData<>();


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

    public void setAllTagDTOs(Context context){
        Handler uiThread = new Handler();
        Executor bgThread = Executors.newSingleThreadExecutor();
        bgThread.execute(() -> {
            try {
                readTagsFromDB(context,uiThread);
                couldRefresh.postValue(true);
            } catch (IOException e) {
                e.printStackTrace();
                readTagsLocally(context);
                couldRefresh.postValue(false);
            }
        });
    }

    private void readTagsFromDB(Context context, Handler uiThread) throws IOException {
        ArrayList<TagDTO> allTags = new ArrayList<>();

        allTags = sheetReader.getAllTags();

        ArrayList<TagDTO> finalAllTags = allTags;
        uiThread.post(() -> {
            sortTags(finalAllTags);
        });

        saveTagsLocally(finalAllTags, context);
    }

    private void saveTagsLocally(ArrayList<TagDTO> allTags, Context context){
        TagList tagList = new TagList(allTags);
        SharedPreferences mPrefs = context.getSharedPreferences("tagList",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tagList);
        prefsEditor.putString("tagList", json);
        prefsEditor.apply();
        tagsAvalable.postValue(true);
    }

    private void readTagsLocally(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("tagList",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("tagList", "");
        if (json==""){
            tagsAvalable.postValue(false);
        } else {
            tagsAvalable.postValue(true);
            TagList tagList = gson.fromJson(json, TagList.class);
            sortTags(tagList.tags);
        }
    }

    private void sortTags(List<TagDTO> tagDTOs){
        for (TagDTO tagDto : tagDTOs) {
            //System.out.println(tagDto);
            String cat = tagDto.getCategory();
            if (cat.equals(TagDTO.MOOD_CATEGORY)){
                moodsMLD.getValue().add(tagDto);
            } else if (cat.equals(TagDTO.TYPE_CATEGORY)){
                typesMLD.getValue().add(tagDto);
            } else if (cat.equals(TagDTO.ZONE_CATEGORY)){
                zonesMLD.getValue().add(tagDto);
            }
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

    private class TagList{
        ArrayList<TagDTO> tags;
        TagList(ArrayList<TagDTO> tags){
            this.tags=tags;
        }
    }

    public MutableLiveData<Boolean> getCouldRefresh() {
        return couldRefresh;
    }

    public MutableLiveData<Boolean> getTagsAvalable() {
        return tagsAvalable;
    }
}
