package ru.mirea.makarov.mireaproject1;

import java.util.ArrayList;

public class Mathematics {
    private float res = 0;
    public Mathematics(String line){
        ArrayList<Float> numbers = new ArrayList<>();
        ArrayList<Character> symbols = new ArrayList<>();
        char[] split_line = line.toCharArray();
        StringBuilder number = new StringBuilder();
        for (int ind = 0; ind < split_line.length; ++ind){
            switch (split_line[ind]){
                case '-':
                    numbers.add(Float.parseFloat(number.toString()));
                    number = new StringBuilder();
                    symbols.add('-');
                    break;
                case '+':
                    numbers.add(Float.parseFloat(number.toString()));
                    number = new StringBuilder();
                    symbols.add('+');
                    break;
                case '*':
                    numbers.add(Float.parseFloat(number.toString()));
                    number = new StringBuilder();
                    symbols.add('*');
                    break;
                case '/':
                    numbers.add(Float.parseFloat(number.toString()));
                    number = new StringBuilder();
                    symbols.add('/');
                    break;
                case '%':
                    numbers.add(Float.parseFloat(number.toString()));
                    number = new StringBuilder();
                    symbols.add('%');
                    break;
                default:
                    number.append(split_line[ind]);
                    if (ind == split_line.length-1) numbers.add(Float.parseFloat(number.toString()));
                    break;
            }
        }
        for (int indSymbol = 0; indSymbol < symbols.size(); ++indSymbol){
            if (symbols.get(indSymbol).equals('*') || symbols.get(indSymbol).equals('/') ||
                    symbols.get(indSymbol).equals('%')){
                float result_operation;
                if (symbols.get(indSymbol).equals('*')){
                    result_operation = numbers.get(indSymbol) * numbers.get(indSymbol+1);
                }else if (symbols.get(indSymbol).equals('/')){
                    result_operation = numbers.get(indSymbol) / numbers.get(indSymbol+1);
                }else{
                    result_operation = numbers.get(indSymbol) % numbers.get(indSymbol+1);
                }
                ArrayList<Character> new_array_char = new ArrayList<>();
                symbols.set(indSymbol, '+');
                numbers.set(indSymbol, 0f);
                numbers.set(indSymbol+1, result_operation);
            }
        }
        res += numbers.get(0);
        for (int indSymbol = 0; indSymbol < symbols.size(); ++indSymbol){
            res += numbers.get(indSymbol+1)*(symbols.get(indSymbol).equals('-')?(-1) : (1));
        }
    }
    public float getRes(){
        return res;
    }
}
