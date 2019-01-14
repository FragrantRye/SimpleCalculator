package cn.xfz.calculator;
class Word {
    //0:算符，1:数字, -1:异常
    int type;
    Object value;
    Word(int t, Object v){
        type=t;
        value=v;
    }
}
