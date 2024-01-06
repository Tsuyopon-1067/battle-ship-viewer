import React, { useState, useEffect } from 'react';

import { TableDataType, CellType, initialBattleTableData } from './type';
import styles from './BattleTable.module.css';
import submarineIcon from './submarine.svg';

function Cell({ cellData }: { cellData: CellType }) {
  if (cellData) {
    return (
      <td className={styles.td}>
        <div className={styles.submarineInfo}>
          <p className={styles.submarineInfoText}>
            <img src={submarineIcon} className={styles.submarineInfoIcon} alt="icon" />
            {cellData.name}
          </p>
          <p className={styles.submarineInfoTextHp}>
              HP: {cellData.hp.currentHp}/{cellData.hp.maxHp}
          </p>
        </div>
      </td>
    );
  }
  return <td className={styles.td}></td>;
}

function renderBoard(battleTableData: TableDataType) {
  let rows = [];

  for (let y = 0; y < battleTableData.height; y++) {
    let cells = [];
    for (let x = 0; x < battleTableData.width; x++) {
      cells.push(<Cell key={x} cellData={battleTableData.board[y][x]} />);
    }
    rows.push(<tr key={y}>{cells}</tr>);
  }

  return <table className={styles.table}>{rows}</table>;
}

function BattleTable() {
  const [battleTableData, setBattleTableData] = useState<TableDataType>(initialBattleTableData);
  const longPolling = async () => {
      try {
        const response = await fetch("http://localhost:50000/getjson/");
        const jsonData = await response.json();
        setBattleTableData(jsonData);
      } catch (error) {
        console.error('ロングポーリングエラー:', error);
      } finally {
        setTimeout(longPolling, 2000);
      }
    };

    longPolling();

  return (
    <div>
      {renderBoard(battleTableData)}
    </div>
  );
}

export default BattleTable;
