package edu.okami.m.dicebuilder.Objects;

import java.util.Date;

/**
 * Created by M on 4/20/2017.
 */

public class ChatMessage {
    private String appKey;
    private String recipientId;
    private String message;

    public ChatMessage(String appKey, String recipientId, String message){
        this.appKey = appKey;
        this.recipientId = recipientId;
        this.message = message;
    }

    public ChatMessage(){

    }

    public String getKey(){
        return appKey;
    }

    public String getRecipientId(){
        return recipientId;
    }

    public String getMessage(){
        return message;
    }

}
