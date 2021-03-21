package L1;
import javafx.util.Pair;

import java.util.*;

public class main {
    // Метод определения типа символа
    // 0 - скобка
    // 1 - число
    // 2 - другой символ
    public static int typeOfSymbol(char in){
        if (in == '[' || in == ']'){
            return 0;
        }
        if (in == '0'
                || in == '1'
                || in == '2'
                || in == '3'
                || in == '4'
                || in == '5'
                || in == '6'
                || in == '7'
                || in == '8'
                || in == '9'){
            return 1;
        }
        return 2;
    }

    //Метод раскрытия скобок
    public static Pair<Integer, String> braces (String s, int pos){
        String result = "";           // Результирующая строка
        String temp_result = "";      // Строка раскрытия внутренних скобок
        int numOfRepeat = 0;          // Кол-во повторений строки
        int repeatCount = 1;          // Множитель для повторений
        int temp_pos = pos - 1;       // Временная переменная для считывания значений
        // Высчитываем количество повторений
        while (temp_pos >= 0 && typeOfSymbol(s.charAt(temp_pos)) == 1){                  // Пока не сместимся на начало строки и символ - число
            numOfRepeat += Character.digit(s.charAt(temp_pos), 10)  * repeatCount; // Увеличиваем количество повторений
            repeatCount *= 10;        // Увеличиваем множитель для корректного получения следующего разряда
            temp_pos--;               // Сдвигаемся на 1 символ влево
        }
        Pair<Integer, String> tmp_values; // Пара для однократного вызова метода раскрытия скобок
        for (int i = 0; i < numOfRepeat; i++) { // Склеиваем строку столько раз, сколько количество повторений
            temp_pos = pos;               // Получаем текущую позицию строки
            while (s.charAt(++temp_pos) != ']') { // Пока не "наткнёмся" на закрывающую скобку
                if (typeOfSymbol(s.charAt(temp_pos)) == 2) { // Если символ не число и не скобка, склеиваем результаты
                    result += temp_result;
                    result += s.charAt(temp_pos);
                }
                if (s.charAt(temp_pos) == '[') {  // Рекурсия на случай скобки внутри скобки
                    tmp_values = braces(s, temp_pos);
                    temp_result += tmp_values.getValue(); // Во временный результат пишем раскрытие для корректного отображения
                    pos = tmp_values.getKey();  // Получаем текущую позицию (для пропуска раскрытых скобок)
                    temp_pos = pos;
                }
            }
        }
        return new Pair<Integer, String>(temp_pos, result);
    }

    public static String unpack(String s){
        int numOfBrace = 0;

        String result = "";
        Pair<Integer, String> tmp_values;
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == '['){
                numOfBrace++;
                if (i == 0 || typeOfSymbol(s.charAt(i-1)) != 1){
                    return "string is not valid"; // Если скобка без числа - строка не валидна
                }
                tmp_values = braces(s, i);
                result += tmp_values.getValue();
                i = tmp_values.getKey();
            }
            if (s.charAt(i) == ']'){
                numOfBrace--;
            }
            if (numOfBrace < 0){
                return "string is not valid"; // Если в какой-то момент закрывающих скобок больше, чем открывающих - строка не валидна
            }
            if (typeOfSymbol(s.charAt(i)) == 2){
                result += s.charAt(i);
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        System.out.println(unpack(a));
    }
}
