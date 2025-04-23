package com.campus.Service.User;


import com.campus.Classification.Restriction;
import com.campus.Classification.UserRole;
import com.campus.Entity.Resource.Equipment;
import com.campus.Entity.Resource.IndoorVenue;
import com.campus.Entity.Resource.Resource;
import com.campus.Entity.User.User;
import com.campus.Repository.Resource.EquipmentRepository;
import com.campus.Repository.Resource.IndoorVenueRepository;
import com.campus.Repository.Resource.ResourceRepository;
import com.campus.Repository.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class AdministrativeStaffServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private IndoorVenueRepository indoorVenueRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdministrativeStaffService administrativeStaffService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testRegisterNewUser_Success() {
        User newUser = new User(
                "testUser",
                "test@example.com",
                "password123",
                UserRole.AdministrativeStaff);
        when(userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = administrativeStaffService.registerNewUser(newUser);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository, times(1)).save(newUser);  // Ensure save was called
    }

    @Test
    public void testRegisterNewUser_UsernameOrEmailExists() {
        User newUser = new User("testUser", "test@example.com", "password123", UserRole.AdministrativeStaff);
        when(userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.of(newUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> administrativeStaffService.registerNewUser(newUser));
        assertEquals("User with this email or username already exists.", exception.getMessage());
    }

    @Test
    public void testModifyUsername_Success() {
        User user = new User("oldUsername", "test@example.com", "encodedPassword", UserRole.AdministrativeStaff);
        when(userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.empty());

        User modifiedUser = administrativeStaffService.modifyUsername(user, "newUsername");

        assertNotNull(modifiedUser);
        assertEquals("newUsername", modifiedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testModifyUsername_UsernameExists() {
        User user = new User("oldUsername", "test@example.com", "encodedPassword", UserRole.AdministrativeStaff);
        when(userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> administrativeStaffService.modifyUsername(user, "existingUsername"));
        assertEquals("User with this email or username already exists.", exception.getMessage());
    }

    @Test
    public void testModifyEmail_Success() {
        User user = new User("testUser", "old@example.com", "encodedPassword", UserRole.AdministrativeStaff);
        when(userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.empty());

        User modifiedUser = administrativeStaffService.modifyEmail(user, "new@example.com");

        assertNotNull(modifiedUser);
        assertEquals("new@example.com", modifiedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testModifyEmail_EmailExists() {
        User user = new User("testUser", "old@example.com", "encodedPassword", UserRole.AdministrativeStaff);
        when(userRepository.findByUsernameEqualsIgnoreCaseOrEmailEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> administrativeStaffService.modifyEmail(user, "existing@example.com"));
        assertEquals("User with this email or username already exists.", exception.getMessage());
    }

    @Test
    public void testAddNewEquipment_Success() {
        Equipment equipment = new Equipment(
                "Equipment",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                Restriction.NonRestriction,
                "serial123");
        when(equipmentRepository.findBySerialNumberEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
//        when(resourceRepository.save(any(Equipment.class))).thenReturn(equipment);

        Resource result = administrativeStaffService.addNewResource(equipment);

        assertNotNull(result);
        assertTrue(result instanceof Equipment);
        verify(resourceRepository, times(1)).save(equipment);
    }

    @Test
    public void testAddNewIndoorVenue_Success() {
        // Arrange
        IndoorVenue indoorVenue = new IndoorVenue(
                "IndoorVenue",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                Restriction.NonRestriction,
                "BuildingA", "Room101");
        when(indoorVenueRepository.findIndoorVenueByBuildingEqualsIgnoreCaseAndRoomNumberEqualsIgnoreCase(anyString(), anyString())).thenReturn(Optional.empty());
//        when(resourceRepository.save(any(IndoorVenue.class))).thenReturn(indoorVenue);

        Resource result = administrativeStaffService.addNewResource(indoorVenue);

        assertNotNull(result);
        assertTrue(result instanceof IndoorVenue);
        verify(resourceRepository, times(1)).save(indoorVenue);
    }

    @Test
    public void testDeleteUser_Success() {
        User user = new User("testUser", "test@example.com", "password123", UserRole.AdministrativeStaff);

        administrativeStaffService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
    }
}
