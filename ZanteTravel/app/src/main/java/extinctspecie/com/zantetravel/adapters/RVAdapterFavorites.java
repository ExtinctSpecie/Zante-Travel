package extinctspecie.com.zantetravel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;
import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.models.Business;

/**
 * Created by WorkSpace on 28-Jul-17.
 */

public class RVAdapterFavorites extends RecyclerView.Adapter<RVAdapterFavorites.MyViewHolder>{

    private List<Business> businessList;

    Context context;

    View.OnClickListener mClickListener;

    public RVAdapterFavorites(List<Business> businesses , Context context ) {

        if(!businesses.isEmpty())
        {
            this.businessList = businesses;

            this.context = context;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_rv_row, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClickListener.onClick(v);
            }
        });
        return new MyViewHolder(itemView);

    }
    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Business business = businessList.get(position);
        holder.name.setText(business.getName());
        holder.type.setText(business.getType());
        holder.location.setText(business.getLocation());
        holder.shortDescription.setText(business.getShortDescription());

        if(business.getThumbnailURL() != null && !business.getThumbnailURL().isEmpty())
        {

            Picasso.get().load(business.getThumbnailURL()).networkPolicy(NetworkPolicy.OFFLINE).fit().into(holder.thumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    Log.v("Picasso","Offline thumbnails loaded");
                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(business.getThumbnailURL()).fit().into(holder.thumbnail, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.v("Picasso","Could not fetch image");
                        }
                    });
                }
            });
        }
        if(business.getDistanceToUser() > -1f)
        {
            holder.distanceToUser.setText(String.valueOf(business.getDistanceToUser()).substring(0,4) + " KM");
            holder.distanceToUser.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public Business getBusiness(int position)
    {
        return businessList.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //thumbnail /title / location / type
        private TextView name, type, location , shortDescription , distanceToUser;

        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.ivBusinessThumbnail);
            name = (TextView) view.findViewById(R.id.tvBusinessName);
            type = (TextView) view.findViewById(R.id.tvBusinessType);
            location = (TextView) view.findViewById(R.id.tvBusinessLocation);
            shortDescription = (TextView) view.findViewById(R.id.tvShortdescription);
            distanceToUser = (TextView) view.findViewById(R.id.tvDistanceToUser);
        }
    }

}
