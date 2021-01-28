package net.citizensnpcs.api.gui;

import java.util.Collection;

/**
 * The concrete instance of a {@link MenuPattern}. Defines a (possibly multiline) pattern with bound slots/transitions
 * depending on the pattern.
 */
public class InventoryMenuPattern {
    private final MenuPattern info;
    private final Collection<InventoryMenuSlot> slots;
    private final Collection<InventoryMenuTransition> transitions;

    public InventoryMenuPattern(MenuPattern info, Collection<InventoryMenuSlot> slots,
            Collection<InventoryMenuTransition> transitions) {
        this.info = info;
        this.slots = slots;
        this.transitions = transitions;
    }

    public String getPattern() {
        return info.value();
    }

    public Collection<InventoryMenuSlot> getSlots() {
        return slots;
    }

    public Collection<InventoryMenuTransition> getTransitions() {
        return transitions;
    }
}
