package it.moondroid.themoviedblibrary.entity;

import java.util.ArrayList;

/**
 * Created by Marco on 14/02/2015.
 */
public class NowPlaying {

    public Dates dates;
    public int page;
    public ArrayList<Result> results;
    public int total_pages;
    public int total_results;

    public NowPlaying(){
        // No args constructor
    }

    public static class Dates {

        public String minimum;
        public String maximum;

        public Dates(){
            // No args constructor
        }
    }
}
