package model;

public class Archer extends GameCharacter {

    public Archer(String name) {
        super(name);
        setMaxHealth(120);
        setMaxMana(80);
        setBaseStat(StatType.STRENGTH, 6);
        setBaseStat(StatType.AGILITY, 10);
        setBaseStat(StatType.INTELLIGENCE, 4);
        setBaseStat(StatType.DEFENSE, 5);
        addAbility(new Ability("Piercing Arrow", 25, 15, 2, 0));
        heal(getMaxHealth());
        restoreMana(getMaxMana());
    }

    @Override
    public void performBasicAttack(GameCharacter target) {
        int damage = getStat(StatType.AGILITY) * 2;
        System.out.println(getName() + " shoots a basic arrow at " + target.getName() + " for " + damage + " damage.");
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
        double reduction = getStat(StatType.DEFENSE) * 0.03;
        if (reduction < 0) reduction = 0;
        if (reduction > 0.8) reduction = 0.8;
        return reduction;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 15);
        heal(getMaxHealth());
        setMaxMana(getMaxMana() + 10);
        restoreMana(getMaxMana());

        setBaseStat(StatType.AGILITY, getBaseStat(StatType.AGILITY) + 2);
        setBaseStat(StatType.STRENGTH, getBaseStat(StatType.STRENGTH) + 1);
        setBaseStat(StatType.DEFENSE, getBaseStat(StatType.DEFENSE) + 1);

        System.out.println(getName() + " reached level " + getLevel() + " (Archer growth applied).");
    }

    @Override
    public void allocateStatPoint(StatType stat) {
        setBaseStat(stat, getBaseStat(stat) + 1);
        System.out.println("Allocated 1 point to " + stat + ". New value: " + getBaseStat(stat));
    }
}
