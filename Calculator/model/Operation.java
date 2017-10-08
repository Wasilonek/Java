package BeatifulCalculator.model;

/**
 * Created by Kamil on 2017-09-06.
 */
public class Operation {
    public double operation(double num1 , double num2 , String operator ){
        switch (operator){
            case "+":{
                return num1 + num2;
            }
            case "-":{
                return num1 - num2;
            }
            case "*":{
                return num1 * num2;
            }
            case "/":{
                if(num2 == 0)
                    return 0;
                return num1 / num2;
            }
            case "%":{
                return num1 % num2;
            }
        }
        return 0;
    }
}
