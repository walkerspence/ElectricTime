package com.example.walker.prog1;

import java.util.*;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.content.Context;
import android.os.Bundle;

public class HomeScreen extends AppCompatActivity implements View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {
    public double DISTANCE = 0;
    public String SELECTED_VEHICLE_FROM;
    public String SELECTED_VEHICLE_TO;
    public static final Map<String, Double[]> VEHICLES = createVehicles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        String[] vehicleNames = new String[]{"Walk", "Boosted Mini S", "Evolve Skateboard",
                "OneWheel", "Mototec Skateboard", "Segway Ninebot One S1", "Segway i2 SE",
                "Razor Scooter", "Geoblade 500", "Hovertrax Hoverboard"};
        Integer[] icons = {R.drawable.walk, R.drawable.boosted, R.drawable.evolve,
                R.drawable.onewheel, R.drawable.mototec, R.drawable.segways1, R.drawable.segwayse,
                R.drawable.razor, R.drawable.geoblade, R.drawable.hovertrax,};

        final TextView time1 = (TextView) findViewById(R.id.time1);
        time1.setText(Double.toString(0.0) + " minutes");
        final TextView time2 = (TextView) findViewById(R.id.time2);
        time2.setText(Double.toString(0.0) + " minutes");

        final Spinner spinnerFrom = (Spinner) findViewById(R.id.from);
        final Spinner spinnerTo = (Spinner) findViewById(R.id.to);

        SELECTED_VEHICLE_FROM = "Walk";
        SELECTED_VEHICLE_TO = "Walk";

        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.spinner_dropdown_layout, vehicleNames, icons);
        customAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerFrom.setAdapter(customAdapter);
        spinnerTo.setAdapter(customAdapter);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setOnFocusChangeListener(this);

        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

        spinnerFrom.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        spinnerTo.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!editText.getText().toString().equals("")) {
                        DISTANCE = Double.parseDouble(editText.getText().toString());
                        Map<String, Double> vehicleTimes = convertAll(DISTANCE);
                        double travelTimeFrom = vehicleTimes.get(SELECTED_VEHICLE_FROM);
                        double travelTimeTo = vehicleTimes.get(SELECTED_VEHICLE_TO);
                        if (travelTimeFrom == (double) Integer.MIN_VALUE) {
                            time1.setText("Out of range");
                        } else {
                            time1.setText(Double.toString(travelTimeFrom) + " minutes");
                        }

                        if (travelTimeTo == (double) Integer.MIN_VALUE) {
                            time2.setText("Out of range");
                        } else {
                            time2.setText(Double.toString(travelTimeTo) + " minutes");
                        }
                    } else {
                        time1.setText(Double.toString(0.0) + " minutes");
                        time2.setText(Double.toString(0.0) + " minutes");
                    }
                    hideKeyboard(v);
                }
            }
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String selectedVehicle = spinnerFrom.getSelectedItem().toString();
                Map<String, Double> vehicleTimes = convertAll(DISTANCE);
                double travelTime = vehicleTimes.get(selectedVehicle);
                if (travelTime == (double) Integer.MIN_VALUE) {
                    time1.setText("Out of range");
                } else {
                    time1.setText(Double.toString(travelTime) + " minutes");
                }
                SELECTED_VEHICLE_FROM = selectedVehicle;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //override
            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String selectedVehicle = spinnerTo.getSelectedItem().toString();
                Map<String, Double> vehicleTimes = convertAll(DISTANCE);
                double travelTime = vehicleTimes.get(selectedVehicle);
                if (travelTime == (double) Integer.MIN_VALUE) {
                    time2.setText("Out of range");
                } else {
                    time2.setText(Double.toString(travelTime) + " minutes");
                }
                SELECTED_VEHICLE_TO = selectedVehicle;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //override
            }
        });
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //override
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //override
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //override
    }

    private static Map<String, Double[]> createVehicles()
    {
        Map<String, Double[]> vehicles = new HashMap<>();
        vehicles.put("Walk", new Double[]{3.1, 30.0});
        vehicles.put("Boosted Mini S", new Double[]{18.0, 7.0});
        vehicles.put("Evolve Skateboard", new Double[]{26.0, 31.0});
        vehicles.put("OneWheel", new Double[]{19.0, 7.0});
        vehicles.put("Mototec Skateboard", new Double[]{22.0, 10.0});
        vehicles.put("Segway Ninebot One S1", new Double[]{12.5, 15.0});
        vehicles.put("Segway i2 SE", new Double[]{12.5, 24.0});
        vehicles.put("Razor Scooter", new Double[]{10.0, 7.0});
        vehicles.put("Geoblade 500", new Double[]{15.0, 8.0});
        vehicles.put("Hovertrax Hoverboard", new Double[]{8.0, 8.0});
        return vehicles;
    }
    public Map<String, Double> convertAll(double distance) {
        Map<String, Double> vehicleTimes = new HashMap<>();

        for(String vehicle : VEHICLES.keySet()) {
            double speed = VEHICLES.get(vehicle)[0];
            double range = VEHICLES.get(vehicle)[1];
            if(range >= distance) {
                vehicleTimes.put(vehicle, (double) Math.round((distance / speed * 60) * 100 ) / 100);
            } else {
                vehicleTimes.put(vehicle, (double) Integer.MIN_VALUE);
            }
        }
        return vehicleTimes;
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}