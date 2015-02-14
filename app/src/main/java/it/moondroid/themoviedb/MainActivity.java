package it.moondroid.themoviedb;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.moondroid.themoviedb.fragment.MoviesListFragment;
import it.moondroid.themoviedblibrary.entity.NowPlaying;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements Callback<NowPlaying> {

    MoviesListFragment fragmentMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setProgressBarIndeterminate(true);
        setProgressBarIndeterminateVisibility(false);

        fragmentMovies = (MoviesListFragment) getFragmentManager().findFragmentById(R.id.frg_movies_list);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_refresh).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_refresh)
                        .colorRes(android.R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_refresh){
            TheMovieDbApp.theMovieDbService.getNowPlaying(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(NowPlaying nowPlaying, Response response) {
        Toast.makeText(this, "success! "+nowPlaying.total_results, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", nowPlaying.dates.minimum);
        fragmentMovies.renderMovies(nowPlaying.results);
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("MainActivity", error.getBody().toString());
    }
}
