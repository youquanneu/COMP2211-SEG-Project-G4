package com.campus.Service.Resource;

import com.campus.Entity.Resource.Resource;
import com.campus.Classification.ResourceCategory;
import com.campus.Repository.Resource.ResourceRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Nonnull
    private Resource saveResource(Resource resource){
        return resourceRepository.save(resource);
    }
    private void deleteResource(Resource resource){
        resourceRepository.delete(resource);
    }
    public List<Resource> getAllResource(){
        return resourceRepository.findAll();
    }
    public List<Resource> getResourceByCategory(ResourceCategory resourceCategory){
        return resourceRepository.findByResourceCategory(resourceCategory);
    }
    public void changeOpenTime(Resource resource, LocalTime localTime){

        saveResource(resource);
    }
}
