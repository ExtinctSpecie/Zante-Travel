package extinctspecie.com.zantetravel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import extinctspecie.com.zantetravel.models.Business;

/**
 * Created by WorkSpace on 06-Jul-17.
 */

public class RVAdapterBusinessesID extends RecyclerView.Adapter<RVAdapterBusinessesID.MyViewHolder>{

    private List<Business> businessList;

    public RVAdapterBusinessesID(List<Business> businessList) {
        this.businessList = businessList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
        }
    }
}
