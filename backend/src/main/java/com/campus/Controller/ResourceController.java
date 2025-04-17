package com.campus.Controller;

import com.campus.Service.Resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
}
