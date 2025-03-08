package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {


    @Query("SELECT a FROM Address a  WHERE a.user.userId = :userId")
    List<Address> getUserAddressList(Integer userId);
}
