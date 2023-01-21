package com.mariamramadan.link_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomWorkerList extends Throwable {

    private int ImageId;
    private String Worker;
    private String Rating;


    public CustomWorkerList(int imageId, String Worker, String Rating)
    {
        this.ImageId = imageId;
        this.Worker = Worker;
        this.Rating= Rating;

    }
    public int getImageId() {
        return ImageId;
    }

    public String getWorker() {
        return Worker;
    }

    public String getRating() {
        return Rating;
    }
}