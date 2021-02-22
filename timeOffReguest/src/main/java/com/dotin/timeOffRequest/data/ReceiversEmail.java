package com.dotin.timeOffRequest.data;

import javax.persistence.*;

@Entity
public class ReceiversEmail {

    private Long id;
    private EmailTO email;
    private EmployeeTO receiver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmailTO.class, optional = false)
    @JoinColumn(name = "id")
    public EmailTO getEmail() {
        return email;
    }

    public void setEmail(EmailTO email) {
        this.email = email;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeTO.class, optional = false)
    @JoinColumn(name = "id")
    public EmployeeTO getReceiver() {
        return receiver;
    }

    public void setReceiver(EmployeeTO employee) {
        this.receiver = employee;
    }
}
