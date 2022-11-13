package net.citizensnpcs.api.event;

import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.npc.NPC;

public class NPCKnockbackEvent extends NPCEvent {
    private double strength;
    private Vector vector;

    public NPCKnockbackEvent(NPC npc, double impulse, double dx, double dz) {
        super(npc);
        this.strength = impulse;
        this.vector = new Vector(dx, 0, dz);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Vector getKnockbackVector() {
        return vector;
    }

    public double getStrength() {
        return strength;
    }

    public void setKnockbackVector(Vector vector) {
        this.vector = vector;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private static final HandlerList handlers = new HandlerList();
}
