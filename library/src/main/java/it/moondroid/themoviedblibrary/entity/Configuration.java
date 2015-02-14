package it.moondroid.themoviedblibrary.entity;

import java.util.ArrayList;

/**
 * Created by Marco on 14/02/2015.
 */
public class Configuration {

    public Images images;
    public ArrayList<String> change_keys;

    public Configuration(){
        // No args constructor
    }

    public static class Images {

        public String base_url;
        public String secure_base_url;
        public ArrayList<String> backdrop_sizes;
        public ArrayList<String> logo_sizes;
        public ArrayList<String> poster_sizes;
        public ArrayList<String> profile_sizes;
        public ArrayList<String> still_sizes;

        public Images(){
            // No args constructor
        }
    }

}
