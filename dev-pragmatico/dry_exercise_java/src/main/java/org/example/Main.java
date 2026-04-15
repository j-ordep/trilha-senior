package org.example;

import java.util.ArrayList;

public class Main {
     public static void main(String[] args) {
    }

    public static class TaxCalculator{
        private static final double BASE_RATE = 0.1;

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
            return "europe".equals(region); // essa forma é mais segura, se fosse o contrario poderia gerar null pointer
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


        private static double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
    }

    public static class TaxCalculatorLikeElixir {
        private static final double BASE_RATE = 0.1;

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
