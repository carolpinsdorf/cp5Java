package org.example.service;

import java.time.LocalDate;

public class EntityValidator {
    // validacoes tipo nao deixar receber input vazio, se o ano é válido etc...

    public boolean checkString (String string){
        return string == null || string.isEmpty();
    }

    public boolean checkID(Integer id) {
        return id == null || id <= 0; // Verifica se o ID não é nulo e é positivo
    }
    public boolean checkYear(int year) {
        // Obtém o ano atual
        int currentYear = LocalDate.now().getYear();

        // Verifica se o ano está no intervalo de 1900 até o ano atual
        return year < 1900 || year > currentYear;
    }
    public boolean checkDuration(int duration) {
        return duration >= 60 && duration <= 900; // entre 1 min e 15min
    }
}
