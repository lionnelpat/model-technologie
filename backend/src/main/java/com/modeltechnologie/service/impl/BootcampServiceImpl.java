package com.modeltechnologie.service.impl;

import com.modeltechnologie.dto.BootcampCreateDTO;
import com.modeltechnologie.dto.BootcampResponseDTO;
import com.modeltechnologie.dto.BootcampUpdateDTO;
import com.modeltechnologie.entity.Bootcamp;
import com.modeltechnologie.exception.BootcampNotFoundException;
import com.modeltechnologie.exception.DuplicateBootcampException;
import com.modeltechnologie.mapper.BootcampMapper;
import com.modeltechnologie.repository.BootcampRepository;
import com.modeltechnologie.service.BootcampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BootcampServiceImpl implements BootcampService {

    private final BootcampRepository bootcampRepository;
    private final BootcampMapper bootcampMapper;

    @Override
    @Transactional
    public BootcampResponseDTO createBootcamp(BootcampCreateDTO createDTO) {
        log.info("Création d'un nouveau bootcamp: {}", createDTO.getTitle());

        // ✅ Vérifier les doublons
        if (bootcampRepository.existsByNameIgnoreCase(createDTO.getTitle())) {
            log.warn("Tentative de création d'un bootcamp en doublon: {}", createDTO.getTitle());
            throw new DuplicateBootcampException("Un bootcamp avec le nom '" + createDTO.getTitle() + "' existe déjà");
        }

        // ✅ Mapper et sauvegarder
        Bootcamp bootcamp = bootcampMapper.toEntity(createDTO);
        Bootcamp saved = bootcampRepository.save(bootcamp);

        log.info("Bootcamp créé avec succès, ID: {}", saved.getId());
        return bootcampMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BootcampResponseDTO getBootcampById(Long id) {
        log.debug("Récupération du bootcamp avec l'ID: {}", id);

        Bootcamp bootcamp = bootcampRepository.findByIdWithBenefits(id)
                .orElseThrow(() -> {
                    log.warn("Bootcamp non trouvé, ID: {}", id);
                    return new BootcampNotFoundException("Bootcamp avec l'ID " + id + " non trouvé");
                });

        return bootcampMapper.toResponseDTO(bootcamp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BootcampResponseDTO> getAllActiveBootcamps() {
        log.debug("Récupération de tous les bootcamps actifs");

        return bootcampRepository.findAllActive()
                .stream()
                .map(bootcampMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BootcampResponseDTO getBootcampByName(String name) {
        log.debug("Récupération du bootcamp par nom: {}", name);

        Bootcamp bootcamp = bootcampRepository.findByNameWithBenefits(name)
                .orElseThrow(() -> {
                    log.warn("Bootcamp non trouvé, nom: {}", name);
                    return new BootcampNotFoundException("Bootcamp '" + name + "' non trouvé");
                });

        return bootcampMapper.toResponseDTO(bootcamp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BootcampResponseDTO> getFeaturedBootcamps() {
        log.debug("Récupération des bootcamps en vedette");

        return bootcampRepository.findFeaturedBootcamps()
                .stream()
                .map(bootcampMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BootcampResponseDTO> getBootcampsByLevel(String level) {
        log.debug("Récupération des bootcamps de niveau: {}", level);

        return bootcampRepository.findByLevel(level)
                .stream()
                .map(bootcampMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BootcampResponseDTO> getBootcampsByTargetSector(String sector) {
        log.debug("Récupération des bootcamps du secteur: {}", sector);

        return bootcampRepository.findByTargetSector(sector)
                .stream()
                .map(bootcampMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BootcampResponseDTO> getBootcampsByStatus(String status) {
        log.debug("Récupération des bootcamps avec statut: {}", status);

        return bootcampRepository.findByStatus(status)
                .stream()
                .map(bootcampMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BootcampResponseDTO updateBootcamp(Long id, BootcampUpdateDTO updateDTO) {
        log.info("Mise à jour du bootcamp avec l'ID: {}", id);

        // Utiliser findByIdWithBenefits pour charger les benefits en LAZY
        // car updateBenefits() appelle bootcamp.getBenefits().clear()
        Bootcamp bootcamp = bootcampRepository.findByIdWithBenefits(id)
                .orElseThrow(() -> new BootcampNotFoundException("Bootcamp avec l'ID " + id + " non trouvé"));

        // Vérifier les doublons lors de la mise à jour du nom
        if (updateDTO.getTitle() != null &&
                !bootcamp.getName().equals(updateDTO.getTitle()) &&
                bootcampRepository.existsByNameIgnoreCase(updateDTO.getTitle())) {
            throw new DuplicateBootcampException("Un bootcamp avec le nom '" + updateDTO.getTitle() + "' existe déjà");
        }

        bootcampMapper.updateEntity(bootcamp, updateDTO);
        Bootcamp updated = bootcampRepository.save(bootcamp);

        log.info("Bootcamp mis à jour avec succès, ID: {}", updated.getId());
        return bootcampMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteBootcamp(Long id) {
        log.info("Suppression du bootcamp avec l'ID: {}", id);

        Bootcamp bootcamp = bootcampRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tentative de suppression d'un bootcamp inexistant, ID: {}", id);
                    return new BootcampNotFoundException("Bootcamp avec l'ID " + id + " non trouvé");
                });

        bootcampRepository.delete(bootcamp);
        log.info("Bootcamp supprimé avec succès, ID: {}", id);
    }
}