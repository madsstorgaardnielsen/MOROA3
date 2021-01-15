package dk.gruppea3moro.moroa3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Deque;

import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;

    public int width;
    public static int height;
    MainActivityViewModel mainActivityViewModel;
    private boolean appInUse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();
        mainActivityViewModel.getEventsAvailable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean available) {
                if (!available && appInUse) {
                    Toast.makeText(MainActivity.this, getString(R.string.msg_turn_network_on), Toast.LENGTH_LONG).show();
                    mainActivityViewModel.setEventDTOs();
                    mainActivityViewModel.setTagDTOs();
                    //Set Inuse false, so it doesnt call database more too much
                    appInUse = false;
                }
            }
        });
        mainActivityViewModel.getTagsAvailable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean available) {
                if (!available && appInUse) {
                    Toast.makeText(MainActivity.this, getString(R.string.msg_turn_network_on), Toast.LENGTH_LONG).show();
                    mainActivityViewModel.setTagDTOs();
                    mainActivityViewModel.setEventDTOs();
                    //Set Inuse false, so it doesnt call database more too much
                    appInUse = false;
                }
            }
        });


        //Read database from google sheet in background thread
        EventRepository.get().refreshDbInBackground(this);

        //Bottombar
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        //Load home fragment
        //loadFragment(new FrontpageFragment());
        //Set home as default fragment
        bottomNavigationView.setSelectedItemId(R.id.bn_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //Get selected item id
                        int id = item.getItemId();
                        int fragmentId = AppState.getFragmentLayoutId(id);
                        int currectFragmentPosition = getFragmentBotNavPosition(bottomNavigationView.getSelectedItemId());
                        int chosenFragmentPosition = getFragmentBotNavPosition(id);


                        System.out.println("NUVÆRENDE -> " + getFragmentBotNavPosition(bottomNavigationView.getSelectedItemId()));
                        System.out.println("VALGT -> " + getFragmentBotNavPosition(id));
                        //avoid loading the fragment youre already on when pressing it agian
                        if (bottomNavigationView.getSelectedItemId() == id) {
                            return true;
                        }
                        //Push fragment id to backstack deque
                        AppState.get().pushToBackstackDequeTop(fragmentId);

                        //load fragment
                        if (currectFragmentPosition < chosenFragmentPosition) {
                            AppState.get().setBotNavSelectGreater(true);
                            loadFragmentRightEntering(AppState.getFragmentFromLayoutId(fragmentId));
                        } else {
                            AppState.get().setBotNavSelectGreater(false);
                            loadFragmentLeftEntering(AppState.getFragmentFromLayoutId(fragmentId));
                        }
                        //loadFragment(AppState.getFragmentFromLayoutId(fragmentId));
                        return true;
                    }
                }
        );

        //Get rid of support action bar in top (Telling name of the app)
        getSupportActionBar().hide();

        //Set color of android's own statusbar in top.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlackBackground));
        }

        //Change color of android's own navigation bar, so it matches our navigation bar
        //Only if android version is compatible
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBlackBackground));
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    private int getFragmentBotNavPosition(int fragmentId) {
        if (fragmentId == 2131296346) {
            return 1;
        }
        if (fragmentId == 2131296349) {
            return 2;
        }
        if (fragmentId == 2131296345) {
            return 3;
        }
        if (fragmentId == 2131296348) {
            return 4;
        }
        if (fragmentId == 2131296347) {
            return 5;
        } else
            return 0;
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFL, fragment)
                .commit();
    }

    public void loadFragmentRightEntering(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.right_entering, R.anim.left_exit)
                .replace(R.id.mainFL, fragment)
                .commit();
    }

    public void loadFragmentLeftEntering(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.left_entering, R.anim.right_exit)
                .replace(R.id.mainFL, fragment)
                .commit();
    }

    public void onBackPressed() {
        //Get integerDeqye from AppState
        Deque<Integer> integerDeque = AppState.get().getIntegerDeque();

        //Pop to previous fragment
        integerDeque.pop();

        if (!integerDeque.isEmpty()) {
            //When deque is not empty
            //load fragment
            int id = integerDeque.peek();
            setBottonNavSelection(id);

            //To ensure the proper animation direction when back is pressed.
            boolean b = AppState.get().isBotNavSelectGreater();
            if (b) {
                loadFragmentLeftEntering(AppState.getFragmentFromLayoutId(id));
            } else {
                loadFragmentRightEntering(AppState.getFragmentFromLayoutId(id));
            }

            //loadFragment(AppState.getFragmentFromLayoutId(id));
        } else {
            //When deque list is empty
            //Finish activity
            finish();
        }
    }

    //Use to change between fragments and set the bottom navigation bar at the same time.
    //Only usable for fragments, that are present on bottom navigation bar
    public void setBottonNavSelection(int fragmentId) {
        switch (fragmentId) {
            case R.id.fragment_frontpage:
                //Set checked frontpage
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.fragment_right_now:
                //Set checked right now
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            case R.id.fragment_find_event:
                //case R.id.fragment_category_tab:
                //case R.id.fragment_when_tab:
                //case R.id.fragment_where_tab:
                //Set checked find event
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                break;
            case R.id.fragment_my_profile:
                //Set checked my profile
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                break;
            case R.id.fragment_menu:
            case R.id.fragment_about_us:
            case R.id.fragment_contact_us:
            case R.id.fragment_tip_us:
                //Set checked menu
                bottomNavigationView.getMenu().getItem(4).setChecked(true);
                break;
            default:
                //If it's not one of the bottom-nav fragments - do nothing
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        //Read AppState from PreferenceManager - it may be empty or it may be saved from when app was in use
        AppState.get().readFromPM(getApplicationContext());
        if (AppState.get().getIntegerDeque().size() == 0) {
            //Add home fragment in deque list
            AppState.get().getIntegerDeque().push(R.id.fragment_frontpage);
        }

        //Call onStart() from super class
        super.onStart();

        //Load top fragment
        int fragmentId = AppState.get().getIntegerDeque().peek();
        Fragment currentFragment = AppState.getFragmentFromLayoutId(fragmentId);
        setBottonNavSelection(fragmentId);
        loadFragment(currentFragment);
    }

    @Override
    public void onStop() {
        super.onStop();

        //If mainactivity is stopped - the app is no longer in RAM
        //The state is saved to PreferenceManager
        AppState.get().saveToPM(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppState.get().setKilled(true);
        AppState.get().saveToPM(getApplicationContext());
    }

    public MainActivityViewModel getMainActivityViewModel() {
        return mainActivityViewModel;
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        appInUse = true;
    }
}

       /* lad vær med at slet dem indtil videre :D

       21 31 29 63 46 homepage

        2131296349 lige nu

        2131296345 finde event

        2131296348 gemte events

         menu*/