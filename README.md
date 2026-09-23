# Laboratorio Signup / Login Forms

Computación Móvil, Universidad Piloto de Colombia. Prof. Gilberto Pedraza García.

## Objetivo

Diseñar formularios para realizar el registro (signup) y la autenticación (login) de usuarios en una aplicación móvil.

## Desarrollo

### Signup_Form (`activity_signup__form.xml`)

- `TextInputLayout` (com.google.android.material.textfield.TextInputLayout) para nombre completo, usuario, eMail, dirección física, password y confirmación del password.
- `Spinner` con los roles profesor, estudiante y coordinador.
- Dirección física por geolocalización: el botón **Obtener ubicación** toma la latitud y la longitud del GPS y, con `Geocoder`, las convierte en una dirección estructurada.
- Fecha de nacimiento con `DatePickerDialog`. Solo se aceptan como usuarios activos las personas mayores de 18 años.
- Género (masculino, femenino o binario) con `RadioGroup` y `RadioButton`.
- Botón **Register** para registrar los datos.

### Login_Form (`activity_login__form.xml`)

- Campos de usuario y password.
- Botón **Login** y botón **Register** (abre el formulario de registro).
- Es la actividad principal que se muestra al lanzar la aplicación (`LAUNCHER` en `AndroidManifest.xml`).

## Ejecución

Abrir el proyecto en Android Studio y ejecutarlo en un emulador con Google APIs. Para probar la geolocalización, fijar una ubicación en *Extended controls → Location*.
