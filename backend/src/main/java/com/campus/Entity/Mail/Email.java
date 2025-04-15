package com.campus.Entity.Mail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Email {
    public Email(){}
    public Email(String subject, String header,String footer,String content){
        setSubject(subject);
        setHeader(header);
        setFooter(footer);
        setContent(content);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emailId;
    private String subject;
    private String header;
    private String footer;
    private String content;
    public String getSubject() {
        return subject;
    }
    public String getHeader() {
        return header;
    }
    public String getFooter() {
        return footer;
    }
    public String getContent() {
        return content;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public void setFooter(String footer) {
        this.footer = footer;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
