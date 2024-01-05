package com.github.Tsuyopon1067.BattleShipViwer;

import java.util.ArrayList;
import java.util.Iterator;

public class BattleShipBoard {
    private ArrayList<ArrayList<Ship>> board;
    private int height;
    private int width;

    public BattleShipBoard(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new ArrayList<ArrayList<Ship>>(height);
        for (int i = 0; i < height; i++) {
            var line = new ArrayList<Ship>(width);
                for (int j = 0; j < width; j++) {
                    line.add(null);
                }
            board.add(line);
        }
    }

    public void setShip(Ship ship, int y, int x) {
        board.get(y).set(x, ship);
    }

    public String toJSonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"width\": " + width + ", ");
        sb.append("\"height\": " + height + ", ");
        sb.append("\"board\":");
        appendLineJsonString(sb);
        sb.append("}");
        return sb.toString();
    }

    private void appendLineJsonString(StringBuilder sb) {
        sb.append("[");
        Iterator<ArrayList<Ship>> iterator = board.iterator();
        while (iterator.hasNext()) {
            ArrayList<Ship> line = iterator.next();
            sb.append("[");
            appendSquaresJsonString(sb, line);
            sb.append("]");
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("]");
    }

    private void appendSquaresJsonString(StringBuilder sb, ArrayList<Ship> line) {
        Iterator<Ship> iterator = line.iterator();
        while (iterator.hasNext()) {
            Ship ship = iterator.next();
            if (ship != null) {
                ship.appendJsoString(sb);
            } else {
                sb.append("null");
            }
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
    }
}