package cn.xfz.calculator;

import java.math.BigDecimal;

public class Lexical {
    private String str;
    private int pointer;
    private static final int[][] table = {
            { 1, 1, 4, 6, 4, 6, 6},
            { 0,-1,-1, 5,-1,-1,-1},
            { 0,-1,-1, 5,-1,-1,-1},
            { 3, 3,-1,-1, 3,-1,-1},
            { 2, 2,-1,-1,-1,-1,-1}};
    Lexical(String str) {
        this.str = str;
        pointer=0;
    }

    int GetPointer(){
        return pointer;
    }
    Word GetNextWord(){
        if(pointer>str.length()-1)
            return new Word(0, '#');
        char value=0;
        if(str.charAt(pointer)=='*')
            value='*';
        else if(str.charAt(pointer)=='/')
            value='/';
        else if(str.charAt(pointer)=='(')
            value = '(';
        else if(str.charAt(pointer)==')')
            value = ')';
        else if(str.charAt(pointer)=='+')
            value = '+';
        else if(str.charAt(pointer)=='-')
            value = '-';
        if(value!=0) {
            pointer++;
            return new Word(0, value);
        }

        int state=0;
        int begin=pointer;
        while(true){
            if(pointer>=str.length())
                break;
            int op;
            if(str.charAt(pointer)>='0'&&str.charAt(pointer)<='9')
                op=0;
            else if(str.charAt(pointer)=='+')
                op=1;
            else if(str.charAt(pointer)=='-')
                op=2;
            else if(str.charAt(pointer)=='e')
                op=3;
            else if(str.charAt(pointer)=='.')
                op=4;
            else
                break;
            if((state = table[op][state])==-1)
                break;
            pointer++;
        }
        BigDecimal x;
        try {
            x = new BigDecimal(str.substring(begin, pointer));
        }catch(NumberFormatException e){
            return new Word(-1,pointer);
        }
        return new Word(1, x);
    }
    public static void main(String[] args){
        Lexical w=new Lexical("1e2");

        Word temp=new Word(0, 0);
        WHILE:while(temp.value!=null && !temp.value.equals('#')) {
            temp = w.GetNextWord();
            switch(temp.type){
                case -1:
                    System.out.println("终止。");
                    break WHILE;
                case 0:
                    System.out.println("算符："+(char)temp.value);
                    break;
                case 1:
                    System.out.println("数字："+((BigDecimal)temp.value).toPlainString());
                    break;
            }
        }
    }
}
