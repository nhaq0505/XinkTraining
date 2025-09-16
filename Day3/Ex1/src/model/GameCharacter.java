package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class GameCharacter {
    protected String name;
    protected int level;
    protected int health, maxHealth;
    protected int mana, maxMana;
    protected Map<StatType, Integer> baseStats;
    protected List<Ability> abilities;
    private List<Equipment> inventory;
    private List<Equipment> equippedItems;
    private List<StatusEffect> activeEffects;

    public GameCharacter(String name) {
        this.name = name;
        this.level = 1;
        this.baseStats = new HashMap<>();
        this.abilities = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.equippedItems = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
    }
    // Combat
    public abstract void performBasicAttack(GameCharacter target);
    public abstract void useAbility(Ability ability, GameCharacter target);
    public abstract double calculateDamageReduction();

    // Progression
    public abstract void levelUp();
    public abstract void allocateStatPoint(StatType stat);

    // Common API
    public boolean isDead() { return health <= 0; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    protected void setLevel(int level) { this.level = level; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    protected void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    protected void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getBaseStat(StatType stat) {
        return baseStats.getOrDefault(stat, 0);
    }
    protected void setBaseStat(StatType stat, int value) {
        baseStats.put(stat, value);
    }
    public Map<StatType, Integer> getBaseStatsView() {
        return Map.copyOf(baseStats);
    }
    public List<Ability> getAbilities() {
        return List.copyOf(abilities);
    }
    protected void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    // Inventory & Equipment
    public void addToInventory(Equipment item) {
        if (item != null) inventory.add(item);
    }
    public boolean equip(Equipment item) {
        if (item == null) return false;
        if (!inventory.contains(item)) inventory.add(item);
        if (!equippedItems.contains(item)) {
            equippedItems.add(item);
            return true;
        }
        return false;
    }
    public boolean unequip(Equipment item) {
        return equippedItems.remove(item);
    }
    public List<Equipment> getEquippedItems() {
        return List.copyOf(equippedItems);
    }

    // Stat computation (base + equipment + status)
    private int getEquipmentBonus(StatType stat) {
        int bonus = 0;
        for (Equipment e : equippedItems) {
            bonus += e.getBonus(stat);
        }
        return bonus;
    }
    private int getStatusBonus(StatType stat) {
        int bonus = 0;
        for (StatusEffect se : activeEffects) {
            bonus += se.getStatModifier(stat);
        }
        return bonus;
    }
    public int getStat(StatType stat) {
        return getBaseStat(stat) + getEquipmentBonus(stat) + getStatusBonus(stat);
    }

    // Mana & HP
    protected boolean trySpendMana(int amount) {
        if (amount <= 0) return true;
        if (mana < amount) return false;
        mana -= amount;
        return true;
    }
    protected void restoreMana(int amount) {
        if (amount <= 0) return;
        mana = Math.min(maxMana, mana + amount);
    }
    public void heal(int amount) {
        if (amount <= 0) return;
        health = Math.min(maxHealth, health + amount);
    }
    public void takeDamage(int damage) {
        double dr = calculateDamageReduction();
        if (dr < 0) dr = 0;
        if (dr > 0.9) dr = 0.9;
        int reduced = (int) Math.max(0, Math.round(damage * (1 - dr)));
        health -= reduced;
        if (health < 0) health = 0;
        System.out.println(name + " takes " + reduced + " damage. Remaining HP: " + health);
    }

    // Status
    public void applyStatus(StatusEffect effect) {
        if (effect == null) return;
        activeEffects.add(effect);
        System.out.println(name + " gets status: " + effect.getName());
    }
    public void tickTurn() {
        // 1) Reduce cooldowns
        for (Ability a : abilities) a.reduceCooldown();
        // 2) Apply status effects (DoT/HoT), decrement duration, remove expired
        Iterator<StatusEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            StatusEffect se = it.next();
            if (se.getDamagePerTurn() > 0) {
                System.out.println(name + " suffers " + se.getDamagePerTurn() + " from " + se.getName());
                takeDamage(se.getDamagePerTurn());
            }
            if (se.getHealPerTurn() > 0) {
                int before = health;
                heal(se.getHealPerTurn());
                System.out.println(name + " is healed " + (health - before) + " by " + se.getName());
            }
            se.reduceDuration();
            if (!se.isActive()) {
                System.out.println(name + "'s status expired: " + se.getName());
                it.remove();
            }
        }
    }
}




