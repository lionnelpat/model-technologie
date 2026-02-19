// src/services/contact/ContactService.ts

import { httpClient } from '@/services/http/';
import { ContactFormData, ContactMessageResponse } from '@/types/contact.types';

export class ContactService {
    private readonly basePath = '/v1/contact';

    async submitContactForm(data: ContactFormData): Promise<ContactMessageResponse> {
        return httpClient.post<ContactMessageResponse>(this.basePath, data);
    }
}

export const contactService = new ContactService();
