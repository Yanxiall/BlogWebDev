package com.HYX.webDev.entity;

import java.util.Date;

public class contact {
    private Long contactId;

    private String contactName;

    private String contactEmail;

    private String contactPhoneNumber;

    private String contactCompany;

    private Date createTime;

    private String contactMessage;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber == null ? null : contactPhoneNumber.trim();
    }

    public String getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(String contactCompany) {
        this.contactCompany = contactCompany == null ? null : contactCompany.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(String contactMessage) {
        this.contactMessage = contactMessage == null ? null : contactMessage.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", contactId=").append(contactId);
        sb.append(", contactName=").append(contactName);
        sb.append(", contactEmail=").append(contactEmail);
        sb.append(", contactPhoneNumber=").append(contactPhoneNumber);
        sb.append(", contactCompany=").append(contactCompany);
        sb.append(", createTime=").append(createTime);
        sb.append(", contactMessage=").append(contactMessage);
        sb.append("]");
        return sb.toString();
    }
}