// src/hooks/mutations/useContactMutations.ts

import { useMutation } from '@tanstack/react-query';
import { contactService } from '@/services/contact/ContactService';
import { ContactFormData } from '@/types/contact.types';

export function useSubmitContact() {
    return useMutation({
        mutationFn: (data: ContactFormData) => contactService.submitContactForm(data),
    });
}
