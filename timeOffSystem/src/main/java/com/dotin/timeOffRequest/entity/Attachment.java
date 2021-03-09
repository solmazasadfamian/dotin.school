package com.dotin.timeOffRequest.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Blob;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "t_attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "c_id",nullable = false,unique = true)
    private Long id;
    @Lob
    @Column(name = "c_content")
    private Blob content;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Email.class)
    @JoinColumn(name = "c_email")
    private Email email;
    @Column(name = "c_disabled")
    private Boolean disabled = false;
    @Column(name = "c_active")
    private Boolean active = true;
    @Version
    @Column(name = "c_version")
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
