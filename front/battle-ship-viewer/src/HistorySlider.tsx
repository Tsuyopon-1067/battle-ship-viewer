import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Slider from '@mui/material/Slider';
import MuiInput from '@mui/material/Input';

const Input = styled(MuiInput)`
  width: 40pt;
`;

interface HisotrySliderProps {
  maxValue: number;
  value: number;
  updateValue: (value: number) => void;
}
export default function HistorySlider({maxValue, value, updateValue}: HisotrySliderProps) {

  const handleSliderChange = (event: Event, newValue: number | number[]) => {
    updateValue(newValue as number);
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = event.target.value === '' ? 0 : Number(event.target.value);
    if (0 <= newValue && newValue <= maxValue) {
      updateValue(newValue);
    }
  };

  const handleBlur = () => {
    if (value < 0) {
      updateValue(0);
    } else if (value > maxValue) {
      updateValue(maxValue);
    }
  };

  React.useEffect(() => {
    updateValue(value);
  }, [value]);

  return (
    <Box sx={{ width: 350 }}>
      <Grid container spacing={2} alignItems="center">
        <Grid item xs>
          <Slider
            value={typeof value === 'number' ? value : 0}
            onChange={handleSliderChange}
            aria-labelledby="input-slider"
            max={maxValue}
          />
        </Grid>
        <Grid item>
          <Input
            value={value}
            size="small"
            onChange={handleInputChange}
            onBlur={handleBlur}
            inputProps={{
              step: 1,
              min: 0,
              max: {maxValue},
              type: 'number',
              'aria-labelledby': 'input-slider',
            }}
          />
          <span style={{marginLeft: "5pt", fontSize: "13pt"}}>/ {maxValue}</span>
        </Grid>
      </Grid>
    </Box>
  );
}