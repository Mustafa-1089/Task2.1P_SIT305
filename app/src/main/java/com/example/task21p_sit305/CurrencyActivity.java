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

import java.util.HashMap;
import java.util.Map;

public class CurrencyActivity extends AppCompatActivity {

    public void backClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    Button convertButton;
    TextView outcomeTextView;
    EditText inputUnit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_currency);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        setupSpinner(sourceUnitSpinner, R.array.sourceUnitOptions);

        Spinner destinationUnitSpinner = findViewById(R.id.destinationUnitSpinner);
        setupSpinner(destinationUnitSpinner, R.array.destinationUnitOptions);

        convertButton = findViewById(R.id.convertButton);
        outcomeTextView = findViewById(R.id.outcomeTextView);
        inputUnit = findViewById(R.id.inputUnit);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputUnit.getText().toString();
            if (inputStr.isEmpty()) {
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

            String source = sourceUnitSpinner.getSelectedItem().toString();
            String destination = destinationUnitSpinner.getSelectedItem().toString();

            double usdValue = amount * usdRates.get(source);
            double result = usdValue / usdRates.get(destination);

            @SuppressLint("DefaultLocale") String resultText = String.format("%.2f %s", result, destination);
            outcomeTextView.setText(resultText);
        });

        usdRates.put("USD", 1.0);
        usdRates.put("AUD", 1.0 / 1.55);
        usdRates.put("EUR", 1.0 / 0.92);
        usdRates.put("JPY", 1.0 / 148.50);
        usdRates.put("GBP", 1.0 / 0.78);
    }

    private final Map<String, Double> usdRates = new HashMap<>();

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