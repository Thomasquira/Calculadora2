package com.ejemplo.calculadora;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView txtResultado;

    String numeroActual = "";
    String operador = "";
    double numero1 = 0.0;
    double memoria = 0.0;
    boolean operadorSeleccionado = false;


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

        txtResultado = findViewById(R.id.txtResultado);

        int[] numeros = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberListener = v -> {
            String n = ((Button) v).getText().toString();
            numeroActual += n;
            txtResultado.setText(numeroActual);
        };

        for (int id : numeros) {
            findViewById(id).setOnClickListener(numberListener);
        }

        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if (!numeroActual.contains(".")) {
                if (numeroActual.isEmpty()) numeroActual = "0";
                numeroActual += ".";
                txtResultado.setText(numeroActual);
            }
        });

        findViewById(R.id.btnPlus).setOnClickListener(v -> seleccionarOperacion("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> seleccionarOperacion("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> seleccionarOperacion("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> seleccionarOperacion("/"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> calcular());

        findViewById(R.id.btnCE).setOnClickListener(v -> {
            numeroActual = "";
            txtResultado.setText("0");
        });

        findViewById(R.id.btnC).setOnClickListener(v -> {
            numeroActual = "";
            operador = "";
            numero1 = 0;
            operadorSeleccionado = false;
            txtResultado.setText("0");
        });


        findViewById(R.id.btnPercent).setOnClickListener(v -> porcentaje());



        findViewById(R.id.btnMS).setOnClickListener(v -> memoriaStore());
        findViewById(R.id.btnMR).setOnClickListener(v -> memoriaRecall());
        findViewById(R.id.btnMC).setOnClickListener(v -> memoriaClear());
        findViewById(R.id.btnMPlus).setOnClickListener(v -> memoriaAdd());
        findViewById(R.id.btnMMinus).setOnClickListener(v -> memoriaSubstract());


    }

    public void seleccionarOperacion(String op) {
        if (numeroActual.isEmpty()) {
            operador = op;
            return;
        }

        if (!operador.isEmpty()) {
            calcular();
        }

        numero1 = Double.parseDouble(numeroActual);
        operador = op;
        numeroActual = "";
    }


    public void calcular() {
        if (operador.isEmpty() || numeroActual.isEmpty()) return;
        double numero2 = Double.parseDouble(numeroActual);
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = numero1 + numero2;
                break;

            case "-":
                resultado = numero1 - numero2;
                break;

            case "*":
                resultado = numero1 * numero2;
                break;

            case "/":
                if (numero2 == 0) {
                    txtResultado.setText("Error");
                    return;
                }
                resultado = numero1 / numero2;
                break;

            case "%":
                resultado = numero1 % numero2;
                break;
        }

        mostrarResultado(resultado);
        numeroActual = String.valueOf(resultado);
        operador = "";
    }

    public void porcentaje() {
        if (numeroActual.isEmpty() || operador.isEmpty()) return;

        double n2 = Double.parseDouble(numeroActual);
        double porcentaje = numero1 * (n2 / 100.0);

        numeroActual = String.valueOf(porcentaje);
        txtResultado.setText(numeroActual);
    }


    public void mostrarResultado(double resultado) {
        if (resultado == (long) resultado) {
            txtResultado.setText(String.valueOf((long) resultado));
            numeroActual = String.valueOf((long) resultado);
        } else {
            txtResultado.setText(String.valueOf(resultado));
            numeroActual = String.valueOf(resultado);
        }
    }


    public void memoriaStore() {
        memoria = Double.parseDouble(txtResultado.getText().toString());
    }

    public void memoriaRecall() {
        numeroActual = String.valueOf(memoria);
        txtResultado.setText(numeroActual);
    }

    public void memoriaClear() {
        memoria = 0;
    }

    public void memoriaAdd() {
        memoria += Double.parseDouble(txtResultado.getText().toString());
    }

    public void memoriaSubstract() {
        memoria -= Double.parseDouble(txtResultado.getText().toString());
    }
}