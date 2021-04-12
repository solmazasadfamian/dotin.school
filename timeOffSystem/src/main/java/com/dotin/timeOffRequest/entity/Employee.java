package com.dotin.timeOffRequest.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_employee")

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "c_timeOffBalance")
    private double timeOffBalance = 240;
    @Column(name = "c_firstName")
    private String firstName;
    @Column(name = "c_lastName")
    private String lastName;
    @Column(name = "c_nationalCode")
    private String nationalCode;
    @Column(name = "c_phoneNumber")
    private String phoneNumber;
    @Column(name = "c_address")
    private String address;
    @Column(name = "c_emailAddress")
    private String emailAddress;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryElement.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "c_role")
    private CategoryElement role;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_manager")
    private Employee manager;
    @Column(name = "c_disabled")
    private Boolean disabled = false;
    @Column(name = "c_active")
    private Boolean active = true;
    @Version
    @Column(name = "c_version")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public CategoryElement getRole() {
        return role;
    }

    public void setRole(CategoryElement role) {
        this.role = role;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public double getTimeOffBalance() {
        return timeOffBalance;
    }

    public void setTimeOffBalance(double timeOffBalance) {
        this.timeOffBalance = timeOffBalance;
    }
}
