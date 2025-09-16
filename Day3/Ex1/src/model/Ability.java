package model;

public class Ability {
    private String name;
    private int damage;
    private int manaCost;
    private int cooldown;
    private int currentCooldown;

    public Ability(String name, int damage, int manaCost, int cooldown, int currentCooldown) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.currentCooldown = currentCooldown;
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public void use() {
        currentCooldown = cooldown;
    }

    public void reduceCooldown() {
        if (currentCooldown > 0) currentCooldown--;
    }

    public int getDamage() { return damage; }
    public int getManaCost() { return manaCost; }
    public String getName() { return name; }
}