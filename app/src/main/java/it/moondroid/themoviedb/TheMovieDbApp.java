package it.moondroid.themoviedb;

import android.app.Application;
import android.util.Log;

import it.moondroid.themoviedblibrary.TheMovieDbService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Marco on 14/02/2015.
 */
public class TheMovieDbApp extends Application {

    public static TheMovieDbService theMovieDbService;

    @Override
    public void onCreate() {
        super.onCreate();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(TheMovieDbService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Log.i("RETROFITLOG", msg);
                    }
                })
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addQueryParam("api_key", getResources().getString(R.string.themoviedb_api_key));
                        request.addQueryParam("language", TheMovieDbService.LANGUAGE);
                    }
                })
                .build();

        theMovieDbService = restAdapter.create(TheMovieDbService.class);
    }
}
