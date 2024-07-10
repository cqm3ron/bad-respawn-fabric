package com.arndew.badrespawn.event;

import com.arndew.badrespawn.effect.ModStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Random;

public class AfterRespawnHandler implements BedAnchorRespawnCallback, ServerPlayerEvents.AfterRespawn {
    StatusEffectInstance fragility = new StatusEffectInstance(ModStatusEffects.FRAGILITY, -1);
    StatusEffectInstance hallucination = new StatusEffectInstance(ModStatusEffects.HALLUCINATION, 6000);

    private boolean eventTriggered = false;

    @Override
    public void onBlockAnchorRespawn() {
        eventTriggered = true;
    }

    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (eventTriggered) {
            generatePlayerHealth(newPlayer);
            generatePlayerHunger(newPlayer);

            newPlayer.addStatusEffect(fragility);
            newPlayer.addStatusEffect(hallucination);
            eventTriggered = false;
        }
    }

    private void generatePlayerHealth(ServerPlayerEntity player) {
        Random randomHealth = new Random();
        player.setHealth(randomHealth.nextInt(6, 10));
    }

    private void generatePlayerHunger(ServerPlayerEntity player) {
        Random randomFoodLevel = new Random();
        player.getHungerManager().setFoodLevel(randomFoodLevel.nextInt(6, 8));
    }
}
