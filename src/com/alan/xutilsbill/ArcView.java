package com.alan.xutilsbill;

import android.content.Context;
import android.graphics.*;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aaa on 15-4-9.
 * User:alan
 * Date:
 * Email:
 */
public class ArcView extends View {
    private Paint arcPaint;
    private TextPaint textPaint;
    private RectF rectF1,rectF2;

    private float wage;
    private float extra;
    private float income;

    private float eatFee;
    private float clothesFee;
    private float liveFee;
    private float transFee;
    private float useFee;
    private float spending;
    public ArcView(Context context){
        this(context,null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs){
        arcPaint=new Paint();
        arcPaint.setAntiAlias(true);
        textPaint=new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        rectF1=new RectF(30,30,230,230);
        rectF2=new RectF(300,30,500,230);



    }

    public void init(){


        income=wage+extra;
        wage=wage/income*360;
        extra=extra/income*360;

        spending=eatFee+clothesFee+liveFee+transFee+useFee;
        eatFee=eatFee/spending*360;
        clothesFee=clothesFee/spending*360;
        liveFee=liveFee/spending*360;
        transFee=transFee/spending*360;
        useFee=useFee/spending*360;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();
        //第一个
        if(income>0){
            arcPaint.setColor(Color.RED);
            canvas.drawArc(rectF1,0,wage,true,arcPaint);

            arcPaint.setColor(Color.YELLOW);
            canvas.drawArc(rectF1,wage,extra,true,arcPaint);

            arcPaint.setColor(Color.WHITE);
            canvas.drawCircle(130,130,40,arcPaint);

            textPaint.setTextSize(22);
            canvas.drawText("收入",110,130,textPaint);
        }


        if(spending>0){
            //第二个
            arcPaint.setColor(Color.RED);
            canvas.drawArc(rectF2,0,eatFee,true,arcPaint);

            arcPaint.setColor(Color.YELLOW);
            canvas.drawArc(rectF2,eatFee,clothesFee,true,arcPaint);

            arcPaint.setColor(Color.GREEN);
            canvas.drawArc(rectF2,eatFee+clothesFee,liveFee,true,arcPaint);

            arcPaint.setColor(Color.BLUE);
            canvas.drawArc(rectF2,eatFee+clothesFee+liveFee,transFee,true,arcPaint);

            arcPaint.setColor(Color.CYAN);
            canvas.drawArc(rectF2,eatFee+clothesFee+liveFee+transFee,useFee,true,arcPaint);

            arcPaint.setColor(Color.WHITE);
            canvas.drawCircle(400,130,40,arcPaint);

            textPaint.setTextSize(22);
            canvas.drawText("支出",380,130,textPaint);
        }



    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public float getExtra() {
        return extra;
    }

    public void setExtra(float extra) {
        this.extra = extra;
    }

    public float getEatFee() {
        return eatFee;
    }

    public void setEatFee(float eatFee) {
        this.eatFee = eatFee;
    }

    public float getClothesFee() {
        return clothesFee;
    }

    public void setClothesFee(float clothesFee) {
        this.clothesFee = clothesFee;
    }

    public float getLiveFee() {
        return liveFee;
    }

    public void setLiveFee(float liveFee) {
        this.liveFee = liveFee;
    }

    public float getTransFee() {
        return transFee;
    }

    public void setTransFee(float transFee) {
        this.transFee = transFee;
    }

    public float getUseFee() {
        return useFee;
    }

    public void setUseFee(float useFee) {
        this.useFee = useFee;
    }
}
