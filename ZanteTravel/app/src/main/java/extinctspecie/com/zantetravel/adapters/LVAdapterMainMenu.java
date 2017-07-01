package extinctspecie.com.zantetravel.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WorkSpace on 02-Jul-17.
 */

public class LVAdapterMainMenu extends BaseAdapter {


    List<String> menu;
    List<String> menuIcons;

    public LVAdapterMainMenu()
    {
        menu = new ArrayList<>();
        menuIcons = new ArrayList<>();
        populateLists();

    }

    private void populateLists() {

        menuIcons.add("info_icon");
        menuIcons.add("attraction_icon");
        menuIcons.add("accommodation_icon");
        menuIcons.add("food_icon");
        menuIcons.add("entertainment_icon");
        menuIcons.add("shopping_icon");
        menuIcons.add("activities_icon");
        menuIcons.add("beach_icon");
        menuIcons.add("car_rentals_icon");


        menu.add("About Zante");
        menu.add("Attractions");
        menu.add("Accommodation");
        menu.add("Food");
        menu.add("Entertainment");
        menu.add("Shopping");
        menu.add("Activities");
        menu.add("Beaches");
        menu.add("Car Rentals");
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


            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return null;

    }
    static class ViewHolder
    {
        ImageView ivMenuIcon;
        TextView tvMenu;
    }
}
