// src/types/contact.types.ts

export interface ContactFormData {
    firstName: string;
    lastName: string;
    email: string;
    phone?: string;
    company?: string;
    subject: string;
    message: string;
}

export interface ContactMessageResponse {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    subject: string;
    createdAt: string;
}
