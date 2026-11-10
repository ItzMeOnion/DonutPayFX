
package com.donutpayfx;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public final class PayFXHud {

    private PayFXHud() {}

    public static void register() {
        HudElementRegistry.addLast(
            Identifier.of("donutpayfx", "payment_effects"),
            (context, tickCounter) -> render(context)
        );
    }

    private static void render(DrawContext context) {
        PayEffectManager effects = DonutPayFXClient.EFFECTS;

        if (!effects.active()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        Payment payment = effects.payment();
        PayEffectManager.Tier tier = effects.tier();

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        int age = effects.age();
        int remaining = effects.duration() - age;

        int color = switch (tier) {
            case MILLION -> 0xFFFFD700;
            case FIVE_M -> 0xFFFFA500;
            case TEN_M -> 0xFF9B59FF;
            case HUNDRED_M -> 0xFFFF3B30;
            case BILLION -> 0xFF40E8FF;
            default -> 0xFFFFFFFF;
        };

        // Legendary screen glow
        if (tier == PayEffectManager.Tier.BILLION) {
            int alpha = (int) (25 + Math.abs(Math.sin(age * 0.15)) * 45);
            context.fill(
                0, 0, width, height,
                (alpha << 24) | 0x003A16A8
            );
        }

        // Screen-wide energy particles
        int particleCount = switch (tier) {
            case MILLION -> 8;
            case FIVE_M -> 20;
            case TEN_M -> 35;
            case HUNDRED_M -> 55;
            case BILLION -> 90;
            default -> 0;
        };

        for (int i = 0; i < particleCount; i++) {
            int x = Math.floorMod(i * 97 + age * (i % 4 + 1), width);
            int y = Math.floorMod(i * 53 + age * (i % 3 + 1), height);

            int size = tier.ordinal() >= 4 ? 4 : 2;

            context.fill(
                x, y, x + size, y + size,
                color
            );
        }

        // Notification card
        int cardWidth = tier.ordinal() >= 4 ? 310 : 270;
        int cardHeight = tier.ordinal() >= 4 ? 94 : 76;

        int slide = Math.min(age * 12, cardWidth);
        int x = width - slide - 12;
        int y = 24;

        context.fill(
            x - 2, y - 2,
            x + cardWidth + 2, y + cardHeight + 2,
            color
        );

        context.fill(
            x, y,
            x + cardWidth, y + cardHeight,
            0xEE101020
        );

        String heading = switch (tier) {
            case MILLION -> "★ BIG PAYMENT!";
            case FIVE_M -> "✦ MEGA PAYMENT!";
            case TEN_M -> "⚡ HUGE PAYMENT!";
            case HUNDRED_M -> "★ JACKPOT!";
            case BILLION -> "✹ LEGENDARY BILLION!";
            default -> "PAYMENT";
        };

        String amount = "$" + String.format(
            java.util.Locale.US,
            "%,d",
            payment.amount()
        );

        context.drawCenteredTextWithShadow(
            client.textRenderer,
            heading,
            x + cardWidth / 2,
            y + 12,
            color
        );

        context.drawCenteredTextWithShadow(
            client.textRenderer,
            payment.player() + " paid you",
            x + cardWidth / 2,
            y + 31,
            0xFFFFFFFF
        );

        context.drawCenteredTextWithShadow(
            client.textRenderer,
            amount,
            x + cardWidth / 2,
            y + 49,
            color
        );

        if (tier.ordinal() >= 4) {
            context.drawCenteredTextWithShadow(
                client.textRenderer,
                "LEGENDARY!",
                x + cardWidth / 2,
                y + 70,
                0xFFFFFFFF
            );
        }

        // Keep the variable used for the animation's lifetime.
        if (remaining <= 20) {
            // The effect naturally fades out when its timer ends.
        }
    }
}
