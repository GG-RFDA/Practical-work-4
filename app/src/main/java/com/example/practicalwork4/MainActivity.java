package com.example.practicalwork4;
// Импортируем все необходимые классы по умолчанию
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
	// Создаём компоненты TextView для процесса вычисления математических операций и результата выполнения математических операций
	TextView resultTv, solutionTv;
	// Создаём компоненты MaterialButton для разных кнопок
	MaterialButton buttonC, buttonBrackOpen,buttonBrackClose;
	MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
	MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
	MaterialButton buttonAC, buttonDot;

	// Используем переопределение метода
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		resultTv = findViewById(R.id.result_tv);
		solutionTv = findViewById(R.id.solution_tv);
        // Присваиваем идентификаторы кнопкам
		assignId(buttonC, R.id.button_c);
		assignId(buttonBrackOpen, R.id.button_open_bracket);
		assignId(buttonBrackClose, R.id.button_close_bracket);
		assignId(buttonDivide, R.id.button_divide);
		assignId(buttonMultiply, R.id.button_multiply);
		assignId(buttonPlus, R.id.button_plus);
		assignId(buttonMinus, R.id.button_minus);
		assignId(buttonEquals, R.id.button_equals);
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
		assignId(buttonAC, R.id.button_ac);
		assignId(buttonDot, R.id.button_dot);
    }

	// Создаём функцию assignId, реализующую поиск компонента MaterialButton по id и присвоение его переменной btn
	void assignId(MaterialButton btn, int id){
		btn = findViewById(id);
		// Создаём слушатель события
		btn.setOnClickListener(this);
	}

	// Используем переопределение метода
	@Override
	public void onClick(View view) {
		MaterialButton button=(MaterialButton) view;
		// Создаём переменную buttonText типа String, где получаем текст из переменной button
		String buttonText = button.getText().toString();
		// Создаём переменную dataToCalculate типа String, где получаем текст из переменной solutionTv
		String dataToCalculate = solutionTv.getText().toString();

		// Если была нажата кнопка "AC", то полностью очищаем содержимое и устанавливаем 0
		if(buttonText.equals("AC")){
			solutionTv.setText("");
			resultTv.setText("0");
			return;
		}
		// Если бы нажата кнопка "=", то производим вычисление и показываем результат
		if(buttonText.equals("=")){
			solutionTv.setText(resultTv.getText());
			return;
		}
		// Если была нажата кнопка "C", то удаляем посимвольно элементы
		if(buttonText.equals("C")){
			dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
		}else{
			dataToCalculate = dataToCalculate+buttonText;
		}
        solutionTv.setText(dataToCalculate);
		// Объявляем переменную finalResult типа String и приравниваем к ней функцию getResult с параметром dataToCalculate
		String finalResult = getResult(dataToCalculate);
		// Если переменная finalResult не содержит ошибку, то вызываем resultTv, где вызываем setText и передаём параметр finalResult
		if(!finalResult.equals("Err")){
			resultTv.setText(finalResult);
		}

	}

	// Создаём функцию для получения результата вычисления
	String getResult(String data){
		// Создаём блок try...catch
		try{
			// Создаём переменную context типа Context
			Context context  = Context.enter();
			// Устанавлиаем уровень оптимизации в -1
			context.setOptimizationLevel(-1);
			// Создаём переменную scriptable  типа Scriptable
			Scriptable scriptable = context.initStandardObjects();
			// Создаём переменную finalResult типа String, где преобразуем числовой результат в строковый
			String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
			// Если переменная finalResult заканчивается на .0, то символ заменяется на пустоту
			if(finalResult.endsWith(".0")){
				finalResult = finalResult.replace(".0","");
			}
			// Возвращаем результат вычисления
			return finalResult;
			// Обрабатываем исключение
		}catch (Exception e){
			return "Err";
		}
	}
}
