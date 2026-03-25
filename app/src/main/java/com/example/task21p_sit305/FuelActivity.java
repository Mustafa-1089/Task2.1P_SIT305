package com.example.task21p_sit305;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class FuelActivity extends AppCompatActivity {

    public void backClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    Button convertFuelButton;
    TextView resultText;
    EditText inputText;
    Spinner categorySpinner;
    Spinner sourceUnitFuelSpinner;
    Spinner destinationFuelUnitSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fuel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        convertFuelButton = findViewById(R.id.convertFuelButton);
        resultText = findViewById(R.id.resultText);
        inputText = findViewById(R.id.inputText);
        categorySpinner = findViewById(R.id.categorySpinner);
        sourceUnitFuelSpinner = findViewById(R.id.sourceUnitFuelSpinner);
        destinationFuelUnitSpinner = findViewById(R.id.destinationFuelUnitSpinner);

        setupSpinner(categorySpinner, R.array.category_units);

        updateUnitSpinner("Fuel Efficiency");

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {{
                    String selected = parent.getItemAtPosition(position).toString();
                    updateUnitSpinner(selected);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        convertFuelButton.setOnClickListener(v -> {
            String inputStr = inputText.getText().toString();
            if (inputStr.isEmpty()){
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(inputStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
                return;
            }

            String source = sourceUnitFuelSpinner.getSelectedItem().toString();
            String destination = destinationFuelUnitSpinner.getSelectedItem().toString();

            double result = amount * (unitConversion.get(destination) / unitConversion.get(source));

            @SuppressLint("DefaultLocale") String outcomeText = String.format("%.2f %s", result, destination);
            resultText.setText(outcomeText);
        });


        unitConversion.put("mpg", 1.0);
        unitConversion.put("Km/L", 0.425);
        unitConversion.put("Gallon", 1.0);
        unitConversion.put("Liters", 3.785);
        unitConversion.put("Nautical Mile", 1.0);
        unitConversion.put("Kilometers", 1.852);

    }

    private final Map<String, Double> unitConversion = new HashMap<>();

    public void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void updateUnitSpinner(String category) {
        int arrayId;
        if (category.equals("Fuel Efficiency")){
            arrayId = R.array.fuel_unit_options;
        } else if (category.equals("Volume")){
            arrayId = R.array.volume_unit_options;
        } else {
            arrayId = R.array.distance_unit_options;
        }

        setupSpinner(sourceUnitFuelSpinner, arrayId);
        setupSpinner(destinationFuelUnitSpinner, arrayId);
    }
}