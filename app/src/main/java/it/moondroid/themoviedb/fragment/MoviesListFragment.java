package it.moondroid.themoviedb.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.moondroid.themoviedb.R;
import it.moondroid.themoviedb.TheMovieDbApp;
import it.moondroid.themoviedb.adapter.MoviesListRecyclerAdapter;
import it.moondroid.themoviedblibrary.entity.NowPlaying;
import it.moondroid.themoviedblibrary.entity.Result;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MoviesListFragment extends Fragment {

    @InjectView(R.id.rv_movies_list)
    protected RecyclerView moviesListView;

    private MoviesListRecyclerAdapter moviesListAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public MoviesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        setRetainInstance(true);

        loadMovies();


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies_list, container, false);
        ButterKnife.inject(this, view);
        initMoviesRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void renderMovies(List<Result> movies) {
        moviesListAdapter.updateItems(movies);
    }

    private void initMoviesRecyclerView() {
        moviesListView.setLayoutManager(new LinearLayoutManager(getActivity()
                .getApplicationContext()));
        moviesListView.setItemAnimator(new DefaultItemAnimator());
        moviesListAdapter = new MoviesListRecyclerAdapter(getActivity().getApplicationContext());
        moviesListView.setAdapter(moviesListAdapter);
//        moviesListView.setOnItemClickListener(new ClickRecyclerView.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, int position, long id) {
//                moviesListPresenter.onItemSelected(position);
//            }
//        });

        moviesListAdapter.setOnItemClickListener(new MoviesListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String title =  moviesListAdapter.getItem(position).title;
                Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMovies() {
        //view.showLoading();
        getActivity().setProgressBarIndeterminateVisibility(true);
        TheMovieDbApp.theMovieDbService.getNowPlaying(new Callback<NowPlaying>() {
            @Override
            public void success(NowPlaying nowPlaying, Response response) {
                renderMovies(nowPlaying.results);
                //view.hideLoading();
                getActivity().setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void failure(RetrofitError error) {
                getActivity().setProgressBarIndeterminateVisibility(false);
                //view.hideLoading();
            }
        });
    }
}
