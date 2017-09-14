package id.muhmirza.popularmoviestage2.view.presentation.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.muhmirza.popularmoviestage2.App;
import id.muhmirza.popularmoviestage2.R;
import id.muhmirza.popularmoviestage2.data.controller.MovieController;
import id.muhmirza.popularmoviestage2.data.event.MovieErrorEvent;
import id.muhmirza.popularmoviestage2.data.event.MovieEvent;
import id.muhmirza.popularmoviestage2.data.model.Movie;
import id.muhmirza.popularmoviestage2.data.util.Constant;
import id.muhmirza.popularmoviestage2.view.presentation.adapter.MoviesAdapter;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_main_movies)
    RecyclerView mMainMovies;

    @BindView(R.id.pb_loading_bar)
    ProgressBar loadingBar;

    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    private EventBus mEventBus;
    private MoviesAdapter mMoviesAdapter;
    private MovieController mMovieController;
    private int mPage;
    private Constant.Data.MOVIE_LIST_TITLE movieListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEventBus = App.getInstance().getEventBus();
        mEventBus.register(this);

        mMovieController = new MovieController();

        initView();

        if (savedInstanceState != null) {
            List<Movie> movieList = Arrays.asList(App.getInstance().getGson().fromJson(savedInstanceState.getString(Constant.Data.EXTRA_MOVIE_LIST), Movie[].class));
            mMoviesAdapter.setData(movieList);

            MainActivity.this.setTitle(savedInstanceState.getString(Constant.Data.EXTRA_TITLE));
            mMainMovies.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(Constant.Data.EXTRA_MOVIE_LIST_STATE));
            mPage = savedInstanceState.getInt(Constant.Data.EXTRA_PAGE);
            return;
        }

        errorLayout.setVisibility(View.GONE);
        loadingBar.setVisibility(View.VISIBLE);

        mPage = 1;
        setPopularMovies(mPage);
    }

    private void initView() {
        ButterKnife.bind(this);

        int columns = Constant.Function.getColumnsCount(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, columns);
        mMainMovies.setLayoutManager(layoutManager);
        mMainMovies.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter();
        mMainMovies.setAdapter(mMoviesAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                errorLayout.setVisibility(View.GONE);
                mMoviesAdapter.setData(new ArrayList<Movie>());
                loadingBar.setVisibility(View.VISIBLE);
                mPage = 1;
                setPopularMovies(mPage);
                break;
            case R.id.action_top_rated:
                errorLayout.setVisibility(View.GONE);
                mMoviesAdapter.setData(new ArrayList<Movie>());
                loadingBar.setVisibility(View.VISIBLE);
                mPage = 1;
                setTopRatedMovies(mPage);
                break;
            case R.id.action_favorite:
                errorLayout.setVisibility(View.GONE);
                mMoviesAdapter.setData(new ArrayList<Movie>());
                loadingBar.setVisibility(View.VISIBLE);
                mPage = 1;
                setFavoriteMovies();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavoriteMovies() {
        MainActivity.this.setTitle(getString(R.string.favorite_movies));

        this.movieListTitle = Constant.Data.MOVIE_LIST_TITLE.FAVORITE;
        mMovieController.getFavoriteMovies(this);
    }

    private void setTopRatedMovies(int page) {
        MainActivity.this.setTitle(getString(R.string.top_rated_movies));

        this.mPage = page;
        this.movieListTitle = Constant.Data.MOVIE_LIST_TITLE.TOP_RATED;

        mMovieController.getTopRatedMovies(page);
    }

    private void setPopularMovies(int page) {
        MainActivity.this.setTitle(getString(R.string.popular_movies));

        this.mPage = page;
        this.movieListTitle = Constant.Data.MOVIE_LIST_TITLE.POPULAR;

        mMovieController.getPopularMovies(page);
    }

    @Override
    protected void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieList(MovieEvent event) {
        if (event.getMovieListTitle().equals(movieListTitle)) {
            loadingBar.setVisibility(View.GONE);
            mMoviesAdapter.setData(event.getResults());
            mMainMovies.scrollToPosition(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieListError(MovieErrorEvent event) {
        loadingBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        Log.e(getString(R.string.log_error_result_data), event.getMessage());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        List<Movie> movieList = mMoviesAdapter.getData();
        String movieListJson = App.getInstance().getGson().toJson(movieList);
        outState.putString(Constant.Data.EXTRA_MOVIE_LIST, movieListJson);
        outState.putInt(Constant.Data.EXTRA_PAGE, mPage);
        outState.putString(Constant.Data.EXTRA_TITLE, getTitle().toString());
        outState.putParcelable(Constant.Data.EXTRA_MOVIE_LIST_STATE, mMainMovies.getLayoutManager().onSaveInstanceState());
    }
}