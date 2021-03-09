package com.dotin.timeOffRequest.entity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import org.hibernate.annotations.Cache;

@Entity
@Table(name = "t_category")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "c_id",nullable = false,unique = true)
    private Long id;
    @Column(name = "c_name")
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean active) {
        this.disabled = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean deleted) {
        this.active = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
