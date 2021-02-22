package com.dotin.timeOffRequest.data;

import javax.persistence.*;

@Entity
public class EmailTO {

    private Long id;
    private String subject;
    private String description;
    private EmployeeTO sender;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeTO.class, optional = false)
    @JoinColumn(name = "id")
    public EmployeeTO getSender() {
        return sender;
    }

    public void setSender(EmployeeTO sender) {
        this.sender = sender;
    }
}
