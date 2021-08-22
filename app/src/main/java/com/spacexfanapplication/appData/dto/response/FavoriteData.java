package com.spacexfanapplication.appData.dto.response;

public class FavoriteData {

    String fav_id,spacex_rocket_id,name,image;
    int flight_number;
    boolean favorite;
    boolean success;

    public FavoriteData() {
    }

    public FavoriteData(String fav_id, String spacex_rocket_id, String name, String image, int flight_number, boolean favorite, boolean success) {
        this.fav_id = fav_id;
        this.spacex_rocket_id = spacex_rocket_id;
        this.name = name;
        this.image = image;
        this.flight_number = flight_number;
        this.favorite = favorite;
        this.success = success;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getSpacex_rocket_id() {
        return spacex_rocket_id;
    }

    public void setSpacex_rocket_id(String spacex_rocket_id) {
        this.spacex_rocket_id = spacex_rocket_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
