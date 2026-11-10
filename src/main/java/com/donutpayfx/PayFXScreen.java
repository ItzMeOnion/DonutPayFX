
package com.donutpayfx;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PayFXScreen extends Screen {

    public PayFXScreen() {
        super(Text.literal("DonutPayFX"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        addDrawableChild(
            ButtonWidget.builder(
                Text.literal("Preview $1M - Golden Alert"),
                button -> DonutPayFXClient.EFFECTS.preview(
                    PayEffectManager.Tier.MILLION
                )
            ).dimensions(centerX - 105, centerY - 75, 210, 20).build()
        );

        addDrawableChild(
            ButtonWidget.builder(
                Text.literal("Preview $5M - Fireworks"),
                button -> DonutPayFXClient.EFFECTS.preview(
                    PayEffectManager.Tier.FIVE_M
                )
            ).dimensions(centerX - 105, centerY - 48, 210, 20).build()
        );

        addDrawableChild(
            ButtonWidget.builder(
                Text.literal("Preview $10M - Lightning"),
                button -> DonutPayFXClient.EFFECTS.preview(
                    PayEffectManager.Tier.TEN_M
                )
            ).dimensions(centerX - 105, centerY - 21, 210, 20).build()
        );

        addDrawableChild(
            ButtonWidget.builder(
                Text.literal("Preview $100M - Jackpot"),
                button -> DonutPayFXClient.EFFECTS.preview(
                    PayEffectManager.Tier.HUNDRED_M
                )
            ).dimensions(centerX - 105, centerY + 6, 210, 20).build()
        );

        addDrawableChild(
            ButtonWidget.builder(
                Text.literal("Preview $1B - LEGENDARY"),
                button -> DonutPayFXClient.EFFECTS.preview(
                    PayEffectManager.Tier.BILLION
                )
            ).dimensions(centerX - 105, centerY + 33, 210, 20).build()
        );

        addDrawableChild(
            ButtonWidget.builder(
                Text.literal("Close"),
                button -> close()
            ).dimensions(centerX - 50, centerY + 68, 100, 20).build()
        );
    }

    @Override
    public void render(
        DrawContext context,
        int mouseX,
        int mouseY,
        float delta
    ) {
        renderBackground(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(
            textRenderer,
            Text.literal("DONUT PAY FX"),
            width / 2,
            height / 2 - 115,
            0xFFFFD700
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
