package dk.gruppea3moro.moroa3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Fragment bottomBarFragment, mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Top bar

        //Main FL
        mainFragment = new FrontpageFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFL, mainFragment)  // tom container i layout
                .commit();

        //Bottombar
        bottomBarFragment = new BottomBarFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.bottomBarFL, bottomBarFragment)  // tom container i layout
                .commit();
    }

    @Override
    public void onClick(View v) {

    }
}