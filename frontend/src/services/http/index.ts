import { HttpClient } from './HttpClient';

// ✅ Créer l'instance UNE SEULE FOIS
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://staging.model-technologie.com:8080/api';

export const httpClient = new HttpClient({
    baseURL: API_BASE_URL,
    timeout: 10000,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export { HttpClient };
export * from './HttpClient';
