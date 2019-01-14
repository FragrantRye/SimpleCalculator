package cn.xfz.calculator;

import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;

class Grammar {
    private static String[] rule={"CB","+C0B","-C1B","","ED","*E2D","/E3D","","i4","(A)","+C5","-C6"};
    private static Map<Character, Map<Character, Integer>> table;
    static{
        table= new HashMap<>();
        Map<Character, Integer> tempA= new HashMap<>();
        tempA.put('i', 0);
        tempA.put('(', 0);
        tempA.put('+', 0);
        tempA.put('-', 0);
        table.put('A', tempA);

        Map<Character, Integer> tempB= new HashMap<>();
        tempB.put('+', 1);
        tempB.put('-', 2);
        tempB.put(')', 3);
        tempB.put('#', 3);
        table.put('B', tempB);

        Map<Character, Integer> tempC= new HashMap<>();
        tempC.put('i', 4);
        tempC.put('(', 4);
        tempC.put('+', 10);
        tempC.put('-', 11);
        table.put('C', tempC);

        Map<Character, Integer> tempD= new HashMap<>();
        tempD.put('+', 7);
        tempD.put('-', 7);
        tempD.put('*', 5);
        tempD.put('/', 6);
        tempD.put(')', 7);
        tempD.put('#', 7);
        table.put('D', tempD);

        Map<Character, Integer> tempE= new HashMap<>();
        tempE.put('i', 8);
        tempE.put('(', 9);
        table.put('E', tempE);
    }
    private Word w;
    private Stack<Character> analyze;
    private Lexical input;
    private Stack<BigDecimal> nur_S;

    Grammar(String str){
        analyze =new Stack<>();
        nur_S=new Stack<>();
        input=new Lexical(str);
        analyze.push('#');
        analyze.push('A');
    }
    BigDecimal GetResult(){
        if(nur_S.empty())
            return new BigDecimal(0);
        return nur_S.pop();
    }
    private void OperatingStack(char OperatingCode){
        BigDecimal x,y;
        switch(OperatingCode){
            case '0':
                x=nur_S.pop();
                y=nur_S.pop();
                nur_S.push(y.add(x));
                break;
            case '1':
                x=nur_S.pop();
                y=nur_S.pop();
                nur_S.push(y.subtract(x));
                break;
            case '2':
                x=nur_S.pop();
                y=nur_S.pop();
                nur_S.push(y.multiply(x));
                break;
            case '3':
                x=nur_S.pop();
                y=nur_S.pop();
                nur_S.push(y.divide(x,20,RoundingMode.HALF_UP));
                break;
            case '4':
                nur_S.push((BigDecimal)w.value);
                break;
            case '6':
                x=nur_S.pop();
                nur_S.push(x.multiply(new BigDecimal(-1)));
                break;
        }
    }
    int Test(){
        w = input.GetNextWord();
        char temp;
        if(w.type==0)
            temp=(char)w.value;
        else if(w.type==1)
            temp='i';
        else
            return (int)w.value;
        while (analyze.peek() != '#' || (char) w.value != '#') {
            if (analyze.peek() == temp) {
                analyze.pop();

                if(analyze.peek()>='0' && analyze .peek()<='6') {
                    OperatingStack(analyze.pop());
                }

                w = input.GetNextWord();
                if (w.type == 0)
                    temp = (char) w.value;
                else if (w.type == 1)
                    temp = 'i';
                else
                    return (int) w.value;
                continue;
            }

            if(analyze.peek()>='0' && analyze .peek()<='6') {
                OperatingStack(analyze.pop());
                continue;
            }
            try {
                int rule_index = table.get(analyze.peek()).get(temp);
                String rule_t = rule[rule_index];
                analyze.pop();

                for (int i = rule_t.length() - 1; i >= 0; i--)
                    analyze.push(rule_t.charAt(i));
            } catch (Exception e) {
                return input.GetPointer();
            }
        }
        return 0;
    }
}