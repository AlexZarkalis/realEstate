package com.example.real_estate.repositories;

/* imports */
import com.example.real_estate.entities.Apartment;
import com.example.real_estate.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    List<Apartment> findByOwner(Owner owner);
    List<Apartment> findByPriceBetween(int minPrice, int maxPrice);
}