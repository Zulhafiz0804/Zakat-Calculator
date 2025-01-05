package com.example.zakatcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etGoldValue;
    RadioGroup radioGroupGoldType;
    Button btnCalculate;
    ImageButton btnShare;
    TextView tvTotalGoldValue, tvPayableGoldValue, tvTotalZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        etWeight = findViewById(R.id.editTextWeight);
        etGoldValue = findViewById(R.id.editTextGoldValue);
        radioGroupGoldType = findViewById(R.id.radioGroupGoldType); // Updated for RadioGroup
        btnCalculate = findViewById(R.id.btnCalculate);
        btnShare = findViewById(R.id.btnShare); // Share button
        tvTotalGoldValue = findViewById(R.id.tvTotalGoldValue);
        tvPayableGoldValue = findViewById(R.id.tvPayableGoldValue);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);


        btnCalculate.setOnClickListener(v -> calculateZakat());
        btnShare.setOnClickListener(v -> shareResults());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();
        if (selected == R.id.menuAbout) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (selected == R.id.menuInstruction) {
            startActivity(new Intent(this, InstructionActivity.class));
            return true;
        } else if (selected == R.id.menuHome) { // Handle return to MainActivity
            Toast.makeText(this, "You are already in Home", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateZakat() {
        try {
            if (etWeight.getText().toString().isEmpty() || etGoldValue.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            double weight = Double.parseDouble(etWeight.getText().toString());
            double goldValue = Double.parseDouble(etGoldValue.getText().toString());

            // Get the selected gold type from RadioGroup
            int selectedGoldTypeId = radioGroupGoldType.getCheckedRadioButtonId();
            RadioButton selectedGoldTypeButton = findViewById(selectedGoldTypeId);
            String type = selectedGoldTypeButton.getText().toString();

            if (type.isEmpty()) {
                Toast.makeText(this, "Please select a gold type", Toast.LENGTH_SHORT).show();
                return;
            }

            int uruf = type.equalsIgnoreCase("Keep") ? 85 : 200;

            double totalGoldValue = weight * goldValue;
            double payableValue = Math.max(0, (weight - uruf) * goldValue);
            double totalZakat = payableValue * 0.025;

            tvTotalGoldValue.setText(getString(R.string.total_gold_value, totalGoldValue));
            tvPayableGoldValue.setText(getString(R.string.zakat_payable_value, payableValue));
            tvTotalZakat.setText(getString(R.string.total_zakat, totalZakat));

        } catch (NumberFormatException e) {
            tvTotalGoldValue.setText("Please enter valid numbers.");
            tvPayableGoldValue.setText("");
            tvTotalZakat.setText("");
        }
    }
    private void shareResults() {
        String totalGoldValue = tvTotalGoldValue.getText().toString();
        String payableGoldValue = tvPayableGoldValue.getText().toString();
        String totalZakat = tvTotalZakat.getText().toString();

        if (totalGoldValue.isEmpty() || payableGoldValue.isEmpty() || totalZakat.isEmpty()) {
            Toast.makeText(this, "Calculate Zakat before sharing!", Toast.LENGTH_SHORT).show();
            return;
        }

        String shareText = "Zakat Calculation Results:\n" +
                totalGoldValue + "\n" +
                payableGoldValue + "\n" +
                totalZakat;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zakat Calculation Results");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}

