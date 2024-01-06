import './App.css';
import BattleTable from './BattleTable';
import { TableDataType, initialBattleTableData } from './type';
import React, { useState } from 'react';

function App() {
  const [battleTableData, setBattleTableData] = useState<TableDataType>(initialBattleTableData);
  const [battleTableDataHistory, setbattleTableDataHistory] = useState<TableDataType[]>([initialBattleTableData]);
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


    <div className="App">
      <BattleTable data={battleTableData} />
    </div>
  );
}

export default App;
