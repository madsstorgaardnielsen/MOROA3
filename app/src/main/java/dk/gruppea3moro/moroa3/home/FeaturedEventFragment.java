package dk.gruppea3moro.moroa3.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class FeaturedEventFragment extends Fragment {
    TextView title, time, address, date, description;
    ImageView image;
    private FeaturedEventViewModel featuredEventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_featured_event, container, false);
        title = root.findViewById(R.id.featuredEventTitleTV);

        image = root.findViewById(R.id.featuredEventImageView);
        description = root.findViewById(R.id.descriptionTVShowEvent);
        featuredEventViewModel = ViewModelProviders.of(this).get(FeaturedEventViewModel.class);

        featuredEventViewModel.init();
        featuredEventViewModel.getFeaturedEvent().observe(this, new Observer<EventDTO>() {
            @Override
            public void onChanged(EventDTO eventDTO) {
                setupEventView(eventDTO);
            }
        });
        return root;
    }

    public void setupEventView(EventDTO eventDTO) {
        //Set last view event in ViewModel
        featuredEventViewModel.setLastViewedEvent();

        //Set text views
        title.setText(eventDTO.getTitle());
        description.setText(eventDTO.getSubtext());

        //Let Picasso handle the image
        Picasso.get().load(eventDTO.getImageLink()).placeholder(R.drawable.moro_logo).into(image);
    }
}