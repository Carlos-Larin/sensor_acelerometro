package com.ugb.controlesbasicos;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SensorManager SensorManager;
    Sensor Sensor;
    SensorEventListener SensorEventListener;
    TextView TempVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TempVal = findViewById(R.id.lblsensoracelerometro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        activarSensorAcelerometro();
    }

    protected void onResume() {
        iniciar();
        super.onResume();
    }

    protected void onPause() {
        detener();
        super.onPause();
    }

    private void detener() {
        SensorManager.unregisterListener(SensorEventListener);
    }

    private void iniciar() {
        SensorManager.registerListener(SensorEventListener, Sensor, 2000 * 1000);
    }

    @SuppressLint("SetTextI18n")
    private void activarSensorAcelerometro() {
        SensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor = SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (Sensor == null) {
            TempVal.setText("Tu dispositivo NO cuenta con el sensor acelerometro.");
            finish();

            SensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    TempVal.setText("Acelerometro: X=" + sensorEvent.values[0] + "; Y=" + sensorEvent.values[1] + "; Z=" +
                            sensorEvent.values[2]);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };
        }
    }
}