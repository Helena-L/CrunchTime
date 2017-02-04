package com.example.shan.crunchtime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;


public class CrunchTimeActivity extends Activity {

    Spinner exerciseSpinner;
    Spinner unitSpinner;
    Spinner compareSpinner;
    HashMap<String, Double> exerciseHashMap;
    private boolean convertClicked;
    private boolean calculateClicked;
    private String[] exerciseList;
    private String[] unitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        convertClicked = false;
        calculateClicked = false;
        setContentView(R.layout.activity_crunch_time);
        setExerciseHashMap();
        getExerciseSpinners();
        getUnitSpinners();
        getCompareSpinners();
    }

    private void setExerciseHashMap() {
        exerciseHashMap = new HashMap<String, Double>();
        //Amount of calorie burned for 1 unit exercise
        exerciseHashMap.put("Pushups", 100.0/350);
        exerciseHashMap.put("Situps", 100.0/200);
        exerciseHashMap.put("Squats", 100.0/225);
        exerciseHashMap.put("Leg-lifts", 100.0/25);
        exerciseHashMap.put("Plank", 100.0/25);
        exerciseHashMap.put("Jumping Jacks", 100.0/10);
        exerciseHashMap.put("Pullups", 100.0/100);
        exerciseHashMap.put("Cycling", 100.0/12);
        exerciseHashMap.put("Walking", 100.0/20);
        exerciseHashMap.put("Jogging", 100.0/12);
        exerciseHashMap.put("Swimming", 100.0/13);
        exerciseHashMap.put("Stair-Climbing", 100.0/15);
    }

    private void getExerciseSpinners() {
        exerciseList = getResources().getStringArray(R.array.exercise_list);
        exerciseSpinner = (Spinner)findViewById(R.id.exercise_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.exercise_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //do something, if convertClicked = true
                ((TextView) parent.getChildAt(0)).setTextSize(28);
                if(convertClicked) {
                    convertCalories(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getUnitSpinners() {
        unitList = getResources().getStringArray(R.array.unit_list);
        unitSpinner = (Spinner)findViewById(R.id.unit_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.unit_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(28);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getCompareSpinners() {
        compareSpinner = (Spinner)findViewById(R.id.compare_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.exercise_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compareSpinner.setAdapter(adapter);

        compareSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //do something, if calculateClicked = true
                ((TextView) parent.getChildAt(0)).setTextSize(28);
                if(calculateClicked) {
                    calculateExercise(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public double convertCalories(View v) {
        //convert to calories
        convertClicked = true;
        TextView convertResult = (TextView)findViewById(R.id.calorie_result);
        EditText exerciseInput = (EditText)findViewById(R.id.input_textbox);
        Spinner exerciseSpinner = (Spinner)findViewById(R.id.exercise_spinner);
        String exerciseType = exerciseSpinner.getSelectedItem().toString();
        Spinner unitSpinner = (Spinner)findViewById(R.id.unit_spinner);
        String unitType = unitSpinner.getSelectedItem().toString();

        try {
            if(exerciseType.equals("Pullups") || exerciseType.equals("Pushups") || exerciseType.equals("Squats") || exerciseType.equals("Situps")){
                if(unitType.equals("Minutes")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select \"Reps\"", Toast.LENGTH_LONG);
                    toast.show();
                    convertClicked = false;
                    return 0;
                }
                else {
                    double exerciseAmount = Double.parseDouble(exerciseInput.getText().toString());
                    double caloriePerUnit = exerciseHashMap.get(exerciseType);
                    double amountCalorieBurned = exerciseAmount * caloriePerUnit;

                    String result;
                    DecimalFormat f = new DecimalFormat("#.0");
                    result = f.format(amountCalorieBurned);

                    convertResult.setText(result + " Calories BURNED!", TextView.BufferType.EDITABLE);
                    convertClicked = false;
                    return amountCalorieBurned;
                }

            }
            else{
                if(unitType.equals("Reps")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select \"Minutes\"", Toast.LENGTH_LONG);
                    toast.show();
                    convertClicked =false;
                    return 0;
                }
                else {
                    double exerciseAmount = Double.parseDouble(exerciseInput.getText().toString());
                    double caloriePerUnit = exerciseHashMap.get(exerciseType);
                    double amountCalorieBurned = exerciseAmount * caloriePerUnit;

                    String result;
                    DecimalFormat f = new DecimalFormat("#.0");
                    result = f.format(amountCalorieBurned);

                    convertResult.setText(result + " Calories BURNED!", TextView.BufferType.EDITABLE);
                    convertClicked = false;
                    return amountCalorieBurned;
                }
            }

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG);
            toast.show();
            return 0;
        }

    }

    public void calculateExercise(View v) {
        //compare to another exercise
        calculateClicked = true;

        double amountCalorieBurned = convertCalories(v);

        compareSpinner = (Spinner)findViewById(R.id.compare_spinner);
        String compareType = compareSpinner.getSelectedItem().toString();
        TextView calculateText = (TextView)findViewById(R.id.calculate_text);

        double caloriePerUnit = exerciseHashMap.get(compareType);
        double amountComapreExercise = amountCalorieBurned/caloriePerUnit;

        String result;
        DecimalFormat f = new DecimalFormat("#.0");
        result = f.format(amountComapreExercise);

        String unit;
        if(compareType.equals("Pullups") || compareType.equals("Pushups") || compareType.equals("Squats") || compareType.equals("Situps")){
            unit = " reps of ";
        }
        else{
            unit = " minutes of ";
        }

        calculateText.setText("WOW!! This is equivalent to " + result + unit + compareType + "!");
        calculateClicked = false;

    }

}
