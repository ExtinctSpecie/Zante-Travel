package extinctspecie.com.zantetravel.services;

import java.util.List;

import extinctspecie.com.zantetravel.helpers.Information;
import extinctspecie.com.zantetravel.models.Business;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by WorkSpace on 03-Jul-17.
 */

public interface API {
    @GET("businesses/")
    Call<List<Business>> getBusinesses();

    public class Factory {
        private static API service;

        public static API getInstance() {
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Information.BaseURL).build();
                service = retrofit.create(API.class);
                return service;
            } else {
                return service;
            }
        }

    }
}
