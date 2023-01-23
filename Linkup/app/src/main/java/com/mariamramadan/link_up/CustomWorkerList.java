package com.mariamramadan.link_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomWorkerList extends Throwable {

    private int ImageId;
    private String Worker;
    private String Rating;
    private String SubCategory;

    public CustomWorkerList(int imageId, String Worker, String Rating, String SubCategory)
    {
        this.ImageId = imageId;
        this.Worker = Worker;
        this.Rating= Rating;
        this.SubCategory= SubCategory;

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

<<<<<<< HEAD
    public String getSubCategory(){ return SubCategory; }
=======
>>>>>>> 7aae59e30e412d326af6a84c1d37387339040105
}