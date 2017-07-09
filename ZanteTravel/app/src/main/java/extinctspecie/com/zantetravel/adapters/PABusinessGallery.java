package extinctspecie.com.zantetravel.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import extinctspecie.com.zantetravel.R;

/**
 * Created by WorkSpace on 09-Jul-17.
 */

public class PABusinessGallery extends PagerAdapter
{

    private List<String> items;
    Context context;

    public PABusinessGallery(Context context,List<String> items) {
        this.context = context;
        this.items = items;

    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_business_gallery, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivImage);

        Picasso.with(context).load(items.get(position)).fit().into(imageView);

        container.addView(itemView);

        return itemView;

        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
