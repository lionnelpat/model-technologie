package com.modeltechnologie.service;

import com.modeltechnologie.dto.ContactMessageCreateDTO;
import com.modeltechnologie.dto.ContactMessageResponseDTO;

public interface ContactMessageService {
    ContactMessageResponseDTO saveMessage(ContactMessageCreateDTO createDTO);
}
