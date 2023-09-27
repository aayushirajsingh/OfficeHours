package com.example.officehours;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.officehours.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.button.setOnClickListener(v -> calculateWorkHours());
    }

    private void calculateWorkHours() {
        String signInStr = binding.signIn.getText().toString();
        String signOutStr = binding.signOut.getText().toString();
        String breakTimeStr = binding.breakTime.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa", Locale.US);
        try {
            Date signIn = format.parse(signInStr + " AM");
            Date signOut = format.parse(signOutStr + " PM");

            long breakTime = Long.parseLong(breakTimeStr);

            long workTimeMillis = signOut.getTime() - signIn.getTime() - (breakTime * 60 * 1000);

            if (workTimeMillis > 0) {
                long hours = workTimeMillis / (60 * 60 * 1000);
                long minutes = (workTimeMillis % (60 * 60 * 1000)) / (60 * 1000);

                String result = String.format(Locale.US, "Work Hours: %d hours %d minutes", hours, minutes);

                binding.totalHours.setText(result);
            } else {
                Toast.makeText(MainActivity.this, "Invalid input. Make sure sign out time is after sign in time.", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error parsing input. Please enter valid times and break duration.", Toast.LENGTH_LONG).show();
        }
    }
}