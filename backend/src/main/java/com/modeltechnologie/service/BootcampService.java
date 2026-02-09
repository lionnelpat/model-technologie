package com.modeltechnologie.service;

import com.modeltechnologie.entity.Bootcamp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface BootcampService {

    /**
     * Create a new bootcamp
     * @param bootcamp
     * @return
     */
    Bootcamp createBootcamp(Bootcamp bootcamp);

    /**
     * Get bootcamp by ID
     * @param id
     * @return
     */
    Optional<Bootcamp> getBootcampById(Long id);

    /**
     * Get bootcamp by name
     * @param name
     * @return
     */
    Optional<Bootcamp> getBootcampByName(String name);

    /**
     * Get all active bootcamps
     * @return
     */
    List<Bootcamp> getAllActiveBootcamps();

    /**
     * Get bootcamps by level
     * @param level
     * @return
     */
    List<Bootcamp> getBootcampsByLevel(String level);

    /**
     * Get bootcamps by target sector
     * @param sector
     * @return
     */
    List<Bootcamp> getBootcampsByTargetSector(String sector);


    /**
     * Get bootcamps by status
     * @param status
     * @return
     */
    List<Bootcamp> getBootcampsByStatus(String status);

    /**
     * Update bootcamp details
     * @param id
     * @param updatedBootcamp
     * @return
     */

    Bootcamp updateBootcamp(Long id, Bootcamp updatedBootcamp);


    /**
     * Delete a bootcamp (soft delete)
     * @param id
     */
    void deleteBootcamp(Long id);
}