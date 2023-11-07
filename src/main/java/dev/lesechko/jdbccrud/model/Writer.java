package dev.lesechko.jdbccrud.model;

import java.util.List;

public class Writer {
    private long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String composeFullName() {
        String fullName = "";
        if (lastName != null && !lastName.isEmpty())
            fullName += lastName + ", ";
        if (firstName != null && !firstName.isEmpty())
            fullName += firstName;
        return fullName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
