package extinctspecie.com.zantetravel.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.activities.FullScreenImagesActivity;

/**
 * Created by WorkSpace on 09-Jul-17.
 */

public class PABusinessGallery extends PagerAdapter
{

    private String TAG = this.getClass().getSimpleName();
    private List<String> items;
    Context context;
    private View.OnClickListener mClickListener;

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
    public Object instantiateItem(ViewGroup container,final int position) {

        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_business_gallery, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.ivImage);

        if(items.get(position) != null && !items.get(position).isEmpty())
        {

            Picasso.with(context).load(items.get(position)).networkPolicy(NetworkPolicy.OFFLINE).fit().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    Log.v("Picasso","Offline Image loaded loaded");
                }

                @Override
                public void onError() {
                    Picasso.with(context).load(items.get(position)).fit().into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Log.v("Picasso","Could not fetch image for gallery");
                        }
                    });
                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(v);
            }
        });

        container.addView(itemView);

        return itemView;

        //return super.instantiateItem(container, position);
    }
    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
