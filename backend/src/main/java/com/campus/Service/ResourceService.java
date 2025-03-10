package com.campus.Service;

import com.campus.Repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

}
