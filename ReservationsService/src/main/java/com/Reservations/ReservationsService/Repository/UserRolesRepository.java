package com.Reservations.ReservationsService.Repository;

import com.Reservations.ReservationsService.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole,Integer> {

    UserRole findByUserId(Integer userId);
    List<UserRole> findByRole(String role);

}


