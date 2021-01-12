package dk.gruppea3moro.moroa3.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.MainActivityViewModel;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class GridAdapter extends BaseAdapter{
    private Context mContext;
    private MainActivityViewModel mainActivityViewModel;
    private String category;//MUST BE EITHER "zone", "mood" or "type"


    public GridAdapter(Context mContext, MainActivityViewModel mainActivityViewModel, String category) {
        this.mContext=mContext;
        this.mainActivityViewModel = mainActivityViewModel;
        this.category=category.toLowerCase().trim();
    }

    @Override
    public int getCount() {
        if (category.equals(TagDTO.TYPE_CATEGORY)){
            if (mainActivityViewModel.getTypesMLD().getValue() ==null){
                return 0;
            } else{
                return mainActivityViewModel.getTypesMLD().getValue().size();
            }
        } else if (category.equals(TagDTO.MOOD_CATEGORY)){
            if (mainActivityViewModel.getMoodsMLD().getValue() ==null){
                return 0;
            } else{
                return mainActivityViewModel.getMoodsMLD().getValue().size();
            }
        } else{
            if (mainActivityViewModel.getZonesMLD().getValue() ==null){
                return 0;
            } else{
                return mainActivityViewModel.getZonesMLD().getValue().size();
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        //Init the Views
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.find_event_grid_item, null);
        } else {
            grid = (View) convertView;
        }

        //Logic for drawing boxes
        TextView textView = (TextView) grid.findViewById(R.id.grid_item_text_view);
        //Get TagDTO
        TagDTO tagDTO = getTagDto(category,position);
        //Set text formatted
        textView.setText(tagDTO.getFormattedText());
        //Set hint to id of tag //TODO fix newline som er blevet overset
        textView.setHint(tagDTO.getId());
        //Set border color according to category
        switch (tagDTO.getCategory()){
            case TagDTO.MOOD_CATEGORY:
            case TagDTO.TYPE_CATEGORY:
                textView.setBackgroundResource(R.drawable.yellowborder);
                break;
            case TagDTO.ZONE_CATEGORY:
                textView.setBackgroundResource(R.drawable.blackborder);
                break;
        }

        if (tagDTO.isSelected()){
            textView.setBackgroundResource(R.drawable.greenborder);
        } else {
            textView.setBackgroundResource(R.drawable.blackborder);
        }



        return grid;
    }

    private TagDTO getTagDto(String category,int position){
        TagDTO tagDTO;
        if (category.equals(TagDTO.TYPE_CATEGORY)){
            tagDTO = mainActivityViewModel.getTypesMLD().getValue().get(position);
        } else if (category.equals(TagDTO.MOOD_CATEGORY)){
            tagDTO = mainActivityViewModel.getMoodsMLD().getValue().get(position);
        } else if (category.equals(TagDTO.ZONE_CATEGORY)) {
            tagDTO = mainActivityViewModel.getZonesMLD().getValue().get(position);
        } else {
            System.out.println("FEJL I METODEN getTagDto() i GridAdapter - ikke korrekt kategori af tag");
            tagDTO = new TagDTO("","","","");
        }
        return tagDTO;
    }
}