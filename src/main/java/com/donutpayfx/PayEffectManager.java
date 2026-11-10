
package com.donutpayfx;

public class PayEffectManager {

    public enum Tier {
        NONE,
        MILLION,
        FIVE_M,
        TEN_M,
        HUNDRED_M,
        BILLION
    }

    private Payment payment;
    private Tier tier = Tier.NONE;
    private int age = 0;
    private int duration = 0;

    public void trigger(Payment newPayment) {
        payment = newPayment;
        age = 0;
        tier = tierFor(newPayment.amount());

        duration = switch (tier) {
            case MILLION -> 100;
            case FIVE_M -> 140;
            case TEN_M -> 180;
            case HUNDRED_M -> 220;
            case BILLION -> 300;
            default -> 0;
        };
    }

    public void preview(Tier previewTier) {
        long amount = switch (previewTier) {
            case MILLION -> 1_000_000L;
            case FIVE_M -> 5_000_000L;
            case TEN_M -> 10_000_000L;
            case HUNDRED_M -> 100_000_000L;
            case BILLION -> 1_000_000_000L;
            default -> 0L;
        };

        trigger(new Payment(
            "PreviewPlayer",
            amount,
            System.currentTimeMillis()
        ));
    }

    public void tick() {
        if (age < duration) {
            age++;
        }
    }

    public boolean active() {
        return payment != null && age < duration;
    }

    public Payment payment() {
        return payment;
    }

    public Tier tier() {
        return tier;
    }

    public int age() {
        return age;
    }

    public int duration() {
        return duration;
    }

    public static Tier tierFor(long amount) {
        if (amount >= 1_000_000_000L) return Tier.BILLION;
        if (amount >= 100_000_000L) return Tier.HUNDRED_M;
        if (amount >= 10_000_000L) return Tier.TEN_M;
        if (amount >= 5_000_000L) return Tier.FIVE_M;
        if (amount >= 1_000_000L) return Tier.MILLION;

        return Tier.NONE;
    }
}
