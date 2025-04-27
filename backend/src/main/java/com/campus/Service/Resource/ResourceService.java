package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.DataTransferObject.Resource.ResourceDTO;
import com.campus.Entity.Resource.Resource;
import com.campus.Classification.ResourceCategory;
import com.campus.Repository.Resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    public Resource getResourceByID(Integer id){
        Optional<Resource> resource = resourceRepository.findById(id);
        if (resource.isEmpty()) {
            throw new RuntimeException("Resource Not Found");
        }
        return resource.get();
    }
    public Resource getResourceByResourceName(String resourceName){
        Optional<Resource> resource = resourceRepository.findByResourceNameEqualsIgnoreCase(resourceName);
        if (resource.isEmpty()){
            throw new RuntimeException("Resource not found");
        }
        return resource.get();
    }
    public Resource getResourceBySimilarName(String resourceName){
        Optional<Resource> resource = resourceRepository.findByResourceNameContainsIgnoreCase(resourceName);
        if (resource.isEmpty()){
            throw new RuntimeException("Resource not found");
        }
        return resource.get();
    }
    public List<Resource> getAllResource(){
        return resourceRepository.findAll();
    }   // Get all resources
    public List<Resource> getBookableResource(){
        return resourceRepository.findBookableResource();
    }
    public List<Resource> filterResource(Integer resourceId, String resourceName,
                                         LocalTime openTime, LocalTime closeTime,
                                         Restriction restriction,ResourceCategory resourceCategory){
        return resourceRepository.findResourceByFilter
                (resourceId,resourceName,openTime,closeTime,restriction,resourceCategory);
    }   // Base Function : Filter resource
    public List<Resource> getResourceByCategory(ResourceCategory resourceCategory){
        return resourceRepository.findByResourceCategory(resourceCategory);
    }   // Filter resources by category
    public List<Resource> getResourceByRestriction(Restriction restriction){
        return resourceRepository.findByRestriction(restriction);
    }   // Filter resources by restriction level
    public List<Resource> getResourceByNameSearching(String search){
        return resourceRepository.findByResourceNameContainingIgnoreCase(search);
    }   // Find resource by searching input
    public List<Resource> getResourceOpenDuring(LocalTime timeFrom,LocalTime timeTo){
        return resourceRepository.findByOpenTimeBeforeAndCloseTimeAfter(timeFrom,timeTo);
    }   // Filter resource which open during the time
}