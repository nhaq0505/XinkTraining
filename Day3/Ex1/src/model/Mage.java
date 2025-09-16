package model;

public class Mage extends GameCharacter {

    public Mage(String name) {
        super(name);
        setMaxHealth(90);
        setMaxMana(120);
        setBaseStat(StatType.STRENGTH, 3);
        setBaseStat(StatType.AGILITY, 5);
        setBaseStat(StatType.INTELLIGENCE, 12);
        setBaseStat(StatType.DEFENSE, 3);
        addAbility(new Ability("Fireball", 35, 20, 3, 0));
        // set current to max
        heal(getMaxHealth());
        restoreMana(getMaxMana());
    }

    @Override
    public void performBasicAttack(GameCharacter target) {
        int damage = getStat(StatType.INTELLIGENCE) * 2;
        System.out.println(getName() + " casts a magic bolt at " + target.getName() + " for " + damage + " damage.");
        target.takeDamage(damage);
    }

    @Override
    public void useAbility(Ability ability, GameCharacter target) {
        if (!getAbilities().contains(ability)) { System.out.println("No such ability."); return; }
        if (!ability.isReady()) { System.out.println(ability.getName() + " is on cooldown."); return; }
        if (!trySpendMana(ability.getManaCost())) { System.out.println("Not enough mana."); return; }

        System.out.println(getName() + " uses " + ability.getName() + " on " + target.getName() + " for " + ability.getDamage() + " damage.");
        target.takeDamage(ability.getDamage());
        ability.use();
    }

    @Override
    public double calculateDamageReduction() {
        // Pháp sư mặc áo choàng: phòng thủ thấp hơn
        double reduction = getStat(StatType.DEFENSE) * 0.02;
        if (reduction < 0) reduction = 0;
        if (reduction > 0.75) reduction = 0.75;
        return reduction;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 10);
        heal(getMaxHealth());
        setMaxMana(getMaxMana() + 20);
        restoreMana(getMaxMana());
        setBaseStat(StatType.INTELLIGENCE, getBaseStat(StatType.INTELLIGENCE) + 2);
        setBaseStat(StatType.AGILITY, getBaseStat(StatType.AGILITY) + 1);
        setBaseStat(StatType.DEFENSE, getBaseStat(StatType.DEFENSE) + 1);
        System.out.println(getName() + " reached level " + getLevel() + " (Mage growth applied).");
    }

    @Override
    public void allocateStatPoint(StatType stat) {
        setBaseStat(stat, getBaseStat(stat) + 1);
        System.out.println("Allocated 1 point to " + stat + ". New value: " + getBaseStat(stat));
    }
}