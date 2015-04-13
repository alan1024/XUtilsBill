package com.alan.xutilsbill;


        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.os.Bundle;

        import android.provider.Settings;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import com.alan.helper.MyDatePickerDialog;
        import com.alan.model.Bill;
        import com.lidroid.xutils.DbUtils;
        import com.lidroid.xutils.db.sqlite.DbModelSelector;
        import com.lidroid.xutils.db.table.DbModel;
        import com.lidroid.xutils.exception.DbException;


        import java.util.*;

public class ShowActivity extends Activity {
    private ArcView arcView;
    private DbUtils dbUtils;
    private Button button_time,button_ok;
    private EditText editText;
    private List<Map<String, Object>> list;
    private String [] num={"00","01","100","101","102","110","111","120","121","130","131","140","141"};
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);
        arcView= (ArcView) findViewById(R.id.arcView);
        list=new ArrayList<Map<String, Object>>();
        dbUtils=DbUtils.create(this, "db_bill.db");
        button_ok= (Button) findViewById(R.id.button_ok);
        editText= (EditText) findViewById(R.id.editText_main);
        button_time= (Button) findViewById(R.id.button_time);
        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(
                        ShowActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editText.setText( i + "-" + (i1+1) );
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                datePickerDialog.show();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = editText.getText().toString();
                float money=0;

                String count = select(num[0]);
                if(count!=null){
                    arcView.setWage(Float.parseFloat(count));
                }else{
                    arcView.setWage(0);
                }


                count = select(num[1]);
                if(count!=null){
                    arcView.setExtra(Float.parseFloat(count));
                }else{
                    arcView.setExtra(0);
                }


                count = select(num[2]);
                if(count!=null){
                    money=Float.parseFloat(count);
                }
               count = select(num[3]);
                if(count!=null){
                    money=Float.parseFloat(count);
                }
                count = select(num[4]);
                if(count!=null){
                    money=Float.parseFloat(count);
                }
                arcView.setEatFee(money);



                money=0;
                count = select(num[5]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                count = select(num[6]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                arcView.setClothesFee(money);


                money=0;
                count = select(num[7]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                count = select(num[8]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                arcView.setLiveFee(money);



                money=0;
                count = select(num[9]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                count = select(num[10]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                arcView.setTransFee(money);



                money=0;
                count = select(num[11]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                count = select(num[12]);
                if(count!=null){
                    money=money+Float.parseFloat(count);
                }
                arcView.setUseFee(money);


                arcView.invalidate();
            }

        });
    }


    public String select(Object value){
        DbModel model = null;
        try {
            model = dbUtils.findDbModelFirst(DbModelSelector.from(Bill.class).select("sum(money)").where("typeId","=",value));
            if (model!=null){
                HashMap<String, String> dataMap = model.getDataMap();
                Set<String> strings = dataMap.keySet();
                for(String key:strings){
                    String values=dataMap.get(key);
                    System.out.print("-------"+key+values);
                    return values;
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


}
