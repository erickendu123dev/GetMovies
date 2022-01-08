package com.erickdev.getmovies.recylerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.erickdev.getmovies.DetailMovie;
import com.erickdev.getmovies.R;

import java.util.List;

public class ItemAdapterNowPlaying extends RecyclerView.Adapter<ItemAdapterNowPlaying.ItemViewHolder> {

  List<ItemProductNowPlaying> list;
  private Context context;

  public ItemAdapterNowPlaying(List<ItemProductNowPlaying> list) {
    this.list = list;
  }

  @NonNull
  @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_recylerview_home, parent, false);
    return new ItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    final ItemProductNowPlaying itemProductTopRated = list.get(position);
    holder.tvtitle.setText(itemProductTopRated.getTitle());
    holder.id = itemProductTopRated.getId();
    String imager = "https://image.tmdb.org/t/p/w500"+ itemProductTopRated.getPoster_path();
    Glide.with(holder.itemView.getContext()).load(imager).into(holder.ivposter);
  }

  public class ItemViewHolder extends RecyclerView.ViewHolder {
    private View view;
    private ImageView ivposter;
    private TextView tvtitle;
    private String id;
    private CardView cardView;


    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);
      context = itemView.getContext();

      cardView = itemView.findViewById(R.id.cardview);
      ivposter = itemView.findViewById(R.id.iv_poster);
      tvtitle = itemView.findViewById(R.id.title);

     cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(itemView.getContext(), DetailMovie.class);
          intent.putExtra("idpath", id);
          itemView.getContext().startActivity(intent);
        }
      });
    }
  }
  @Override
  public int getItemCount() {
    return list.size();
  }
}
