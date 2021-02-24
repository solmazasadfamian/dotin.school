package com.dotin.timeOffRequest.data;

import javax.persistence.*;
import java.sql.Blob;
@Entity
@Table(name = "t_attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    @Lob
    @Column(name = "c_content")
    private Blob content;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Email.class)
    @JoinColumn(name = "c_email")
    private Email email;

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
}
