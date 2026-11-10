
package com.donutpayfx;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PaymentParser {

    private static final Pattern PAYMENT = Pattern.compile(
        "(?i)([A-Za-z0-9_]{1,16})\\s+paid you\\s+\\$?([0-9,.]+)\\s*([kmb]?)"
    );

    private PaymentParser() {}

    public static Optional<Payment> parse(String message) {
        Matcher matcher = PAYMENT.matcher(message);

        if (!matcher.find()) {
            return Optional.empty();
        }

        try {
            String player = matcher.group(1);
            String number = matcher.group(2).replace(",", "");
            String suffix = matcher.group(3).toLowerCase();

            double value = Double.parseDouble(number);

            value *= switch (suffix) {
                case "k" -> 1_000;
                case "m" -> 1_000_000;
                case "b" -> 1_000_000_000;
                default -> 1;
            };

            return Optional.of(new Payment(
                player,
                (long) value,
                System.currentTimeMillis()
            ));

        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }
}
