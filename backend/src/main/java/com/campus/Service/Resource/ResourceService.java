package com.campus.Service.Resource;

import com.campus.Entity.Resource.Resource;
import com.campus.EntityClassification.ResourceCategory;
import com.campus.Repository.Resource.ResourceRepository;
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
    public List<Resource> getResourceByCategory(ResourceCategory resourceCategory){
        return resourceRepository.findByResourceCategory(resourceCategory);
    }
}
