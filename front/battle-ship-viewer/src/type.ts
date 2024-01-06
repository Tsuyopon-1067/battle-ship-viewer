export interface TableDataType {
  width: number;
  height: number;
  board: CellType[][];
}

export type CellType = ShipType | null;

export interface ShipType {
  name: string;
  hp: {
    currentHp: number;
    maxHp: number;
  };
}

export const initialBattleTableData = {
    width: 5,
    height: 5,
    board: [
        [null, null, null, null, null],
        [null, null, null, null, null],
        [null, null, null, null, null],
        [null, null, null, null, null],
        [null, null, null, null, null]
        ]
}