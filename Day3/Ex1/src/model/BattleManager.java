package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BattleManager {
    private final Team teamA;
    private final Team teamB;

    public BattleManager(Team a, Team b) {
        this.teamA = a;
        this.teamB = b;
    }

    public void nextTurn() {
        tickTeam(teamA);
        tickTeam(teamB);
    }

    private void tickTeam(Team t) {
        for (GameCharacter c : t.getMembers()) {
            if (!c.isDead()) c.tickTurn();
        }
    }

    public boolean isBattleOver() {
        return teamA.isDefeated() || teamB.isDefeated();
    }

    public String getWinner() {
        if (!isBattleOver()) return "None";
        if (teamA.isDefeated() && teamB.isDefeated()) return "Draw";
        return teamA.isDefeated() ? teamB.getName() : teamA.getName();
    }

    public List<GameCharacter> getAlive(Team t) {
        return t.getMembers().stream().filter(c -> !c.isDead())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}