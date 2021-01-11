package dk.gruppea3moro.moroa3.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.MainAktivityViewModel;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class GridAdapter extends BaseAdapter{
    private Context mContext;
    private MainAktivityViewModel mainAktivityViewModel;


    public GridAdapter(Context mContext, MainAktivityViewModel mainAktivityViewModel) {
        this.mContext=mContext;
        this.mainAktivityViewModel=mainAktivityViewModel;
    }

    @Override
    public int getCount() {
        if (mainAktivityViewModel.getZonesMLD().getValue() ==null){
            return 0;
        } else{
            return mainAktivityViewModel.getZonesMLD().getValue().size();
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
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.find_event_grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_item_text_view);

            //Get TagDTO
            TagDTO tagDTO = mainAktivityViewModel.getZonesMLD().getValue().get(position);

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
        } else {
            grid = (View) convertView;
        }


        return grid;
    }
}