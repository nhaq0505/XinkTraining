package model;

public class Warrior extends GameCharacter {

    public Warrior(String name) {
        super(name);
        setMaxHealth(150); 
        // init HP/MP
        // note: use setters and base stat methods
        setMaxMana(50);
        // initialize current values
        // base stats
        setBaseStat(StatType.STRENGTH, 10);
        setBaseStat(StatType.DEFENSE, 8);
        setBaseStat(StatType.AGILITY, 5);
        setBaseStat(StatType.INTELLIGENCE, 2);

        addAbility(new Ability("Power Strike", 30, 10, 2, 0));

        // set current to max
        heal(getMaxHealth());
        restoreMana(getMaxMana());
    }

    @Override
    public void performBasicAttack(GameCharacter target) {
        int damage = getStat(StatType.STRENGTH) * 2;
        System.out.println(getName() + " attacks " + target.getName() + " for " + damage + " damage.");
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
        return getStat(StatType.DEFENSE) * 0.05;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 20);
        // heal to full
        heal(getMaxHealth());
        setMaxMana(getMaxMana() + 5);
        // restore mana to full
        restoreMana(getMaxMana());
        System.out.println(getName() + " reached level " + getLevel());
    }
    @Override
    public void allocateStatPoint(StatType stat) {
        setBaseStat(stat, getBaseStat(stat) + 1);
        System.out.println("Allocated 1 point to " + stat + ". New value: " + getBaseStat(stat));
    }
}