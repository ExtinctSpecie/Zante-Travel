package extinctspecie.com.zantetravel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import extinctspecie.com.zantetravel.R;
import extinctspecie.com.zantetravel.models.Business;

/**
 * Created by WorkSpace on 06-Jul-17.
 */

public class RVAdapterBusinessesID extends RecyclerView.Adapter<RVAdapterBusinessesID.MyViewHolder>{

    private List<Business> businessList;
    Context context;
    View.OnClickListener mClickListener;

    public RVAdapterBusinessesID(List<Business> businessList , Context context) {
        this.businessList = businessList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_rv_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Item Clicked: ",((TextView)v.findViewById(R.id.tvBusinessName)).getText().toString());
                mClickListener.onClick(v);
            }
        });
        return new MyViewHolder(itemView);

    }
    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Business business = businessList.get(position);
        holder.name.setText(business.getName());
        holder.type.setText(business.getType());
        holder.location.setText(business.getLocation());
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //thumbnail /title / location / type
        public TextView name, type, location;
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.ivBusinessThumbnail);
            name = (TextView) view.findViewById(R.id.tvBusinessName);
            type = (TextView) view.findViewById(R.id.tvBusinessType);
            location = (TextView) view.findViewById(R.id.tvBusinessLocation);
        }
    }
}
