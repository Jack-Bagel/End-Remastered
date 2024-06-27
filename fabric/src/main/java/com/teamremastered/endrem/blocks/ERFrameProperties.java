package com.teamremastered.endrem.blocks;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public enum ERFrameProperties implements StringIdentifiable {
    EMPTY,
    OLD_EYE,
    ROGUE_EYE,
    NETHER_EYE,
    COLD_EYE,
    CORRUPTED_EYE,
    MAGICAL_EYE,
    BLACK_EYE,
    LOST_EYE,
    WITHER_EYE,
    END_CRYSTAL_EYE,
    GUARDIAN_EYE,
    WITCH_EYE,
    CURSED_EYE,
    EXOTIC_EYE,
    EVIL_EYE,
    UNDEAD_EYE,
    CRYPTIC_EYE;

    @Override
    public String asString() {
        return this.asItemSerializedName();
    }

    public String asItemSerializedName() {
        return switch (this) {
            case EMPTY -> "empty";
            case OLD_EYE -> "old_eye";
            case ROGUE_EYE -> "rogue_eye";
            case NETHER_EYE -> "nether_eye";
            case COLD_EYE -> "cold_eye";
            case CORRUPTED_EYE -> "corrupted_eye";
            case MAGICAL_EYE -> "magical_eye";
            case BLACK_EYE -> "black_eye";
            case LOST_EYE -> "lost_eye";
            case WITHER_EYE -> "wither_eye";
            case END_CRYSTAL_EYE -> "end_crystal_eye";
            case GUARDIAN_EYE -> "guardian_eye";
            case WITCH_EYE -> "witch_eye";
            case CURSED_EYE -> "cursed_eye";
            case EXOTIC_EYE -> "exotic_eye";
            case EVIL_EYE -> "evil_eye";
            case UNDEAD_EYE -> "undead_eye";
            case CRYPTIC_EYE -> "cryptic_eye";
        };
    }

    public static ERFrameProperties getFramePropertyFromEye(Item eye) {

        for (ERFrameProperties property : ERFrameProperties.values()) {
            // match the serialized name of the property to the item name of the eye
            if (property.asString().equals(eye.toString())) {
                return property;
            }
        }

        return EMPTY;
    }
}


