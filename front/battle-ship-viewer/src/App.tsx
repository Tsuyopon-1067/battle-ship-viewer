import './App.css';
import BattleTable from './BattleTable';
import { TableDataType, initialBattleTableData } from './type';
import React, { useEffect, useRef, useState } from 'react';
import HistorySlider from './HistorySlider';
import FormControlLabel from '@mui/material/FormControlLabel';
import Switch from '@mui/material/Switch';

function App() {
  //const battleTableDataHistory:TableDataType[] = ([initialBattleTableData]);
  const [battleTableDataHistory, setbattleTableDataHistory] = useState<TableDataType[]>([initialBattleTableData]);
  const selectedHisotyrIndexRef = useRef<number>(0);
  const [selectedHisotyrIndex, setSelectedHisotyrIndex] = useState<number>(0);
  const isEnableAutoUpdate = useRef<boolean>(false);
  const maxValueRef = useRef<number>(0);
  const [maxValue, setMaxValue] = useState<number>(0);
  const [sliderValue, setSliderValue] = useState<number>(0);
  const updateSliderValue = (value: number) => {
    selectedHisotyrIndexRef.current = value;
    setSelectedHisotyrIndex(value);
    setSliderValue(value);
  }
  const changeSwitch = (event: React.ChangeEvent) => {
    const checked = (event.target as HTMLInputElement).checked;
    isEnableAutoUpdate.current = checked;
  }

  useEffect(() => {
    const longPolling = () => {
      fetch("http://localhost:50000/getjson/")
        .then((response) => response.json())
        .then((data) => {
          battleTableDataHistory.push(data);
          maxValueRef.current = battleTableDataHistory.length - 1;
          setMaxValue(maxValueRef.current);
          if (isEnableAutoUpdate.current) {
            selectedHisotyrIndexRef.current = maxValueRef.current;
            setSelectedHisotyrIndex(maxValueRef.current);
            updateSliderValue(maxValueRef.current);
          }
        })
      .catch((error) => console.error("Error:", error))
      .finally(() => {
        longPolling();
      });
    };
    longPolling();
  }, []);

  return (
    <div className="App">
      {selectedHisotyrIndex}/{maxValue}
      <BattleTable data={battleTableDataHistory[selectedHisotyrIndexRef.current]} />
      <HistorySlider maxValue={maxValueRef.current} value={sliderValue} updateValue={updateSliderValue} />
      <FormControlLabel control={<Switch onChange={changeSwitch} />} label="Auto Update" />
    </div>
  );
}

export default App;
