package extinctspecie.com.zantetravel.services;

import java.util.List;

import extinctspecie.com.zantetravel.helpers.Information;
import extinctspecie.com.zantetravel.models.Business;
import extinctspecie.com.zantetravel.models.Images;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by WorkSpace on 03-Jul-17.
 */

public interface API {

    @GET("businesses/")
    Call<List<Business>> getAllBusinesses();

    @GET("businesses/{ID}")
    Call<List<Business>> getBusinessesWithGroupID(@Path("ID") int businessGroupID);

    @GET("images/{ID}")
    Call<List<Images>> getImagesOfBusinessWithID(@Path("ID") int businessID);

    public class Factory {

        private static API service;
        public static API getInstance() {
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Information.BASE_API_URL).build();
                service = retrofit.create(API.class);
                return service;
            } else {
                return service;
            }
        }

    }
}
