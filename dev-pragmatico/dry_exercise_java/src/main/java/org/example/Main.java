package org.example;

import java.util.ArrayList;

public class Main {
     public static void main(String[] args) {
    }

    /*
    Contexto:
    - Esta classe estática apresenta uma versão mais modular do cálculo de imposto.

    Conceito principal:
    - A regra foi decomposta em métodos estáticos pequenos para nomear decisões e evitar repetição de detalhes de cálculo.

    Trade-off:
    - A extração aumenta coesão e reaproveitamento, mas distribui a leitura entre vários métodos auxiliares.
    */
    public static class TaxCalculator{
        private static final double BASE_RATE = 0.1;

        /*
        Decisão de design:
        - O método principal orquestra o fluxo e delega cada regra para um método com nome semântico.

        Aprendizado:
        - Em DRY, a extração mais útil é a que reduz duplicação relevante sem sacrificar demais a leitura local.
        */
        public static double calculate(double price, String region) {
            if (isEurope(region)) {
                return europeTax(price);
            }

            if (isHighValue(price)) {
                return highValueTax(price);
            }

            return defaultTax(price);
        }


        public static boolean isEurope(String region) {
            // Comparar a constante com o argumento evita NullPointerException quando region for null.
            // O ganho é pontual e técnico: o método fica mais defensivo sem adicionar ramificações extras.
            return "europe".equals(region);
        }

        public static boolean isHighValue(double price) {
            return price > 1000;
        }

        public static double europeTax(double price) {
            return round(price * (BASE_RATE + 0.05));
        }

        public static double highValueTax(double price) {
            return round(price * BASE_RATE * 0.9);
        }

        public static double defaultTax(double price) {
            return round(price * BASE_RATE);
        }


        /*
        Ponto de atenção:
        - Centralizar o arredondamento elimina duplicação técnica e mantém consistência entre as variações da regra.
        - Em produção, valores monetários costumam exigir tipos mais adequados que double por causa de precisão binária.
        */
        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }

    /*
    Contexto:
    - Esta segunda classe estática mantém a mesma regra em um estilo mais linear, com menos extração de métodos auxiliares.

    Trade-off:
    - A leitura fica concentrada em um único método, o que favorece entendimento imediato.
    - Em contrapartida, o reaproveitamento fica menos explícito do que na versão modular.
    */
    public static class TaxCalculatorLikeElixir {
        private static final double BASE_RATE = 0.1;

        /*
        Conceito principal:
        - Cada condição já calcula e retorna o resultado final, preservando um fluxo imperativo curto e sequencial.

        Aprendizado:
        - DRY não implica extrair toda repetição; em regras pequenas, manter a conta próxima da condição pode melhorar a legibilidade local.
        */
        public static double calculate(double price, String region) {

            if ("europe".equals(region)) {
                return round(price * (BASE_RATE + 0.05));
            }

            if (price > 1000) {
                return round(price * BASE_RATE * 0.9);
            }

            return round(price * BASE_RATE);
        }

        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }
}
