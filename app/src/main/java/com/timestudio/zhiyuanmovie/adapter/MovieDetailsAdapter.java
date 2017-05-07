package com.timestudio.zhiyuanmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Comment;
import com.timestudio.zhiyuanmovie.bean.MovieDetails;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by strongShen on 2017/5/5.
 */

public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private int mViewType = 0x001;
    private MovieDetails movieDetails;
    private List<Comment> mComments;


    public void setmComments(List<Comment> mComments) {
        this.mComments = mComments;
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return mViewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == mViewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moviedtails_title, null);
            return new TitleHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moviedetails_comment, null);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            //显示电影信息数据
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.tv_movie_d_name.setText(movieDetails.getTitle());
            titleHolder.tv_movie_d_type.setText(movieDetails.getGenres() + "  " + movieDetails.getCountry());
            titleHolder.tv_movie_d_fen.setText(movieDetails.getRating());
            titleHolder.tv_movie_d_time.setText(movieDetails.getRuntime() + " " + movieDetails.getLanguage());
            titleHolder.tv_movie_d_date.setText(movieDetails.getRelease_date() + " 上映");
            titleHolder.tv_movie_d_summary.setText(movieDetails.getPlot_simple());
            titleHolder.tv_movie_d_directors.setText(movieDetails.getDirectors());
            titleHolder.tv_movie_d_actors.setText(movieDetails.getActors());
            ImageLoader.getInstance().displayImage(movieDetails.getPoster().getUrl()
                    , titleHolder.iv_movie_d_photo
                    , ImageLoadOptions.build_item());


        } else if (holder instanceof CommentHolder) {
            //显示评论数据
            CommentHolder commentHolder = (CommentHolder) holder;
            commentHolder.tv_comment_uName.setText(mComments.get(position - 1).getUserName());
            commentHolder.tv_comment_uSummary.setText(mComments.get(position - 1).getContent());
            commentHolder.tv_comment_time.setText(mComments.get(position - 1).getCreatedAt());
            ImageLoader.getInstance().displayImage(mComments.get(position - 1).getPhotoUrl()
                    , commentHolder.civ_comment
                    , ImageLoadOptions.build_item());
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size() + 1;
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_movie_d_photo)
        ImageView iv_movie_d_photo;
        @Bind(R.id.tv_movie_d_name)
        TextView tv_movie_d_name;
        @Bind(R.id.tv_movie_d_type)
        TextView tv_movie_d_type;
        @Bind(R.id.tv_movie_d_fen)
        TextView tv_movie_d_fen;
        @Bind(R.id.tv_movie_d_time)
        TextView tv_movie_d_time;
        @Bind(R.id.tv_movie_d_date)
        TextView tv_movie_d_date;
        @Bind(R.id.tv_movie_d_summary)
        TextView tv_movie_d_summary;
        @Bind(R.id.tv_movie_d_directors)
        TextView tv_movie_d_directors;
        @Bind(R.id.tv_movie_d_actors)
        TextView tv_movie_d_actors;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class CommentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.civ_comment)
        CircularImageView civ_comment;
        @Bind(R.id.tv_comment_uName)
        TextView tv_comment_uName;
        @Bind(R.id.tv_comment_uSummary)
        TextView tv_comment_uSummary;
        @Bind(R.id.tv_comment_time)
        TextView tv_comment_time;

        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
