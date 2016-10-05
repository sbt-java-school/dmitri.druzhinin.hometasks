package ru.sbt.school.chat;

import java.io.Serializable;

public class Message implements Serializable {
    private final String loginFrom;
    private final String loginTo;
    private final String data;

    public Message(String loginFrom, String loginTo, String data) {
        this.loginFrom = loginFrom;
        this.loginTo = loginTo;
        this.data = data;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public String getLoginTo() {
        return loginTo;
    }

    public String getData() {
        return data;
    }
}
