package com.example.saad.jspart3;

/**
 * Created by saad on 2/16/2017.
 */
public class MessageWord {
    private String sender;
    private String postdate;
    private String senderImage;

    public MessageWord(String send, String date){
        sender = send;
        postdate = date;
    }
    public MessageWord(String send, String date, String image){
        sender = send;
        postdate = date;
        senderImage = image;
    }

    public String getPostdate() {
        return postdate;
    }

    public String getSender() {
        return sender;
    }

    public String getSenderImage() {
        return senderImage;
    }
}
