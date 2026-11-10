
package com.donutpayfx;

public record Payment(
    String player,
    long amount,
    long timestamp
) {
}
