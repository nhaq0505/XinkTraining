package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String name;
    private final List<GameCharacter> members = new ArrayList<>();

    public Team(String name) { this.name = name; }
    public String getName() { return name; }

    public void addMember(GameCharacter c) { if (c != null) members.add(c); }
    public List<GameCharacter> getMembers() { return List.copyOf(members); }

    public boolean isDefeated() {
        return members.stream().allMatch(GameCharacter::isDead);
    }
}