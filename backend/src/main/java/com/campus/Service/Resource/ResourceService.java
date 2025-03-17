package com.campus.Service.Resource;

import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Resource;
import com.campus.Classification.ResourceCategory;
import com.campus.Repository.Resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
    public Resource saveResource(Resource resource){
        return resourceRepository.save(resource);
    }
    public void deleteResource(Resource resource){
        resourceRepository.delete(resource);
    }
    public List<Resource> getAllResource(){
        return resourceRepository.findAll();
    }   // Get all resources
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
    public Resource changeResourceName(Resource resource, String name){
        resource.changeResourceName(name);
        return saveResource(resource);
    }   // Change the name of the resource
    public Resource changeOpenTime(Resource resource, LocalTime openTime){
        resource.changeOpenTime(openTime);
        return saveResource(resource);
    }   // Change the open time of the resource
    public Resource changeCloseTime(Resource resource, LocalTime closeTime){
        resource.changeCloseTime(closeTime);
        return saveResource(resource);
    }   // Change the close time of the resource
    public Resource setToNonRestriction(Resource resource){
        resource.setToNonRestriction(Restriction.NonRestriction);
        return saveResource(resource);
    }   // Change the resource as non restrict
    public Resource setToApprovalRequired(Resource resource){
        resource.setToApprovalRequired(Restriction.ApprovalRequired);
        return saveResource(resource);
    }   // Change the resource as approval required
    public Resource setToRestricted(Resource resource){
        resource.setToRestricted(Restriction.Restricted);
        return saveResource(resource);
    }   // Change the resource as restricted
}