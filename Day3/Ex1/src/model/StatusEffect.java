package model;

import java.util.HashMap;
import java.util.Map;

public class StatusEffect {
    private String name;
    private int duration; // turns remaining
    private int damagePerTurn;
    private int healPerTurn;
    private boolean stun;
    private boolean silence;
    private Map<StatType, Integer> statModifiers = new HashMap<>();

    public StatusEffect(String name, int duration) {
        this(name, duration, 0, 0, false, false);
    }

    public StatusEffect(String name, int duration, int damagePerTurn, int healPerTurn, boolean stun, boolean silence) {
        this.name = name;
        this.duration = duration;
        this.damagePerTurn = damagePerTurn;
        this.healPerTurn = healPerTurn;
        this.stun = stun;
        this.silence = silence;
    }

    public void addStatModifier(StatType stat, int value) {
        statModifiers.put(stat, statModifiers.getOrDefault(stat, 0) + value);
    }

    public int getStatModifier(StatType stat) {
        return statModifiers.getOrDefault(stat, 0);
    }

    public int getDamagePerTurn() { return damagePerTurn; }
    public int getHealPerTurn() { return healPerTurn; }
    public boolean isStun() { return stun; }
    public boolean isSilence() { return silence; }
    public void reduceDuration() { duration--; }
    public boolean isActive() { return duration > 0; }
    public String getName() { return name; }
}
