package com.tam.joblinks.models;

/**
 * Created by toan on 4/13/2016.
 */
public class Job {
    public String title;
    public String company;
    public String city;
    public String getDisplaySalary() {
        return String.valueOf(this.salary);
    }
    public int salary;

    public Job() {}
}
