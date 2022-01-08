package com.erickdev.getmovies.recylerview;

public class ItemProductTopRated {

  private String id, title, poster_path;

  public ItemProductTopRated() {
  }

  public ItemProductTopRated(String id, String title, String poster_path) {
    this.id = id;
    this.title = title;
    this.poster_path = poster_path;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }
}
