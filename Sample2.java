import com.github.Tsuyopon1067.BattleShipViwer.*;
import java.util.Scanner;

public class Sample2 {
    static final int HEIGHT = 5;
    static final int WIDTH = 5;
    static final int MAX_HP = 3;
    static final int PORT = 50000;
    static final String CLIENT_URL = "http://localhost:50001";

    public static void main(String... args) {
        // サーバーを起動
        BattleShipViwer battleShipViwer = new BattleShipViwer(PORT, CLIENT_URL);
        Thread serverThread = new Thread(battleShipViwer);
        serverThread.start();

        // ここでは例としてFREQUENCY_MS[ms]間隔でランダムな盤面データを登録している
        while (true) {
            // 盤面データの登録
            BattleShipBoard board = register();
            battleShipViwer.registerBoard(board);
        }
    }

    public static BattleShipBoard register() {
        BattleShipBoard res = new BattleShipBoard(HEIGHT, WIDTH);
        for (int i = 0; i < 1; i++) {
            ShipAndPos ship = scanShip(MAX_HP, i+1);
            res.setShip(ship.ship, ship.y, ship.x);
        }

        return res;
    }

    static ShipAndPos scanShip(int MAX_HP, int count) {
        Scanner sc = new Scanner(System.in);
        String countStr = String.valueOf(count);
        System.out.print("name" + countStr + ": ");
        String name = sc.next();
        System.out.print("HP: ");
        int currentHp = sc.nextInt();
        System.out.print("y: ");
        int y = sc.nextInt();
        System.out.print("x: ");
        int x = sc.nextInt();
        return new ShipAndPos(name, MAX_HP, currentHp, y, x);
    }
}


class ShipAndPos {
    public int y;
    public int x;
    public Ship ship;

    public ShipAndPos(String name, int MAX_HP, int hp, int y, int x) {
        this.y = y;
        this.x = x;
        this.ship = new Ship(name, MAX_HP, hp);
    }
}
