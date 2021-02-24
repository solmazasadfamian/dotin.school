package com.dotin.timeOffRequest.data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Entity
@Table(name = "t_email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_subject")
    private String subject;
    @Column(name = "c_description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class, optional = false)
    @JoinColumn(name = "c_sender")
    private Employee sender;
    @Column(name = "c_active")
    private Boolean active = true;
    @Column(name = "c_deleted")
    private Boolean deleted = true;
    @Column(name = "c_attachFilePath")
    @ManyToMany
    @JoinTable(
            name = "t_emailReceiver",
            joinColumns = @JoinColumn(name = "c_email_id"),
            inverseJoinColumns = @JoinColumn(name = "c_employee_id"))
    private Set<Employee> receiver;

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

    public Employee getSender() {
        return sender;
    }

    public void setSender(Employee sender) {
        this.sender = sender;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Employee> getReceiver() {
        return receiver;
    }

    public void setReceiver(Set<Employee> receiver) {
        this.receiver = receiver;
    }
}
