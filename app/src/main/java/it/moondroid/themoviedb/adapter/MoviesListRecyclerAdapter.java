package it.moondroid.themoviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import it.moondroid.themoviedb.R;
import it.moondroid.themoviedblibrary.entity.Result;

/**
 * Created by Marco on 14/02/2015.
 */
public class MoviesListRecyclerAdapter extends RecyclerView.Adapter<MoviesListRecyclerAdapter.ViewHolder> {

    private List<Result> movies = Collections.emptyList();
    private final Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public MoviesListRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void updateItems(List<Result> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public Result getItem(int position){
        return movies.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View modelView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item,
                viewGroup, false);

        return new ViewHolder(modelView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Result movie = movies.get(i);
        renderMovieView(movie, viewHolder);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    private void renderMovieView(Result movie, ViewHolder viewHolder) {
        String posterImage = "http://image.tmdb.org/t/p/w500/"+movie.poster_path;

        Picasso.with(context)
                .load(posterImage)
                .into(viewHolder.ivPosterImage);

        if (movie.vote_count > 0) {
            //viewHolder.tvMovieRating.setText(new RatingFormatter().format(movie.getVoteAverage()));
            viewHolder.tvMovieRating.setText(String.valueOf(movie.vote_average));
        }

        viewHolder.tvMovieTitle.setText(movie.title);
//        viewHolder.movieReleaseDate.setText(new ReleaseDateFormatter()
//                .format(movie.getReleaseDate()));
        viewHolder.movieReleaseDate.setText(movie.release_date);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_poster_image)
        protected ImageView ivPosterImage;
        @InjectView(R.id.tv_movie_title)
        protected TextView tvMovieTitle;
        @InjectView(R.id.tv_movie_rating)
        protected TextView tvMovieRating;
        @InjectView(R.id.tv_movie_release_date)
        protected TextView movieReleaseDate;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(v, getPosition());
                    }
                }
            });
        }
    }
}
