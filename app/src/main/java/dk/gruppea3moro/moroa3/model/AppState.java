package dk.gruppea3moro.moroa3.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayDeque;
import java.util.Deque;

import dk.gruppea3moro.moroa3.burgermenu.AboutUsFragment;
import dk.gruppea3moro.moroa3.burgermenu.ContactUsFragment;
import dk.gruppea3moro.moroa3.findevent.FindEventFragment;
import dk.gruppea3moro.moroa3.home.FrontpageFragment;
import dk.gruppea3moro.moroa3.burgermenu.MenuFragment;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.findevent.ShowResultFragment;
import dk.gruppea3moro.moroa3.home.ShowEventFragment;
import dk.gruppea3moro.moroa3.burgermenu.TipUsFragment;
import dk.gruppea3moro.moroa3.profile.MyProfileFragment;

import static android.content.Context.MODE_PRIVATE;

public class AppState //extends Application
{
    private static AppState instance;
    private Deque<Integer> integerDeque = new ArrayDeque<>(5);
    private EventDTO lastViewedEvent;
    private EventTipDTO eventTipDTO;
    private int findEventVPposition;

    //STATIC METHODS--------------------------------------------------------------------------------
    public static AppState get() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void setTip(EventTipDTO eventTipDTO) {
        this.eventTipDTO = eventTipDTO;
    }

    public EventTipDTO getEventTipDTO() {
        return eventTipDTO;
    }



    //Returns layout id of the fragment corresponding to the item selected in bottom navigation bar
    public static int getFragmentLayoutId(int bnItemId) {
        switch (bnItemId) {
            case R.id.bn_home:
                return R.id.fragment_frontpage;
            case R.id.bn_right_now:
                return R.id.fragment_right_now;
            case R.id.bn_find_event:
                return R.id.fragment_find_event;
            case R.id.bn_my_profile:
                return R.id.fragment_my_profile;
            case R.id.bn_menu:
                return R.id.fragment_menu;
            default:
                //Indicate that something went wrong
                return 1;
        }
    }

    public static Fragment getFragmentFromLayoutId(int layoutId) {
        switch (layoutId) {
            case R.id.fragment_frontpage:
                return new FrontpageFragment();
            case R.id.fragment_right_now:
                return new ShowResultFragment();
            case R.id.fragment_show_result:
                return new ShowResultFragment();
            case R.id.fragment_show_event:
                return new ShowEventFragment();
            case R.id.fragment_find_event:
                return new FindEventFragment();
            case R.id.fragment_my_profile:
                return new MyProfileFragment();
            case R.id.fragment_menu:
                return new MenuFragment();
            case R.id.fragment_contact_us:
                return new ContactUsFragment();
            case R.id.fragment_tip_us:
                return new TipUsFragment();
            case R.id.fragment_about_us:
                return new AboutUsFragment();
            default:
                //If something went wrong
                return new FrontpageFragment();
        }
    }

    public static void resetPM() {
        //clear instance
        instance = new AppState();
    }

    //NON STATIC METHODS----------------------------------------------------------------------------

    public void saveToPM(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("AppState", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(get());
        System.out.println("Forsøger at gemme " + json);
        prefsEditor.putString("AppState", json);
        prefsEditor.apply();
        System.out.println("Har lige gemt " + json);
    }

    public void readFromPM(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("AppState", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("AppState", "");
        instance = gson.fromJson(json, AppState.class);
        System.out.println("Har lige læst " + json + " fra PM.");
    }

    public Deque<Integer> getIntegerDeque() {
        return integerDeque;
    }

    public void pushToBackstackDequeTop(int fragmentID) {
        //Get selected item id
        if (integerDeque.contains(fragmentID)) {
            //If deque already contains the item - remove
            integerDeque.remove(fragmentID);
        }

        //Push selected id in deque list - so it is on top
        integerDeque.push(fragmentID);
        //load fragment
    }


    public static SearchCriteria getRightNowSearchCriteria() {
        SearchCriteria sc = new SearchCriteria();

        long millis = System.currentTimeMillis();
        sc.setFromDate(DateTime.getDateFromTimeMillis(millis));
        sc.setToDate(DateTime.getDateFromTimeMillis(millis+3600000));

        return sc;
    }

    public EventDTO getLastViewedEvent() {
        return lastViewedEvent;
    }

    public void setLastViewedEvent(EventDTO lastViewedEvent) {
        this.lastViewedEvent = lastViewedEvent;
    }

    public int getFindEventVPposition() {
        return findEventVPposition;
    }

    public void setFindEventVPposition(int findEventVPposition) {
        this.findEventVPposition = findEventVPposition;
    }
}
