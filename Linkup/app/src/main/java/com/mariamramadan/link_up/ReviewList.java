package com.mariamramadan.link_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReviewList extends Throwable {

    private int ImageId;
    private String Name;
    private String Rating;
    private String Review;

    public ReviewList(int imageId, String Name, String Review, String Rating)
    {
        this.ImageId = imageId;
        this.Name = Name;
        this.Rating= Rating;
        this.Review= Review;

    }
    public int getImageId() {
        return ImageId;
    }

    public String getName() {
        return Name;
    }

    public String getRating() {
        return Rating;
    }

    public String GetReview(){ return Review; }
}