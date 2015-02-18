package it.moondroid.themoviedblibrary;

import it.moondroid.themoviedblibrary.entity.Configuration;
import it.moondroid.themoviedblibrary.entity.Movie;
import it.moondroid.themoviedblibrary.entity.NowPlaying;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Marco on 14/02/2015.
 */
public interface TheMovieDbService {

    public static final String ENDPOINT = "http://api.themoviedb.org/3";
    public static final String LANGUAGE = "it";

    @GET("/configuration")
    void getConfiguration(Callback<Configuration> cb);

    @GET("/movie/now_playing")
    void getNowPlaying(Callback<NowPlaying> cb);

    @GET("/movie/{id}")
    void getMovie(@Path("id") int id, Callback<Movie> cb);
}
