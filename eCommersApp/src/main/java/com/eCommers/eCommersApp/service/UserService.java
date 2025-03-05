package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.Enum.UserAccountStatus;
import com.eCommers.eCommersApp.Enum.UserRole;
import com.eCommers.eCommersApp.dto.AdminDTO;
import com.eCommers.eCommersApp.dto.CustomerDTO;
import com.eCommers.eCommersApp.dto.UserDTO;
import com.eCommers.eCommersApp.exception.UserException;
import com.eCommers.eCommersApp.model.User;
import com.eCommers.eCommersApp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;



    public User addUser(CustomerDTO customer) throws UserException {
        if (customer == null)
            throw new UserException("customer Can not be Null");
        Optional<User> findByEmail = userRepository.findByEmail(customer.getEmail());
        if (findByEmail.isPresent()) {
            System.out.println("inside add user method");
            throw new RuntimeException("Email already Register");
        }

        User newCustomer = new User();
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(customer.getPassword());
        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());
        newCustomer.setRole(UserRole.ROLE_USER);
        newCustomer.setRegisterTime(LocalDateTime.now());
        newCustomer.setUserAccountStatus(UserAccountStatus.ACTIVE);

        return userRepository.save(newCustomer);
    }


    public User addUserAdmin(AdminDTO customer) {
        if (customer == null)
            throw new UserException("admin Can not be Null");
        Optional<User> findByEmail = userRepository.findByEmail(customer.getEmail());
        if (findByEmail.isPresent()) {
            System.out.println("inside add user method");
            throw new RuntimeException("Email already Register");
        }
        User newAdmin = new User();
        newAdmin.setEmail(customer.getEmail());
        newAdmin.setPassword(customer.getPassword());
        newAdmin.setFirstName(customer.getFirstName());
        newAdmin.setLastName(customer.getLastName());
        newAdmin.setPhoneNumber(customer.getPhoneNumber());
        newAdmin.setRole(UserRole.ROLE_ADMIN);
        newAdmin.setRegisterTime(LocalDateTime.now());
        newAdmin.setUserAccountStatus(UserAccountStatus.ACTIVE);

        return userRepository.save(newAdmin);
    }

    public User changePassword(Integer userId, UserDTO customer) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        if (customer.getNewPassword().length() >= 5 && customer.getNewPassword().length() <= 10) {
            user.updatePassword(customer.getNewPassword(), passwordEncoder);
            return userRepository.save(user);
        } else {
            throw new UserException("provide valid  password");
        }

    }

    public User getUserByEmailId(String emailId) throws UserException {
        return userRepository.findByEmail(emailId).orElseThrow(()-> new UserException("User not found"));

    }


    public String deactivateUser(Integer userId) throws UserException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        existingUser.setUserAccountStatus(UserAccountStatus.DEACTIVATE);
        userRepository.save(existingUser);
        return "Account deactivate Successfully";
    }


    public User getUserDetails(Integer userId) throws UserException {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }


    public List<User> getAllUserDetails() throws UserException {

        List<User> existingAllUser = userRepository.findAll();
        if (existingAllUser.isEmpty()) {
            throw new UserException("User list is Empty");
        }
        return existingAllUser;
    }


}
