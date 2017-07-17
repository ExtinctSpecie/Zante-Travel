package extinctspecie.com.zantetravel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.helpers.TypeFaces;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

public class LVAdapterMainMenu extends BaseAdapter {


    List<String> menu;
    List<Integer> menuIcons;
    private LayoutInflater layoutInflater;
    private Context context;

    public LVAdapterMainMenu(Context context)
    {
        //this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        menu = new ArrayList<>();
        menuIcons = new ArrayList<>();
        populateLists();

    }

    private void populateLists() {

        menuIcons.add(R.drawable.info_icon);
        menuIcons.add(R.drawable.attraction_icon);
        menuIcons.add(R.drawable.accommodation_icon);
        menuIcons.add(R.drawable.food_icon);
        menuIcons.add(R.drawable.entertainment_icon);
        menuIcons.add(R.drawable.shopping_icon);
        menuIcons.add(R.drawable.activities_icon);
        menuIcons.add(R.drawable.beach_icon);
        menuIcons.add(R.drawable.car_rentals_icon);
        menuIcons.add(R.drawable.car_rentals_icon);
        menuIcons.add(R.drawable.car_rentals_icon);


        menu.add("About Zante");
        menu.add("Attractions");
        menu.add("Accommodation");
        menu.add("Food");
        menu.add("Entertainment");
        menu.add("Shopping");
        menu.add("Activities");
        menu.add("Beaches");
        menu.add("Car Rentals");
        menu.add("Other");
        menu.add("Emergency Help");
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.lv_main_menu,null);
            viewHolder.tvMenuItem = (TextView) convertView.findViewById(R.id.tvMenuItem);
            viewHolder.ivMenuItem = (ImageView) convertView.findViewById(R.id.ivMenuItem);

            viewHolder.tvMenuItem.setTypeface(TypeFaces.getPunkBoy());

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvMenuItem.setText(menu.get(position));
        viewHolder.ivMenuItem.setImageResource(menuIcons.get(position));

        return convertView;

    }
    static class ViewHolder
    {
        ImageView ivMenuItem;
        TextView tvMenuItem;
    }
}
