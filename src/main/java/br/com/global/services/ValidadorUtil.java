package br.com.global.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class ValidadorUtil {
    /**
     * Verifica se uma pessoa é maior de idade.
     *
     * @param dataNascimento A data de nascimento da pessoa.
     * @return True se for maior de idade, False caso contrário.
     */
    public static boolean isMaiorDeIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return false;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears() >= 18;
    }

    /**
     * Verifica se o CPF é válido.
     *
     * @param cpf O CPF a ser validado.
     * @return True se for válido, False caso contrário.
     */
    public static boolean isCpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Validação dos dígitos verificadores
        int[] pesos1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int primeiroDigito = calcularDigito(cpf.substring(0, 9), pesos1);
        int segundoDigito = calcularDigito(cpf.substring(0, 9) + primeiroDigito, pesos2);

        return cpf.equals(cpf.substring(0, 9) + primeiroDigito + segundoDigito);
    }

    private static int calcularDigito(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    /**
     * Verifica se a CNH é válida.
     *
     * Obs: Esta validação é simplificada, pois uma validação real envolveria integrações externas.
     *
     * @param cnh A CNH a ser validada.
     * @return True se for válida, False caso contrário.
     */
    public static boolean isCnhValida(String cnh) {
        if (cnh == null || cnh.length() != 11 || !cnh.matches("\\d+")) {
            return false;
        }

        // Regra de validação simplificada: o número da CNH não pode ser todos iguais.
        return cnh.chars().distinct().count() > 1;
    }

    /**
     * Verifica se um email é válido.
     *
     * @param email O email a ser validado.
     * @return True se for válido, False caso contrário.
     */
    public static boolean isEmailValido(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
}
