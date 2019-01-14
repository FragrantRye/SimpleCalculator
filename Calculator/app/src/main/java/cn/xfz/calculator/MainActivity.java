package cn.xfz.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private String express="";
    private Grammar gra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.textView_q)).setText("请输入表达式");
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button10).setOnClickListener(this);
        findViewById(R.id.button11).setOnClickListener(this);
        findViewById(R.id.button12).setOnClickListener(this);
        findViewById(R.id.button13).setOnClickListener(this);
        findViewById(R.id.button14).setOnClickListener(this);
        findViewById(R.id.button15).setOnClickListener(this);
        findViewById(R.id.button18).setOnClickListener(this);
        findViewById(R.id.buttonE).setOnClickListener(this);
    }

    public void AllClear(View v){
        express="";
        ((TextView) findViewById(R.id.textView_q)).setText(express);
        ((TextView) findViewById(R.id.textView_a)).setText("");
    }

    public void Delete(View v){
        if(express!=null && express.length()>0) {
            express = express.substring(0, express.length() - 1);
            ((TextView) findViewById(R.id.textView_q)).setText(express);
        }
    }
    public void Apply(View v){
        String temp=express.replace('×','*');
        temp=temp.replace('÷','/');
        gra=new Grammar(temp);
        if(gra.Test()==0)
            ((TextView)findViewById(R.id.textView_a)).setText(gra.GetResult().stripTrailingZeros().toPlainString());
        else
            ((TextView)findViewById(R.id.textView_a)).setText("表达式有误");
    }
    @Override
    public void onClick(View v) {
        if(((TextView)findViewById(R.id.textView_a)).getText()!=null && !((TextView)findViewById(R.id.textView_a)).getText().toString().equals(""))
            AllClear(v);
        express+=((Button)v).getText();
        ((TextView) findViewById(R.id.textView_q)).setText(express);
    }
}
