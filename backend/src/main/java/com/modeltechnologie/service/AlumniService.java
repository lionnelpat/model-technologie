package com.modeltechnologie.service;

import com.modeltechnologie.entity.Alumni;


import java.util.List;
import java.util.Optional;


public interface AlumniService {

    Alumni createAlumni(Alumni alumni);

    Optional<Alumni> getAlumniById(Long id);

    Optional<Alumni> getAlumniByEmail(String email);

    List<Alumni> getAllActiveAlumni();

    List<Alumni> getAlumniByCountry(String country);

    List<Alumni> getAlumniByCompany(String company);

    List<Alumni> getAlumniBySkill(String skill);

    Alumni updateAlumni(Long id, Alumni updatedAlumni);

    void deleteAlumni(Long id);

}