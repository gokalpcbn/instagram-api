package com.gokalpcoban;

import java.io.Serializable;

public class Following implements Serializable {

    private static final long serialVersionUID = -5048442665534510598L;

    private Long pk;
    private String username;
    private String fullName;

    public Following() {
    }

    public Following(Long pk, String username, String fullName) {
        this.pk = pk;
        this.username = username;
        this.fullName = fullName;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
