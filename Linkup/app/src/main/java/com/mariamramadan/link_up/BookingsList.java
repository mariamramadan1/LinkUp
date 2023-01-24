package com.mariamramadan.link_up;

public class BookingsList extends Throwable {

    private int ImageId;
    private String Worker;
    private String Phonenum;
    private String TimeStamp;
    private String status;

    public BookingsList(int imageId, String Worker, String Phonenum,String Status, String TimeStamp)
    {
        this.ImageId = imageId;
        this.Worker = Worker;
        this.Phonenum= Phonenum;
        this.status=  Status;
        this.TimeStamp= TimeStamp;

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
    public String getTimeStamp() {
        return TimeStamp;
    }
}