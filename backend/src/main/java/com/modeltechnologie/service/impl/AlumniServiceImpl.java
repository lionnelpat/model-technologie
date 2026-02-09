package com.modeltechnologie.service.impl;

import com.modeltechnologie.entity.Alumni;
import com.modeltechnologie.repository.AlumniRepository;
import com.modeltechnologie.service.AlumniService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlumniServiceImpl implements AlumniService {

    private final AlumniRepository alumniRepository;

    @Transactional
    public Alumni createAlumni(Alumni alumni) {
        log.info("Creating alumni: {}", alumni.getEmail());
        return alumniRepository.save(alumni);
    }

    @Transactional(readOnly = true)
    public Optional<Alumni> getAlumniById(Long id) {
        return alumniRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Alumni> getAlumniByEmail(String email) {
        return alumniRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Alumni> getAllActiveAlumni() {
        return alumniRepository.findAllActive();
    }

    @Transactional(readOnly = true)
    public List<Alumni> getAlumniByCountry(String country) {
        return alumniRepository.findByCountryAndIsActiveTrue(country);
    }

    @Transactional(readOnly = true)
    public List<Alumni> getAlumniByCompany(String company) {
        return alumniRepository.findByCurrentCompanyAndIsActiveTrue(company);
    }

    @Transactional(readOnly = true)
    public List<Alumni> getAlumniBySkill(String skill) {
        return alumniRepository.findBySkill(skill);
    }

    @Transactional
    public Alumni updateAlumni(Long id, Alumni updatedAlumni) {
        log.info("Updating alumni: {}", id);
        Optional<Alumni> existingAlumni = alumniRepository.findById(id);

        if (existingAlumni.isPresent()) {
            Alumni alumni = existingAlumni.get();
            alumni.setFirstName(updatedAlumni.getFirstName());
            alumni.setLastName(updatedAlumni.getLastName());
            alumni.setPhoneNumber(updatedAlumni.getPhoneNumber());
            alumni.setCountry(updatedAlumni.getCountry());
            alumni.setCity(updatedAlumni.getCity());
            alumni.setLinkedinProfile(updatedAlumni.getLinkedinProfile());
            alumni.setGithubProfile(updatedAlumni.getGithubProfile());
            alumni.setBio(updatedAlumni.getBio());
            alumni.setCurrentJobTitle(updatedAlumni.getCurrentJobTitle());
            alumni.setCurrentCompany(updatedAlumni.getCurrentCompany());
            alumni.setSkills(updatedAlumni.getSkills());
            alumni.setPortfolioUrl(updatedAlumni.getPortfolioUrl());

            return alumniRepository.save(alumni);
        }

        throw new IllegalArgumentException("Alumni not found with id: " + id);
    }

    @Transactional
    public void deleteAlumni(Long id) {
        log.info("Deleting alumni: {}", id);
        Optional<Alumni> alumni = alumniRepository.findById(id);
        if (alumni.isPresent()) {
            alumni.get().setIsActive(false);
            alumniRepository.save(alumni.get());
        }
    }
}