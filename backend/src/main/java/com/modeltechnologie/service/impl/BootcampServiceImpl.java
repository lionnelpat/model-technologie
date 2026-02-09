package com.modeltechnologie.service.impl;

import com.modeltechnologie.entity.Bootcamp;
import com.modeltechnologie.repository.BootcampRepository;
import com.modeltechnologie.service.BootcampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BootcampServiceImpl implements BootcampService {

    private final BootcampRepository bootcampRepository;

    @Transactional
    public Bootcamp createBootcamp(Bootcamp bootcamp) {
        log.info("Creating bootcamp: {}", bootcamp.getName());
        return bootcampRepository.save(bootcamp);
    }

    @Transactional(readOnly = true)
    public Optional<Bootcamp> getBootcampById(Long id) {
        return bootcampRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Bootcamp> getBootcampByName(String name) {
        return bootcampRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Bootcamp> getAllActiveBootcamps() {
        return bootcampRepository.findAllActive();
    }

    @Transactional(readOnly = true)
    public List<Bootcamp> getBootcampsByLevel(String level) {
        return bootcampRepository.findByLevelAndIsActiveTrue(level);
    }

    @Transactional(readOnly = true)
    public List<Bootcamp> getBootcampsByTargetSector(String sector) {
        return bootcampRepository.findByTargetSectorAndIsActiveTrue(sector);
    }

    @Transactional(readOnly = true)
    public List<Bootcamp> getBootcampsByStatus(String status) {
        return bootcampRepository.findByStatusAndIsActiveTrue(status);
    }

    @Transactional
    public Bootcamp updateBootcamp(Long id, Bootcamp updatedBootcamp) {
        log.info("Updating bootcamp: {}", id);
        Optional<Bootcamp> existingBootcamp = bootcampRepository.findById(id);

        if (existingBootcamp.isPresent()) {
            Bootcamp bootcamp = existingBootcamp.get();
            bootcamp.setDescription(updatedBootcamp.getDescription());
            bootcamp.setLevel(updatedBootcamp.getLevel());
            bootcamp.setDurationWeeks(updatedBootcamp.getDurationWeeks());
            bootcamp.setMaxStudents(updatedBootcamp.getMaxStudents());
            bootcamp.setPriceEuros(updatedBootcamp.getPriceEuros());
            bootcamp.setTargetSector(updatedBootcamp.getTargetSector());
            bootcamp.setStatus(updatedBootcamp.getStatus());

            return bootcampRepository.save(bootcamp);
        }

        throw new IllegalArgumentException("Bootcamp not found with id: " + id);
    }

    @Transactional
    public void deleteBootcamp(Long id) {
        log.info("Deleting bootcamp: {}", id);
        Optional<Bootcamp> bootcamp = bootcampRepository.findById(id);
        if (bootcamp.isPresent()) {
            bootcamp.get().setIsActive(false);
            bootcampRepository.save(bootcamp.get());
        }
    }
}