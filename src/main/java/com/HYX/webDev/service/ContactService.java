package com.HYX.webDev.service;


import com.HYX.webDev.entity.contact;
import org.springframework.beans.factory.annotation.Autowired;




public interface ContactService {
    public String saveContact(contact contact);
}
