package com.tam.joblinks.models;

/**
 * Created by toan on 4/19/2016.
 */
public class UserAndJob {
    private String objectId;
    private java.util.Date created;
    private String ownerId;
    private java.util.Date updated;
    private Integer type;
    private String jobId;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getObjectId() {
        return objectId;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UserAndJob() {}
}
