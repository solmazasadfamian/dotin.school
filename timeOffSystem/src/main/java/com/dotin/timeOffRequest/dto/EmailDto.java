package com.dotin.timeOffRequest.dto;

import java.util.Set;

public class EmailDto {
    private Long id;
    private String subject;
    private String description;
    private Long senderId;
    private Boolean disabled = false;
    private Boolean active = true;
    private Integer version;
    private Set<Long> receiverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
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

    public Set<Long> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Set<Long> receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", senderId=" + senderId +
                ", disabled=" + disabled +
                ", active=" + active +
                ", version=" + version +
                ", receiverId=" + receiverId +
                '}';
    }
}
