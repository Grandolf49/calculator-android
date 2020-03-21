package com.chinmay.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ast.Scope;

public class MainActivity extends AppCompatActivity {

    private TextView tv_input, tv_output;
    private String expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_input = findViewById(R.id.tv_input);
        tv_output = findViewById(R.id.tv_output);

    }

    public void onButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_0:
                appendCharacter("0");
                break;
            case R.id.btn_1:
                appendCharacter("1");
                break;
            case R.id.btn_2:
                appendCharacter("2");
                break;
            case R.id.btn_3:
                appendCharacter("3");
                break;
            case R.id.btn_4:
                appendCharacter("4");
                break;
            case R.id.btn_5:
                appendCharacter("5");
                break;
            case R.id.btn_6:
                appendCharacter("6");
                break;
            case R.id.btn_7:
                appendCharacter("7");
                break;
            case R.id.btn_8:
                appendCharacter("8");
                break;
            case R.id.btn_9:
                appendCharacter("9");
                break;
            case R.id.btn_add:
                appendCharacter("+");
                break;
            case R.id.btn_subtract:
                appendCharacter("-");
                break;
            case R.id.btn_multiply:
                appendCharacter("×");
                break;
            case R.id.btn_divide:
                appendCharacter("÷");
                break;
            case R.id.btn_percent:
                appendCharacter("%");
                break;
            case R.id.btn_clear:
                clearTextFields();
                break;
            case R.id.btn_back:
                removeLastCharacter();
                break;
            case R.id.btn_dot:
                appendCharacter(".");
                break;
            case R.id.btn_equals:
                evaluateExpression(expression);
                break;
        }
    }

    private void removeLastCharacter() {
        expression = tv_input.getText().toString();
        if (!expression.equals("")) {
            expression = expression.substring(0, expression.length() - 1);
        }
        tv_input.setText(expression);
    }

    private void evaluateExpression(String expression) {
        expression = expression.replaceAll("×", "*");
        expression = expression.replaceAll("%", "/100");
        expression = expression.replaceAll("÷", "/");
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);

        String result = "";
        try {
            Scriptable scriptable = rhino.initStandardObjects();
            result = rhino.evaluateString(scriptable, expression, "javascript", 1, null).toString();
        } catch (Exception e) {
            result = "";
        }
        if (!result.equals("")) {
            result = String.format("%.3f", Double.valueOf(result));
        }
        tv_output.setText(result);
    }

    private void clearTextFields() {
        tv_input.setText("");
        tv_output.setText("");
    }

    private void appendCharacter(String s) {
        expression = tv_input.getText().toString();
        expression += s;
        tv_input.setText(expression);
    }
}
