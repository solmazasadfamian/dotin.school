package com.dotin.timeOffRequest.dto;

public class AttachmentDto {
    private Long id;
    private String fileName;
    private Long emailId;
    private Boolean disabled = false;
    private Boolean active = true;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
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

    @Override
    public String toString() {
        return "AttachmentDto{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", emailId=" + emailId +
                ", disabled=" + disabled +
                ", active=" + active +
                ", version=" + version +
                '}';
    }
}
