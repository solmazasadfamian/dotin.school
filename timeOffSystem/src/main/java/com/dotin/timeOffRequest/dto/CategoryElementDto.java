package com.dotin.timeOffRequest.dto;

public class CategoryElementDto {
    private Long id;
    private Long code;
    private String name;
    private Long categoryId;
    private Boolean disabled = false;
    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public String toString() {
        return "CategoryElementDto{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", disabled=" + disabled +
                ", active=" + active +
                '}';
    }
}
