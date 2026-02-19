package com.modeltechnologie.service;

import com.modeltechnologie.dto.BootcampCreateDTO;
import com.modeltechnologie.dto.BootcampResponseDTO;
import com.modeltechnologie.dto.BootcampUpdateDTO;
import com.modeltechnologie.exception.BootcampNotFoundException;
import com.modeltechnologie.exception.DuplicateBootcampException;

import java.util.List;

public interface BootcampService {
    BootcampResponseDTO createBootcamp(BootcampCreateDTO createDTO) throws DuplicateBootcampException;
    BootcampResponseDTO getBootcampById(Long id) throws BootcampNotFoundException;
    BootcampResponseDTO getBootcampByName(String name) throws BootcampNotFoundException;
    List<BootcampResponseDTO> getAllActiveBootcamps();
    List<BootcampResponseDTO> getBootcampsByLevel(String level);
    List<BootcampResponseDTO> getBootcampsByTargetSector(String sector);
    List<BootcampResponseDTO> getBootcampsByStatus(String status);
    List<BootcampResponseDTO> getFeaturedBootcamps();
    BootcampResponseDTO updateBootcamp(Long id, BootcampUpdateDTO updateDTO) throws BootcampNotFoundException;
    void deleteBootcamp(Long id) throws BootcampNotFoundException;
}