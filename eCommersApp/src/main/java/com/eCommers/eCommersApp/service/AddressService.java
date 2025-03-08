package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.exception.AddressException;
import com.eCommers.eCommersApp.model.Address;
import com.eCommers.eCommersApp.model.User;
import com.eCommers.eCommersApp.repo.AddressRepository;
import com.eCommers.eCommersApp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;



    public Address addAddressToUser(Integer userId, Address address) throws AddressException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Fouund"));

        Address saveaAddress = addressRepository.save(address);
        saveaAddress.setUser(existingUser);

        existingUser.getAddress().add(saveaAddress);
        userRepository.save(existingUser);
        return saveaAddress;
    }


    public Address updateAddress(Address address,Integer addressId) throws AddressException {

        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));


        existingAddress.setFlatNo(address.getFlatNo());
        existingAddress.setZipCode(address.getZipCode());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        // Save the updated address in the repository
        return addressRepository.save(existingAddress);
    }


    public void removeAddress(Integer addressId) throws AddressException {
        // Find the existing address by ID
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));

        // Delete the address from the repository
        addressRepository.delete(existingAddress);
    }


    public List<Address> getAllUserAddress(Integer userId) throws AddressException {
        List<Address> userAddressList = addressRepository.getUserAddressList(userId);
        if (userAddressList.isEmpty()) {
            System.out.println("empty");
            throw new AddressException("User does not have any address");
        }
        return userAddressList;
    }

}
