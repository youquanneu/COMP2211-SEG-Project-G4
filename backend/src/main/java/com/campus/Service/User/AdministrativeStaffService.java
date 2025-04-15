package com.campus.Service.User;

import com.campus.Classification.Restriction;
import com.campus.Classification.Status;
import com.campus.Entity.Event.Event;
import com.campus.Entity.Reservation.Reservation;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.OutdoorVenue;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.AdministrativeStaff;
import com.campus.Entity.User.User;
import com.campus.Classification.UserRole;
import com.campus.Repository.Event.EventRepository;
import com.campus.Repository.Reservation.ReservationRepository;
import com.campus.Repository.Resource.EquipmentRepository;
import com.campus.Repository.Resource.IndoorVenueRepository;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Repository.User.AdministrativeStaffRepository;
import com.campus.Repository.User.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdministrativeStaffService{
    @Autowired
    private AdministrativeStaffRepository administrativeStaffRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<AdministrativeStaff> getAllAdmin(){
        return administrativeStaffRepository.findAll();
    }
    public User registerNewUser(User user) {
        try {
            checkExistingUser(user.getUsername(), user.getEmail());
            encodeUserPassword(user);
            saveUser(user);
            System.out.println("User register successful : \n" + user);
            return user;
        }catch (Exception e){
            System.out.println("User register unsuccessful : \n" + user);
            System.out.println(e.getMessage());
            return null;
        }
    }   // Final Function: Register a new user only if non-duplicate email or username
    public User modifyUsername(@NotNull User user, String username){
        try {
            checkExistingUser(username, null);
            user.changeUsername(username);
            saveUser(user);
            System.out.println("Username changed successful : \n" + user);
            return user;
        }
        catch (Exception e){
            System.out.println("Username changed unsuccessful : \n" + user);
            System.out.println(e.getMessage());
            return null;
        }
    }   // Final Function : Change the user's username
    public User modifyEmail(@NotNull User user, String email){
        try {
            checkExistingUser(null, email);
            user.changeEmail(email);
            saveUser(user);
            System.out.println("User email changed successful : \n" + user);
            return user;
        }catch (Exception e){
            System.out.println("User email changed unsuccessful : \n" + user);
            System.out.println(e.getMessage());
            return null;
        }
    }   // Final Function : Change the user's email
    private void checkExistingUser(String username, String email){
        Optional<User> existingUser = userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(username,email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email or username already exists.");
        }
    }   // Function : Prevent user register with same username or email
    private void encodeUserPassword(@NotNull User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.changePassword(encodedPassword);
    }   // Function : Encode the user password
    private @NotNull User registerNewUser(String username, String email, String password, UserRole userRole) {
        Optional<User> existingUser = userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(username, email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email or username already exists.");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, userRole);
        return saveUser(newUser);
    }   // Alt-Function: Register a new user only if non-duplicate email or username
    private User saveUser(User user){
        return userRepository.save(user);
    }   // Base Function : Insert the user into database
    public void deleteUser(User user){
        userRepository.delete(user);
    }   // Base Function : Delete the user from database
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }   // Base Function: Get a list of all user
    public List<User> filterUsers(Integer userId,String username, String email, UserRole userRole){
        return userRepository.findUserByFilter(userId,username,email,userRole);
    }   // Base Function : Filter user
    public List<User> getUserByUserRole(UserRole userRole){
        return userRepository.findUserByUserRole(userRole);
    }   // Base Function: Get a list of user base on role
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private IndoorVenueRepository indoorVenueRepository;
    public Resource addNewResource(Resource resource){
        try {
            switch (resource.getResourceCategory()) {
                case Equipment -> addNewEquipment((Equipment) resource);
                case IndoorVenue -> addNewIndoorVenue((IndoorVenue) resource);
                case OutdoorVenue -> addNewOutdoorVenue((OutdoorVenue) resource);
                default -> saveResource(resource);
            }
            return resource;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    private void addNewEquipment(Equipment equipment) {
        Optional<Equipment> existingEquipment = equipmentRepository.findBySerialNumberEqualsIgnoreCase(equipment.getSerialNumber());
        if (existingEquipment.isPresent()) {
            throw new RuntimeException("Equipment already exists.");
        }
        saveResource(equipment);
    }   // Function: Add a new equipment only if non-duplicate serial number
    private void addNewIndoorVenue(IndoorVenue indoorVenue){
        Optional<IndoorVenue> existingIndoorVenue = indoorVenueRepository.findIndoorVenueByBuildingEqualsIgnoreCaseAndRoomNumberEqualsIgnoreCase(indoorVenue.getBuilding(), indoorVenue.getRoomNumber());
        if (existingIndoorVenue.isPresent()){
            throw new RuntimeException("Indoor Venue already exist");
        }
        saveResource(indoorVenue);
    }   // Function : Add a new indoor venue only if non-duplicate building and room number
    private void addNewOutdoorVenue(OutdoorVenue outdoorVenue){
        saveResource(outdoorVenue);
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
    public void deleteResource(Resource resource){
        resourceRepository.delete(resource);
    }   // Base Function : Delete the resource from database

    @Autowired
    private ReservationRepository reservationRepository;
    public List<Reservation> getPendingReservationList(){
        return reservationRepository.findReservationByStatus(Status.Pending);
    }
    public Reservation approveReservation(Reservation reservation){
        reservation.changeReservationStatus(Status.Approved);
        return reservationRepository.save(reservation);
    }
    public Reservation rejectReservation(Reservation reservation){
        reservation.changeReservationStatus(Status.Rejected);
        return reservationRepository.save(reservation);
    }
    public List<Reservation> filterReservation(Integer reservationId, User booker, Resource resource, LocalDateTime reservationAfter, LocalDateTime reservationBefore, Status status){
        return reservationRepository.filterReservation(reservationId,booker,resource,reservationAfter,reservationBefore,status);
    }


    @Autowired
    private EventRepository eventRepository;

    public Event createNewEvent(Event event){
        return eventRepository.save(event);
    }

}
