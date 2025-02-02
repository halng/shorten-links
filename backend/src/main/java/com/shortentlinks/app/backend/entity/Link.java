package com.shortentlinks.app.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "links")
public class Link {

    private static final Integer DEFAULT_EXPIRATION_TIME_IN_MILLIS = 864000;

    @Column(nullable = false)
    private String originLink;
    @Id
    private String shortLinkId;
    private Instant expirationDate;
    private Instant createdAt;
    private String creator;
    // This field will be hashed from originLink + creator + shortLink
    // This is also the primary key for the table
    @Column(unique = true)
    private String hash;

    public Link() {
        // Default constructor
    }

    public Link(String originLink, String shortLinkId, String creator, String hash) {
        this.originLink = originLink;
        this.shortLinkId = shortLinkId;
        this.creator = creator;
        this.createdAt = Instant.now();
        this.expirationDate = Instant.ofEpochMilli(
            this.createdAt.toEpochMilli() + DEFAULT_EXPIRATION_TIME_IN_MILLIS);
        this.hash = hash;
    }

    // getter and setter
    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    public String getShortLinkId() {
        return shortLinkId;
    }

    public void setShortLinkId(String shortLink) {
        this.shortLinkId = shortLink;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getHash() {
        return hash;
    }

}