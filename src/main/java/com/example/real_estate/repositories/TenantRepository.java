package com.example.real_estate.repositories;

/* imports */
import com.example.real_estate.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByUserId(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM tenant_apartment_applications WHERE apartment_id = :apartmentId", nativeQuery = true)
    void deleteApplicationsByApartmentId(@Param("apartmentId") Integer apartmentId);
}
