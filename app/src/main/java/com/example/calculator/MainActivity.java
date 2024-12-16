package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView input;
    private TextView output;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        input = findViewById(R.id.input);
        input.setText("");
        output = findViewById(R.id.output);
        output.setText("");
    }

    public void assignOutput(View view){
        Expression result = Expression.EvaluateExpression(input.getText().toString(), 0);
        if (result.remainder_start < 0) {
            return;
        }
        if (result.remainder_start != input.getText().toString().length()) {
            return;
        }
        if(result.value == (long)result.value){
            input.setText(String.format(Locale.ROOT, "%d", (long)result.value));
            return;
        }
        input.setText(String.format(Locale.ROOT, "%s", result.value));
    }
    public void deleteInputChar(View view) {
        String currentText = input.getText().toString();

        if (!currentText.isEmpty()) {
            String updatedText = currentText.substring(0, currentText.length() - 1);
            input.setText(updatedText);
            Expression result = Expression.EvaluateExpression(input.getText().toString(), 0);
            outputExpression(result);
        }
    }

    public void clearInput(View view) {
        input.setText("");
        Expression result = Expression.EvaluateExpression(input.getText().toString(), 0 );
        outputExpression(result);
    }

    public void buttonClicked(View view) {
        try {
            if (!(view instanceof Button)) {
                output.setText("Error: non-button triggered buttonClicked event");
                return;
            }
            Button btn = (Button) view;
            input.append(btn.getText());
            Expression result = Expression.EvaluateExpression(input.getText().toString(),0);
            outputExpression(result);
        } catch (Exception e) {
            output.setText(e.getMessage());
        }
    }
    private void outputExpression(Expression result){
        if (result.remainder_start < 0) {
            output.setText(String.format(Locale.ROOT, "%d", result.remainder_start));
        }
        if (result.remainder_start != input.getText().toString().length()) {
            output.setText("");
        } else {
            output.setText(String.format(Locale.ROOT, "%.2f", result.value));
        }
    }
}
