package com.example.timecapsule.capsule.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Recipient {
    private String recipient;

    private Long recipientId;
    public Recipient() {}
    public Recipient(String recipient, Long recipientId) {
        this.recipient = recipient;
        this.recipientId = recipientId;
    }

    public String getRecipient() {
        return recipient;
    }

    public Long getRecipientId() {
        return recipientId;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

}
