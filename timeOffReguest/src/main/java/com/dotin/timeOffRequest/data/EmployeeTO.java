package com.dotin.timeOffRequest.data;

import javax.persistence.*;

@Entity
public class EmployeeTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private CategoryElementTO role;
    private EmployeeTO manager;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryElementTO.class, optional = false)
    @JoinColumn(name = "id")
    public CategoryElementTO getRole() {
        return role;
    }

    public void setRole(CategoryElementTO categoryElement) {
        this.role = categoryElement;
    }


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeTO.class)
    @JoinColumn(name = "id")
    public EmployeeTO getManager() {
        return manager;
    }

    public void setManager(EmployeeTO manager) {
        this.manager = manager;
    }
}
