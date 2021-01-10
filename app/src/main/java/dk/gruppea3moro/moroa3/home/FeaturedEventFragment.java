package dk.gruppea3moro.moroa3.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class FeaturedEventFragment extends Fragment {
    TextView title, startTime, address;
    ImageView image;
    private FeaturedEventViewModel featuredEventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_featured_event, container, false);
        title = root.findViewById(R.id.featuredEventTitleTV);

        startTime = root.findViewById(R.id.featuredEventTimeTV);
        address = root.findViewById(R.id.featuredEventAddressTV);
        image = root.findViewById(R.id.featuredEventImageView);

        featuredEventViewModel = ViewModelProviders.of(this).get(FeaturedEventViewModel.class);

        featuredEventViewModel.init();

        LiveData<EventDTO> ld =featuredEventViewModel.getFeaturedEvent();
        ld.observe(this, new Observer<EventDTO>() {
            @Override
            public void onChanged(EventDTO eventDTO) {
                setupEventView(eventDTO);
            }
        });

        //setupEventView(featuredEventViewModel.getFeaturedEvent().getValue());

        return root;
    }

    public void setupEventView(EventDTO eventDTO) {
        //Set text views
        title.setText(eventDTO.getTitle());
        startTime.setText(eventDTO.getStart().getTimeFormat());
        address.setText(eventDTO.getAddressDTO().toString());

        //Let Picasso handle the image
        Picasso.get().load(eventDTO.getImageLink()).into(image);
    }
}