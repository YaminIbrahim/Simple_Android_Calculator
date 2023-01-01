package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //declare
    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonAC, buttonOB, buttonCB, buttonDivide, buttonX,
                    buttonMinus, buttonPlus, buttonEqual, button1, button2, button3, button4,
                    button5, button6, button7, button8, button9, button0, buttonDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        //call all the method
        assignId(buttonC, R.id.button_c);
        assignId(buttonAC, R.id.button_AC);
        assignId(buttonOB, R.id.button_OB);
        assignId(buttonCB, R.id.button_CB);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonX, R.id.button_x);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonEqual, R.id.button_equal);
        assignId(buttonDot, R.id.button_dot);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);



    }//close onCreate()

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //have to implement because of OnClickListener()
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        //display the button value
        //solutionTv.setText(buttonText);

        //take the string insert in object
        String previousData = solutionTv.getText().toString();

        //logic for AC/reset button
        if (buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        //logic for = button
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }

        //logic for C/delete button
        if (buttonText.equals("C")){
            previousData = previousData.substring(0, previousData.length()-1);
        }else {
            //take the previous data to calculate
            previousData = previousData + buttonText;
        }

        //display at the textview
        solutionTv.setText(previousData);

        String finalResult = getResult(previousData);

        if (!finalResult.equals("ERROR")){
            resultTv.setText(finalResult);
        }//close if
    }//close onClick()

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            //get rid of .0
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        } catch (Exception e){
            return "ERROR";
        }//close catch
        //return "calculated!";
    }//close getResult

}//close MainActivity