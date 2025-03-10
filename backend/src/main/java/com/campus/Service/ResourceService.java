package com.campus.Service;

import com.campus.Entity.Resource;
import com.campus.Repository.ResourceRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Nonnull
    private Resource saveResource(Resource resource){
        return resourceRepository.save(resource);
    }
    public List<Resource> getAllResource(){
        return resourceRepository.findAll();
    }
}
