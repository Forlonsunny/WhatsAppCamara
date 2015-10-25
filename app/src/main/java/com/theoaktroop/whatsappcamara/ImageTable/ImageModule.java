package com.theoaktroop.whatsappcamara.ImageTable;

import java.io.Serializable;

/**
 * Created by Suuny on 10/11/2015.
 */
public class ImageModule implements Serializable {
    private static final long serialVersionUID = -7406082437623008161L;

    private long imageId;
    private long answerId;
    private byte[] images;



    public ImageModule() {
    }

    public ImageModule(long imageId, long answerId, byte[] images) {
        this.imageId = imageId;
        this.answerId = answerId;
        this.images = images;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
