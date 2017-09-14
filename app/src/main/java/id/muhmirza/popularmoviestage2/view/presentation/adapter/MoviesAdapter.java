package id.muhmirza.popularmoviestage2.view.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.muhmirza.popularmoviestage2.App;
import id.muhmirza.popularmoviestage2.R;
import id.muhmirza.popularmoviestage2.data.model.Movie;
import id.muhmirza.popularmoviestage2.data.util.Constant;
import id.muhmirza.popularmoviestage2.data.util.retrofit.RetrofitApi;
import id.muhmirza.popularmoviestage2.view.presentation.detail.DetailActivity;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> mMovies;

    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    public void setData(List<Movie> movies) {
        this.mMovies.clear();
        this.mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public List<Movie> getData() {
        return this.mMovies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_movies, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        Constant.Function.setImageResource(holder.itemView.getContext(), RetrofitApi.BASE_URL_IMAGE + mMovies.get(position).getPosterPath(), holder.mMoviePoster);
    }

    private void detailMovie(Context context, int position) {
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra(Constant.Data.MOVIE_INTENT, App.getInstance().getGson().toJson(mMovies.get(position)));
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_movies_poster)
        ImageView mMoviePoster;

        MoviesViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mMoviePoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailMovie(itemView.getContext(), getAdapterPosition());
                }
            });
        }
    }
}