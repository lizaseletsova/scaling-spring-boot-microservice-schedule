package com.scaling.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String id;
    private String username;
    private String timeOfTheMessage;
    private String valueMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeOfTheMessage() {
        return timeOfTheMessage;
    }

    public void setTimeOfTheMessage(String timeOfTheMessage) {
        this.timeOfTheMessage = timeOfTheMessage;
    }

    public String getValueMessage() {
        return valueMessage;
    }

    public void setValueMessage(String valueMessage) {
        this.valueMessage = valueMessage;
    }
}
