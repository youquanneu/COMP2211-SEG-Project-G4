package com.campus.Service.User;

import com.campus.Classification.ResourceCategory;
import com.campus.Classification.Restriction;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.OutdoorVenue;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Classification.UserRole;
import com.campus.Repository.Resource.EquipmentRepository;
import com.campus.Repository.Resource.IndoorVenueRepository;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Repository.User.UserRepository;
import com.campus.Service.Resource.EquipmentService;
import com.campus.Service.Resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AdministrativeStaffService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void register(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input username : ");
        String username = scanner.nextLine();
        System.out.println("Input email : ");
        String email = scanner.nextLine();
        System.out.println("Input password : ");
        String password = scanner.nextLine();
        System.out.println("Input type: 1.Student 2.Lecturer 3.AdministrativeStaff ");
        UserRole userRole;
        int type = scanner.nextInt();
        if (type ==1){
            userRole = UserRole.Student;
        } else if (type==2) {
            userRole = UserRole.Lecturer;
        }else {
            userRole = UserRole.AdministrativeStaff;
        }
        try {
            User u = new User(username, email, password, userRole);
            registerNewUser(u);
            //User u = registerNewUser(username, email, password, userRole);
            System.out.println("User register successful : \n" + u);
        }catch (Exception e){
            System.out.println(e.getMessage());
            register();
        }
    }   // Demonstration method: Register a new user
    public void allUser(){
        List<User> userList = getAllUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }   // Demonstration method: List out all user
    public void userByRole() {
        System.out.println("Select type: 1.Student 2.Lecturer 3.AdministrativeStaff ");
        UserRole userRole;
        Scanner scanner = new Scanner(System.in);
        int type = scanner.nextInt();
        if (type ==1){
            userRole = UserRole.Student;
        } else if (type==2) {
            userRole = UserRole.Lecturer;
        }else {
            userRole = UserRole.AdministrativeStaff;
        }
        List<User> roleList = getUserByUserRole(userRole);
        for (User user : roleList) {
            System.out.println(user.toString());
        }
    }   // Demonstration method: List out user by role
    public User registerNewUser(User user) {
        Optional<User> existingUser = userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(user.getUsername(), user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email or username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.changePassword(encodedPassword);
        return saveUser(user);
    }   // Function: Register a new user only if non-duplicate email or username
    private User registerNewUser(String username, String email, String password, UserRole userRole) {
        Optional<User> existingUser = userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(username, email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email or username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, userRole);
        return saveUser(newUser);
    }   // Function: Register a new user only if non-duplicate email or username
    private User modifyUsername(User user, String username){
        user.changeUsername(username);
        return saveUser(user);
    }   // Function : Change the user's username
    private User modifyEmail(User user, String email){
        user.changeEmail(email);
        return saveUser(user);
    }   // Function : Change the user's email
    private User saveUser(User user){
        return userRepository.save(user);
    }   // Base Function : Insert the user into database
    private void deleteUser(User user){
        userRepository.delete(user);
    }   // Base Function : Delete the user from database
    private List<User> getAllUsers(){
        return userRepository.findAll();
    }   // Base Function: Get a list of all user
    private List<User> filterUsers(Integer userId,String username, String email, UserRole userRole){
        return userRepository.findUserByFilter(userId,username,email,userRole);
    }   // Base Function : Filter user
    private List<User> getUserByUserRole(UserRole userRole){
        return userRepository.findUserByUserRole(userRole);
    }   // Base Function: Get a list of user base on role

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private IndoorVenueRepository indoorVenueRepository;
    public void addNewResource(){
        try {
            String name;
            int periodBool,openHour,closeHour;
            LocalTime openTime,closeTime;

            Scanner scanner = new Scanner(System.in);
            System.out.println("Input resource name : ");
            name = scanner.nextLine();

            System.out.println("Set Opening Time? : 1.Yes 2.No");
            periodBool = scanner.nextInt();
            if (periodBool==1) {
                System.out.println("Input open time : ");
                openHour = scanner.nextInt();
                openTime = LocalTime.of(openHour, 0);
                System.out.println("Input close time : ");
                closeHour = scanner.nextInt();
                closeTime = LocalTime.of(closeHour, 0).minusSeconds(1);
            }else {
                openTime = null;
                closeTime = null;
            }

            System.out.println("Input restriction: 1.Non-Restrict 2.Approval required 3.Restricted ");
            Restriction restriction;
            int restrict = scanner.nextInt();
            if (restrict == 1) {
                restriction = Restriction.NonRestriction;
            } else if (restrict == 2) {
                restriction = Restriction.ApprovalRequired;
            } else {
                restriction = Restriction.Restricted;
            }

            System.out.println("Input type: 1.Equipment 2.Indoor Venue 3.Outdoor Venue ");
            int type = scanner.nextInt();
            String blank = scanner.nextLine();  // Resolve scanner next line issue
            if (type == 1) {
                System.out.println("Input serial number : ");
                String serialNumber = scanner.nextLine();
                addNewEquipment(new Equipment(name, openTime, closeTime, restriction, serialNumber));
            } else if (type == 2) {
                System.out.println("Input building : ");
                String building = scanner.nextLine();
                System.out.println("Input room number : ");
                String roomNumber = scanner.nextLine();
                addNewIndoorVenue(new IndoorVenue(name, openTime, closeTime, restriction, building, roomNumber));
            } else {
                System.out.println("Input location : ");
                String location = scanner.nextLine();
                addNewOutdoorVenue(new OutdoorVenue(name, openTime, closeTime, restriction, location));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            addNewResource();
        }
    }   // Demonstration method : Add a new resource
    public Equipment addNewEquipment(Equipment equipment) {
        Optional<Equipment> existingEquipment = equipmentRepository.findBySerialNumberEqualsIgnoreCase(equipment.getSerialNumber());
        if (existingEquipment.isPresent()) {
            throw new RuntimeException("Equipment already exists.");
        }
        return (Equipment) saveResource(equipment);
    }   // Function: Add a new equipment only if non-duplicate serial number
    public IndoorVenue addNewIndoorVenue(IndoorVenue indoorVenue){
        Optional<IndoorVenue> existingIndoorVenue = indoorVenueRepository.findIndoorVenueByBuildingEqualsIgnoreCaseAndRoomNumberEqualsIgnoreCase(indoorVenue.getBuilding(), indoorVenue.getRoomNumber());
        if (existingIndoorVenue.isPresent()){
            throw new RuntimeException("Indoor Venue already exist");
        }
        return (IndoorVenue) saveResource(indoorVenue);
    }   // Function : Add a new indoor venue only if non-duplicate building and room number
    public OutdoorVenue addNewOutdoorVenue(OutdoorVenue outdoorVenue){
        return (OutdoorVenue) saveResource(outdoorVenue);
    }   // Function : Add a new outdoor venue
    public Resource changeResourceName(Resource resource, String name){
        resource.changeResourceName(name);
        return saveResource(resource);
    }   // Function : Change the resource's name
    public Resource changeOpenTime(Resource resource, LocalTime openTime){
        resource.changeOpenTime(openTime);
        return saveResource(resource);
    }   // Function : Change the resource's open time
    public Resource changeCloseTime(Resource resource, LocalTime closeTime){
        resource.changeCloseTime(closeTime);
        return saveResource(resource);
    }   // Function : Change the resource's close time
    public Resource changeRestriction(Resource resource, Restriction restriction){
        resource.changeRestriction(restriction);
        return saveResource(resource);
    }   // Function : Change the resource's restriction
    public Equipment modifySerialNumber(Equipment equipment, String serialNumber){
        equipment.changeSerialNumber(serialNumber);
        return (Equipment) saveResource(equipment);
    }   // Function : Change the equipment's serial number
    private IndoorVenue modifyBuilding(IndoorVenue indoorVenue, String building){
        indoorVenue.changeBuilding(building);
        return (IndoorVenue) saveResource(indoorVenue);
    }   // Function : Change the indoor venue's building
    private IndoorVenue modifyRoomNumber(IndoorVenue indoorVenue, String roomNumber){
        indoorVenue.changeRoomNumber(roomNumber);
        return (IndoorVenue) saveResource(indoorVenue);
    }   // Function : Change the indoor venue's room number
    private OutdoorVenue modifyLocation(OutdoorVenue outdoorVenue, String location){
        outdoorVenue.changeLocation(location);
        return (OutdoorVenue) saveResource(outdoorVenue);
    }   // Function : Chane the outdoor venue's location
    private Resource saveResource(Resource resource){
        return resourceRepository.save(resource);
    }   // Base Function : Insert the resource into database
    private void deleteResource(Resource resource){
        resourceRepository.delete(resource);
    }   // Base Function : Delete the resource from database
}
