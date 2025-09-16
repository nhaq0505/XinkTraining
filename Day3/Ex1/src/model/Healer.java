package model;

public class Healer extends GameCharacter {

    public Healer(String name) {
        super(name);
        setMaxHealth(100);
        setMaxMana(130);
        setBaseStat(StatType.STRENGTH, 3);
        setBaseStat(StatType.AGILITY, 5);
        setBaseStat(StatType.INTELLIGENCE, 11);
        setBaseStat(StatType.DEFENSE, 4);
        addAbility(new Ability("Heal", 30, 15, 2, 0));
        addAbility(new Ability("Smite", 20, 10, 1, 0));
        heal(getMaxHealth());
        restoreMana(getMaxMana());
    }

    @Override
    public void performBasicAttack(GameCharacter target) {
        int damage = getStat(StatType.INTELLIGENCE) * 2;
        System.out.println(getName() + " strikes with holy wand at " + target.getName() + " for " + damage + " damage.");
        target.takeDamage(damage);
    }

    @Override
    public void useAbility(Ability ability, GameCharacter target) {
        if (!getAbilities().contains(ability)) { System.out.println("No such ability."); return; }
        if (!ability.isReady()) { System.out.println(ability.getName() + " is on cooldown."); return; }
        if (!trySpendMana(ability.getManaCost())) { System.out.println("Not enough mana."); return; }

        if ("Heal".equalsIgnoreCase(ability.getName())) {
            int amount = ability.getDamage() + getStat(StatType.INTELLIGENCE);
            int before = target.getHealth();
            target.heal(amount);
            int healed = target.getHealth() - before;
            System.out.println(getName() + " casts Heal on " + target.getName() + " restoring " + healed + " HP.");
        } else {
            System.out.println(getName() + " uses " + ability.getName() + " on " + target.getName() + " for " + ability.getDamage() + " damage.");
            target.takeDamage(ability.getDamage());
        }
        ability.use();
    }

    @Override
    public double calculateDamageReduction() {
        double reduction = getStat(StatType.DEFENSE) * 0.03;
        if (reduction < 0) reduction = 0;
        if (reduction > 0.75) reduction = 0.75;
        return reduction;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 12);
        heal(getMaxHealth());
        setMaxMana(getMaxMana() + 25);
        restoreMana(getMaxMana());

        setBaseStat(StatType.INTELLIGENCE, getBaseStat(StatType.INTELLIGENCE) + 2);
        setBaseStat(StatType.DEFENSE, getBaseStat(StatType.DEFENSE) + 1);
        setBaseStat(StatType.AGILITY, getBaseStat(StatType.AGILITY) + 1);

        System.out.println(getName() + " reached level " + getLevel() + " (Healer growth applied).");
    }

    @Override
    public void allocateStatPoint(StatType stat) {
        setBaseStat(stat, getBaseStat(stat) + 1);
        System.out.println("Allocated 1 point to " + stat + ". New value: " + getBaseStat(stat));
    }
}
