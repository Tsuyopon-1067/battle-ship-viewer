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