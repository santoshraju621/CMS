package com.thesoftwareguild.capstonecms.dtos;

import java.time.LocalDate;
import java.util.List;

public class Post {
    
    private Integer id;
    private String title;
    private String content;
    private Category category;
    private List<Tag> tags;
    private LocalDate created;
    private LocalDate published;
    private LocalDate expiration;
    private boolean isApproved;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getPublished() {
        return published;
    }
    
    public String getPublishedIso() {
        return published.toString();
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    public LocalDate getExpiration() {
        return expiration;
    }
    
    public String getExpirationIso() {
        return expiration.toString();
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
