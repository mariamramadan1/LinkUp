package com.mariamramadan.link_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BookingsList extends Throwable {

    private int ImageId;
    private String Worker;
    private String Phonenum;

    private String status;

    public BookingsList(int imageId, String Worker, String Phonenum,String Status)
    {
        this.ImageId = imageId;
        this.Worker = Worker;
        this.Phonenum= Phonenum;
        this.status=  Status;

    }
    public int getImageId() {
        return ImageId;
    }

    public String getWorker() {
        return Worker;
    }

    public String getphoneNum() {
        return Phonenum;
    }
    public String getStatus() {
        return status;
    }
}