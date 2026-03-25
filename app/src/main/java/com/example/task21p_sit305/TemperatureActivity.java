package com.example.task21p_sit305;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class TemperatureActivity extends AppCompatActivity {

    public void backHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    Button convertTempButton;
    TextView resultOutput;
    Spinner sourceTempSpinner;
    Spinner destinationTempSpinner;
    EditText editTempText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        convertTempButton = findViewById(R.id.convertTempButton);
        resultOutput = findViewById(R.id.resultOutput);
        sourceTempSpinner = findViewById(R.id.sourceTempSpinner);
        destinationTempSpinner = findViewById(R.id.destinationTempSpinner);
        editTempText = findViewById(R.id.editTempText);

        setupSpinner(sourceTempSpinner, R.array.temperature_options);
        setupSpinner(destinationTempSpinner, R.array.temperature_options);

        convertTempButton.setOnClickListener(v -> {
            String inputStr = editTempText.getText().toString();
            if (inputStr.isEmpty()){
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(inputStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
                return;
            }

            String source = sourceTempSpinner.getSelectedItem().toString();
            String destination = destinationTempSpinner.getSelectedItem().toString();

            double result = getResult(source, destination, amount);

            @SuppressLint("DefaultLocale") String outcomeText = String.format("%.2f %s", result, destination);
            resultOutput.setText(outcomeText);
        });
    }

    private static double getResult(String source, String destination, double amount) {
        double result;

        if (source.equals(destination)) {
            result = amount;
        } else if (source.equals("Celsius") && destination.equals("Fahrenheit")) {
            result = (amount * 1.8) + 32;
        } else if (source.equals("Fahrenheit") && destination.equals("Celsius")) {
            result = (amount - 32) / 1.8;
        } else if (source.equals("Celsius") && destination.equals("Kelvin")) {
            result = amount + 273.15;
        } else if (source.equals("Kelvin") && destination.equals("Celsius")) {
            result = amount - 273.15;
        } else if (source.equals("Fahrenheit") && destination.equals("Kelvin")) {
            result = ((amount - 32) / 1.8) + 273.15;
        } else if (source.equals("Kelvin") && destination.equals("Fahrenheit")) {
            result = ((amount - 273.15) * 1.8) + 32;
        } else {
            result = amount;
        }
        return result;
    }

    public void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}