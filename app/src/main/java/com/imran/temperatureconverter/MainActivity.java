package com.imran.temperatureconverter;

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

public class MainActivity extends AppCompatActivity {

    // Declare variables
    Spinner spinnerUnits;
    EditText edTempValue;
    TextView result;
    Button convertButton;
    boolean isCelsius, isFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get references to the views
        spinnerUnits = findViewById(R.id.spinnerUnits);
        edTempValue = findViewById(R.id.edTempValue);
        result = findViewById(R.id.result);
        convertButton = findViewById(R.id.convertButton);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.temperature_units, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerUnits.setAdapter(adapter);


        // Set up an onItemSelectedListener
        spinnerUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //get selected Item
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                //Test if it works by a Toast
                if (selectedItem.equals("Celsius")){
                    isCelsius = true;
                    isFahrenheit = false;
                }
                else if (selectedItem.equals("Fahrenheit")){
                    isFahrenheit = true;
                    isCelsius = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("Nothing selected");
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get temperature value
                String sTempValue = edTempValue.getText().toString();
                //convert to float
                float tempValue = 0;
                //check selected unit
                if (isCelsius && !isFahrenheit && !sTempValue.isEmpty()){
                    //convert to fahrenheit
                    tempValue = Float.parseFloat(sTempValue);
                    float celsiusToFahrenheit = (tempValue * 9/5) + 32;
                    result.setText(String.valueOf(celsiusToFahrenheit +" °F"));

                } else if (isFahrenheit && !isCelsius && !sTempValue.isEmpty()){
                    //convert to celsius
                    tempValue = Float.parseFloat(sTempValue);
                    float fahrenheitToCelsius = (tempValue - 32) * 5/9;
                    result.setText(String.valueOf(fahrenheitToCelsius +" °C"));
                } else {
                    //show error message in input field
                    edTempValue.setError("Please enter a value");
                }
            }
        });

    }
}