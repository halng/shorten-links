package com.shortentlinks.app.backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Entity
@Table(name = "links")
public class Link {

    private static final Integer DEFAULT_EXPIRATION_TIME_IN_MILLIS = 864000;
    private static final String ALGORITHM = "MD5";

    private String originLink;
    @Id
    private String shortLinkId;
    private Instant expirationDate;
    private Instant createdAt;
    private String creator;
    // This field will be hashed from originLink + creator + shortLink
    // This is also the primary key for the table
    private String hash;

    // ==================================================================================================
    private String generateHash() {
        try {
            String strToHashBuff = this.originLink + this.creator + this.shortLinkId;
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] encodedHash = md.digest(strToHashBuff.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            // Should throw an exception here
            return "DO_NOT_HASH";
        }
    }

    public Link() {
        // Default constructor
    }

    public Link(String originLink, String shortLinkId, String creator) {
        this.originLink = originLink;
        this.shortLinkId = shortLinkId;
        this.creator = creator;
        this.createdAt = Instant.now();
        this.expirationDate = Instant.ofEpochMilli(
            this.createdAt.toEpochMilli() + DEFAULT_EXPIRATION_TIME_IN_MILLIS);
        this.hash = this.generateHash();
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