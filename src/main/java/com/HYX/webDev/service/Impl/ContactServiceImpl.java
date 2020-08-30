package com.HYX.webDev.service.Impl;

import com.HYX.webDev.dao.contactMapper;
import com.HYX.webDev.entity.contact;
import com.HYX.webDev.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private contactMapper contactMapper;
    @Override
    @Transactional
    public String saveContact (contact contact){
        if(contactMapper.insertSelective(contact) > 0){
            return "success";
        }
        return "fail";
    }
}

