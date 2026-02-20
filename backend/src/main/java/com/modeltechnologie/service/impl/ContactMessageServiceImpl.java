package com.modeltechnologie.service.impl;

import com.modeltechnologie.dto.ContactMessageCreateDTO;
import com.modeltechnologie.dto.ContactMessageResponseDTO;
import com.modeltechnologie.entity.ContactMessage;
import com.modeltechnologie.repository.ContactMessageRepository;
import com.modeltechnologie.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    @Override
    @Transactional
    public ContactMessageResponseDTO saveMessage(ContactMessageCreateDTO createDTO) {
        log.info("Sauvegarde du message de contact de: {} {}", createDTO.getFirstName(), createDTO.getLastName());

        ContactMessage message = ContactMessage.builder()
                .firstName(createDTO.getFirstName())
                .lastName(createDTO.getLastName())
                .email(createDTO.getEmail())
                .phone(createDTO.getPhone())
                .company(createDTO.getCompany())
                .subject(createDTO.getSubject())
                .message(createDTO.getMessage())
                .build();

        ContactMessage saved = contactMessageRepository.save(message);

        return ContactMessageResponseDTO.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .subject(saved.getSubject())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
