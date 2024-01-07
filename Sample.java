import com.github.Tsuyopon1067.BattleShipViwer.*;
import java.util.Random;

public class Sample {
    static final int HEIGHT = 5;
    static final int WIDTH = 5;
    static final int MAX_HP = 3;
    static final int FREQUENCY_MS = 1000;
    static final int PORT = 50000;
    static final String CLIENT_URL = "http://localhost:50001";

    public static void main(String... args) {
        // サーバーを起動
        BattleShipViwer battleShipViwer = new BattleShipViwer(PORT, CLIENT_URL);
        Thread serverThread = new Thread(battleShipViwer);
        serverThread.start();

        // ここでは例としてFREQUENCY_MS[ms]間隔でランダムな盤面データを登録している
        while (true) {
            try {
                Thread.sleep(FREQUENCY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            // 盤面データの登録
            BattleShipBoard board = register();
            battleShipViwer.registerBoard(board);
        }
    }

    public static BattleShipBoard register() {
        // 登録の流れ
        // 1. BattleShipBoardインスタンスを生成 (コンストラクタ引数は縦横)
        // 2. Shipインスタンスを生成 (コンストラクタ引数は名前と最大HP)
        // 3. BattleShipBoardインスタンスにShipインスタンスを登録 (setShipメソッドの引数はShipインスタンスとy座標とx座標)
        // 4. BattleShipViwerにBattleShipBoardインスタンスを渡す　(mainメソッドの中でやってる)
        BattleShipBoard res = new BattleShipBoard(HEIGHT, WIDTH);
        Ship ship1 = new Ship("ship1", MAX_HP);
        Ship ship2 = new Ship("ship2", MAX_HP);
        Ship ship3 = new Ship("ship3", MAX_HP);
        Ship ship4 = new Ship("ship4", MAX_HP);

        // ランダムな配置をする処理開始
        int[] point = new int[HEIGHT * WIDTH];
        for (int i = 0; i < point.length; i++) {
            point[i] = i;
        }
        for (int i = 0; i < point.length; i++) {
            Random r = new Random();
            int j = r.nextInt(point.length);
            int tmp = point[i];
            point [i] = point[j];
            point [j] = tmp;
        }
        // ランダムな配置をする処理終了

        // 船の位置を登録 (Ship, y, x)
        res.setShip(ship1, point[0]/HEIGHT, point[0]%WIDTH);
        res.setShip(ship2, point[1]/HEIGHT, point[1]%WIDTH);
        res.setShip(ship3, point[2]/HEIGHT, point[2]%WIDTH);
        res.setShip(ship4, point[3]/HEIGHT, point[3]%WIDTH);

        return res;
    }
}
