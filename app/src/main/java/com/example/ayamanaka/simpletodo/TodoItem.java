package com.example.ayamanaka.simpletodo;

import java.io.Serializable;

/**
 * Created by ayamanaka on 4/16/15.
 */
public class TodoItem implements Serializable {
    private static final long serialVersionUID = 5177222050535318633L;
    private int id;
    private String body;
    private int priority;
    private boolean done;

    public TodoItem(String body, int priority, boolean done) {
        super();
        this.body = body;
        this.priority = priority;
        this.done = done;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
