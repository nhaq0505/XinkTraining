import model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Setup characters
        Warrior warrior = new Warrior("Garen");
        Healer healer = new Healer("Soraka");
        Mage mage = new Mage("Lux");
        Archer archer = new Archer("Ashe");


        // Equip items (demo)
        Equipment plate = new Equipment("Steel Plate");
        plate.addBonus(StatType.DEFENSE, 4);
        plate.addBonus(StatType.STRENGTH, 2);
        warrior.addToInventory(plate);
        warrior.equip(plate);

        Equipment robe = new Equipment("Mystic Robe");
        robe.addBonus(StatType.DEFENSE, 2);
        robe.addBonus(StatType.INTELLIGENCE, 2);
        mage.addToInventory(robe);
        mage.equip(robe);

        Equipment longbow = new Equipment("Longbow");
        longbow.addBonus(StatType.AGILITY, 3);
        archer.addToInventory(longbow);
        archer.equip(longbow);

        // Teams
        Team alpha = new Team("Alpha");
        alpha.addMember(warrior);
        alpha.addMember(healer);

        Team beta = new Team("Beta");
        beta.addMember(mage);
        beta.addMember(archer);

        // Apply a status effect to demonstrate DoT/HoT ticking
        StatusEffect burnOnMage = new StatusEffect("Burn", 3, 5, 0, false, false);
        mage.applyStatus(burnOnMage);

        // Battle manager
        BattleManager bm = new BattleManager(alpha, beta);

        System.out.println("=== Battle Start ===");
        int turn = 1;
        int maxTurns = 50;

        while (!bm.isBattleOver() && turn <= maxTurns) {
            System.out.println("\n=== Turn " + turn + " ===");

            // Chọn mục tiêu còn sống
            GameCharacter targetFromBeta = pickFirstAlive(bm.getAlive(beta));
            GameCharacter targetFromAlpha = pickFirstAlive(bm.getAlive(alpha));

            // Team Alpha actions
            if (!warrior.isDead()) {
                if (targetFromBeta != null) {
                    Ability ps = firstAbility(warrior);
                    if (ps != null && ps.isReady()) warrior.useAbility(ps, targetFromBeta);
                    else warrior.performBasicAttack(targetFromBeta);
                }
            }

            if (!healer.isDead()) {

                boolean warriorLow = warrior.getHealth() < warrior.getMaxHealth() / 2;
                Ability healAb = abilityByName(healer, "Heal");
                Ability smiteAb = abilityByName(healer, "Smite");
                if (warriorLow && healAb != null && healAb.isReady()) {
                    healer.useAbility(healAb, warrior);
                } else if (targetFromBeta != null) {
                    if (smiteAb != null && smiteAb.isReady()) healer.useAbility(smiteAb, targetFromBeta);
                    else healer.performBasicAttack(targetFromBeta);
                }
            }

            // Team Beta actions
            if (!mage.isDead()) {
                if (targetFromAlpha != null) {
                    Ability fb = firstAbility(mage);
                    if (fb != null && fb.isReady()) mage.useAbility(fb, targetFromAlpha);
                    else mage.performBasicAttack(targetFromAlpha);
                }
            }

            if (!archer.isDead()) {
                if (targetFromAlpha != null) {
                    Ability pa = firstAbility(archer);
                    if (pa != null && pa.isReady()) archer.useAbility(pa, targetFromAlpha);
                    else archer.performBasicAttack(targetFromAlpha);
                }
            }

            // End of turn: tick cooldowns and status effects
            bm.nextTurn();

            // Print HP summary
            System.out.println("HP Summary: "
                    + warrior.getName() + "=" + warrior.getHealth() + "/"+ warrior.getMaxHealth() + ", "
                    + healer.getName()  + "=" + healer.getHealth()  + "/"+ healer.getMaxHealth()  + ", "
                    + mage.getName()    + "=" + mage.getHealth()    + "/"+ mage.getMaxHealth()    + ", "
                    + archer.getName()  + "=" + archer.getHealth()  + "/"+ archer.getMaxHealth());

            turn++;
        }

        System.out.println("\n=== Battle End ===");
        System.out.println("Winner: " + bm.getWinner());
    }

    private static GameCharacter pickFirstAlive(List<GameCharacter> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    private static Ability firstAbility(GameCharacter c) {
        return c.getAbilities().isEmpty() ? null : c.getAbilities().get(0);
    }

    private static Ability abilityByName(GameCharacter c, String name) {
        for (Ability a : c.getAbilities()) {
            if (a.getName().equalsIgnoreCase(name)) return a;
        }
        return null;
    }
}