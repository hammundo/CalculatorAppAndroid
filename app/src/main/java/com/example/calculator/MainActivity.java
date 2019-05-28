package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Constants
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND_1 = "Operand1";

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    //Operand variables
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init text fields
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        //Init buttons
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDecimal = (Button) findViewById(R.id.buttonDecimal);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonAddition = (Button) findViewById(R.id.buttonAddition);
        Button buttonSubtraction = (Button) findViewById(R.id.buttonSubtraction);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        Button buttonNegative = (Button) findViewById(R.id.buttonNegative);

        //Numerical buttons listener
        View.OnClickListener numericalButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(((Button) v).getText().toString());
            }
        };
        button0.setOnClickListener(numericalButtonListener);
        button1.setOnClickListener(numericalButtonListener);
        button2.setOnClickListener(numericalButtonListener);
        button3.setOnClickListener(numericalButtonListener);
        button4.setOnClickListener(numericalButtonListener);
        button5.setOnClickListener(numericalButtonListener);
        button6.setOnClickListener(numericalButtonListener);
        button7.setOnClickListener(numericalButtonListener);
        button8.setOnClickListener(numericalButtonListener);
        button9.setOnClickListener(numericalButtonListener);
        buttonDecimal.setOnClickListener(numericalButtonListener);

        //Operation buttons listener
        View.OnClickListener operationButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String operation = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, operation);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = operation;
                displayOperation.setText(pendingOperation);
            }
        };
        buttonEquals.setOnClickListener(operationButtonListener);
        buttonAddition.setOnClickListener(operationButtonListener);
        buttonMultiply.setOnClickListener(operationButtonListener);
        buttonSubtraction.setOnClickListener(operationButtonListener);
        buttonDivide.setOnClickListener(operationButtonListener);

        //Clear all listener
        View.OnClickListener clearButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                result.setText("");
                newNumber.setText("");
                displayOperation.setText("");
                operand1 = null;
                operand2 = null;
            }
        };
        buttonClear.setOnClickListener(clearButtonListener);

        //Negative listener
        View.OnClickListener negativeButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if(value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        //newNumber was "-" or "." therefore clear.
                        newNumber.setText("");
                    }
                }
            }
        };
        buttonNegative.setOnClickListener(negativeButtonListener);
    }

    private void performOperation(Double value, String operation) {
        if(operand1 == null) {
            operand1 = value;
        } else {
            operand2 = value;
            if(pendingOperation.equals("+")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if(operand2 == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= operand2;
                    }
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");

    }

    //Save data to bundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if(operand1 != null) {
            outState.putDouble(STATE_OPERAND_1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    //Retrieve data from bundle and re-draw to the screen.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND_1);
        displayOperation.setText(pendingOperation);
    }
}
