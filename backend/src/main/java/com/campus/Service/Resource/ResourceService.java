package com.campus.Service.Resource;

import com.campus.Entity.Resource.Resource;
import com.campus.Classification.ResourceCategory;
import com.campus.Repository.Resource.ResourceRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    public Resource saveResource(Resource resource){
        return resourceRepository.save(resource);
    }
    public void deleteResource(Resource resource){
        resourceRepository.delete(resource);
    }
    public List<Resource> getAllResource(){
        return resourceRepository.findAll();
    }
    public List<Resource> getResourceByCategory(ResourceCategory resourceCategory){
        return resourceRepository.findByResourceCategory(resourceCategory);
    }
    public Resource changeResourceName(Resource resource, String name){
        resource.changeResourceName(name);
        return saveResource(resource);
    }
    public Resource changeOpenTime(Resource resource, LocalTime openTime){
        resource.changeOpenTime(openTime);
        return saveResource(resource);
    }
    public Resource changeCloseTime(Resource resource, LocalTime closeTime){
        resource.changeCloseTime(closeTime);
        return saveResource(resource);
    }
    public void setToNonRestriction(Resource resource){
        resource.setToNonRestriction();
    }
    public void setToApprovalRequired(Resource resource){
        resource.setToApprovalRequired();
    }
    public void setToRestricted(Resource resource){
        resource.setToRestricted();
    }
    public Resource getResourceByID(Integer id){
        Optional<Resource> resource = resourceRepository.findById(id);
        if (resource.isPresent()){
            return resource.get();
        }else {
            throw new RuntimeException();
        }
    }
}