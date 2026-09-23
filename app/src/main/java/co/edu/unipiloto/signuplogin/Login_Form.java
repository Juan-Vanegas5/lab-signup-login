package co.edu.unipiloto.signuplogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);
        getSupportActionBar().setTitle("Login Form");
    }

    public void btn_login(View view) {
        EditText usuario = findViewById(R.id.usuario);
        EditText password = findViewById(R.id.password);

        if (usuario.getText().toString().trim().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese usuario y password", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), Signup_Form.class));
    }
}
