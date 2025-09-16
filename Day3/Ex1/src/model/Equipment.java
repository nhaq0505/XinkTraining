package model;

import java.util.HashMap;
import java.util.Map;

public class Equipment {
    private String name;
    private Map<StatType, Integer> statBonus;

    public Equipment(String name) {
        this.name = name;
        this.statBonus = new HashMap<>();
    }

    public void addBonus(StatType stat, int value) {
        statBonus.put(stat, value);
    }

    public int getBonus(StatType stat) {
        return statBonus.getOrDefault(stat, 0);
    }

    public String getName() { return name; }
}