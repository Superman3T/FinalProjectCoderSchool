package com.tam.joblinks.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.tam.joblinks.helpers.StringHelper;

import java.util.Date;

/**
 * Created by toan on 4/13/2016.
 */
public class Job{
    private String city;
    private String objectId;
    private String title;
    private java.util.Date created;
    private java.util.Date expirationDate;
    private Integer max_salary;
    private Integer salary;
    private java.util.Date updated;
    private Integer min_salary;

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    private String ownerId;
    private String description;
    private String createdBy;
    private String shortDescription;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private java.util.List<UserAndJob> related_users;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public java.util.Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(java.util.Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(Integer max_salary) {
        this.max_salary = max_salary;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(Integer min_salary) {
        this.min_salary = min_salary;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public java.util.List<UserAndJob> getRelated_users() {
        return related_users;
    }

    public void setRelated_users(java.util.List<UserAndJob> related_users) {
        this.related_users = related_users;
    }

    public String displayCompanyOrUser() {
        if (!StringHelper.isNullOrEmpty(this.createdBy)) {
            return this.createdBy;
        }
        return "";
    }

    public Job() {
    }

}
