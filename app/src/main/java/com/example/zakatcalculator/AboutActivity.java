package com.example.zakatcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("ABOUT ME");

        TextView tvDetails = findViewById(R.id.tvDetails);
        TextView tvCopyright = findViewById(R.id.tvCopyright);
        TextView tvGithubLink = findViewById(R.id.tvGithubLink);

        tvDetails.setText("Name: Muhammad Zulhafiz Bin Norman \n\n Group: CDCS2405A\n\n Student Number: 2022831048\n\n Programme Code: CDCS240\n\n" );
        tvCopyright.setText("© 2024 Muhammad Zulhafiz. All rights reserved.");
        tvGithubLink.setText("Visit GitHub Repository");
        tvGithubLink.setOnClickListener(v -> {
            String url = "https://github.com/Zulhafiz0804/Zakat-Calculator";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
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
            Toast.makeText(this, "You are already in About", Toast.LENGTH_SHORT).show();
            return true;
        } else if (selected == R.id.menuInstruction) {
            startActivity(new Intent(this, InstructionActivity.class));
            return true;
        } else if (selected == R.id.menuHome) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

