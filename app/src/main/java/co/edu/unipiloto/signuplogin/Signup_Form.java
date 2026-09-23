package co.edu.unipiloto.signuplogin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Signup_Form extends AppCompatActivity {

    private static final int REQUEST_UBICACION = 1;

    private EditText nombre, usuario, email, direccion, password, confirmPassword, fechaNacimiento;
    private RadioGroup genero;

    private int anio = -1, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);
        getSupportActionBar().setTitle("Signup Form");

        nombre = findViewById(R.id.nombre);
        usuario = findViewById(R.id.usuario);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        fechaNacimiento = findViewById(R.id.fecha_nacimiento);
        genero = findViewById(R.id.genero);
    }

    // ---------------- Fecha de nacimiento ----------------

    public void btn_fechaNacimiento(View view) {
        Calendar hoy = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (picker, y, m, d) -> {
            anio = y;
            mes = m;
            dia = d;
            fechaNacimiento.setText(d + "/" + (m + 1) + "/" + y);
        }, hoy.get(Calendar.YEAR) - 18, hoy.get(Calendar.MONTH), hoy.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(hoy.getTimeInMillis());
        dialog.show();
    }

    private int calcularEdad() {
        Calendar hoy = Calendar.getInstance();
        int edad = hoy.get(Calendar.YEAR) - anio;
        if (hoy.get(Calendar.MONTH) < mes
                || (hoy.get(Calendar.MONTH) == mes && hoy.get(Calendar.DAY_OF_MONTH) < dia)) {
            edad--;
        }
        return edad;
    }

    // ---------------- Geolocalización ----------------

    public void btn_ubicacion(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_UBICACION);
            return;
        }
        obtenerUbicacion();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_UBICACION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacion();
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void obtenerUbicacion() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Active la ubicación (GPS)", Toast.LENGTH_SHORT).show();
            return;
        }
        Location ultima = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ultima != null) {
            mostrarDireccion(ultima);
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
            @Override public void onLocationChanged(@NonNull Location location) { mostrarDireccion(location); }
            @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override public void onProviderEnabled(@NonNull String provider) { }
            @Override public void onProviderDisabled(@NonNull String provider) { }
        }, Looper.getMainLooper());
    }

    /** Latitud, longitud y dirección estructurada (Geocoder de Google). */
    private void mostrarDireccion(Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();
        String coordenadas = String.format(Locale.US, "(%.6f, %.6f)", latitud, longitud);
        direccion.setText(coordenadas);

        new Thread(() -> {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> lista = geocoder.getFromLocation(latitud, longitud, 1);
                if (lista != null && !lista.isEmpty()) {
                    String texto = lista.get(0).getAddressLine(0) + " " + coordenadas;
                    runOnUiThread(() -> direccion.setText(texto));
                }
            } catch (Exception e) {
                // Sin servicio de geocodificación: quedan solo latitud y longitud
            }
        }).start();
    }

    // ---------------- Registro ----------------

    public void btn_registrar(View view) {
        if (nombre.getText().toString().trim().isEmpty()
                || usuario.getText().toString().trim().isEmpty()
                || email.getText().toString().trim().isEmpty()
                || direccion.getText().toString().trim().isEmpty()
                || password.getText().toString().isEmpty()
                || confirmPassword.getText().toString().isEmpty()
                || anio == -1
                || genero.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Los password no coinciden");
            return;
        }
        if (calcularEdad() < 18) {
            fechaNacimiento.setError("Solo mayores de 18 años pueden ser usuarios activos");
            Toast.makeText(this, "Solo mayores de 18 años pueden ser usuarios activos",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
        finish();
    }
}
