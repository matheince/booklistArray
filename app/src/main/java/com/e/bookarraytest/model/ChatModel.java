package com.e.bookarraytest.model;

import java.util.Hashtable;
import java.util.Map;

public class ChatModel {

    public Map<String,Boolean> users = new Hashtable<>();
    public Map<String,Comment> comments = new Hashtable<>();

    public static class  Comment{
       public  String uid;
       public  String message;

    }

}
