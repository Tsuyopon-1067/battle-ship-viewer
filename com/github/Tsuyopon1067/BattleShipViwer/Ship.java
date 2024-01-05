package com.github.Tsuyopon1067.BattleShipViwer;

public class Ship {
    private String name;
    private Hp hp;

    public Ship(String name, int maxHp) {
        this.name = name;
        this.hp = new Hp(maxHp);
    }

    public String getName() {
        return name;
    }

    public Hp getHp() {
        return hp;
    }

    public void appendJsoString(StringBuilder sb) {
        sb.append("{");
        sb.append("\"name\": \"" + name + "\", ");
        sb.append("\"hp\": " + hp.toJsonString());
        sb.append("}");
    }
}

class Hp {
    private int currentHp;
    private int maxHp;

    public Hp(int maxHp) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public String getCurrentHpInformation() {
        return String.format("%d/%d", currentHp, maxHp);
    }

    public void incrementHp() {
        currentHp++;
        if (currentHp > maxHp) {
            currentHp = maxHp;
            System.err.println("HP has exceeded the maximum value.");
        }
    }

    public void decreaseHp() {
        currentHp--;
        if (currentHp < 0) {
            currentHp = 0;
            System.err.println("HP has exceeded the minimum value.");
        }
    }

    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"currentHp\": " + currentHp + ", ");
        sb.append("\"maxHp\": " + maxHp);
        sb.append("}");
        return sb.toString();
    }
}