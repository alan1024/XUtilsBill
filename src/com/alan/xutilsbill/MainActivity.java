package com.alan.xutilsbill;


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.DatePickerDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.*;
        import com.alan.helper.MyAdapter;
        import com.alan.helper.MyDatePickerDialog;
        import com.alan.model.Bill;
        import com.alan.model.Category;
        import com.lidroid.xutils.DbUtils;
        import com.lidroid.xutils.db.sqlite.Selector;
        import com.lidroid.xutils.exception.DbException;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;
        import java.util.Map;

/**
 * Created by aaa on 15-4-10.
 * User:alan
 * Date:
 * Email:
 */
public class MainActivity extends Activity {
    private ListView listView;
    private TextView textView;
    private List<Bill> totalList;
    private List<Bill> list;
    private DbUtils dbUtils;
    private MyAdapter adapter;
    private int position;
    private String[] obj={"收入：工资","收入：外快","支出：吃-日常","支出：吃-请客","支出：吃-烟酒","支出：穿-自用","支出：穿-礼物","支出：住-房租","支出：住-水电费","支出：行-公交","支出：行-出租","支出：用-学习","支出：用-生活"};
    private String[] num={"00","01","100","101","102","110","111","120","121","130","131","140","141"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView= (ListView) findViewById(R.id.listView);
        textView= (TextView) findViewById(R.id.textView_start);
        dbUtils=DbUtils.create(this, "db_bill.db", 1, new DbUtils.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbUtils dbUtils, int i, int i1) {

            }
        });
        totalList=new ArrayList<Bill>();

        try {
            if(!dbUtils.tableIsExist(Category.class)){
                for(int i=0;i<num.length;i++){
                    Category cate=new Category();
                    cate.setTypeId(num[i]);
                    cate.setType(obj[i]);
                    try {
                        dbUtils.save(cate);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            if(dbUtils.tableIsExist(Bill.class)){
                totalList=dbUtils.findAll(Selector.from(Bill.class));
                adapter=new MyAdapter(this,totalList);
                listView.setAdapter(adapter);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        listView.setEmptyView(textView);
        registerForContextMenu(listView);

    }

    public void reload(){
        totalList.clear();
        try {
            list=dbUtils.findAll(Selector.from(Bill.class));
        } catch (DbException e) {
            e.printStackTrace();
        }
        totalList.addAll(list);
        Log.i("--------", totalList.toString());
        adapter=new MyAdapter(this,totalList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bt:
                Intent intent=new Intent();
                intent.setClass(this,ShowActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add:

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("添加账单");
                View view = getLayoutInflater().inflate(R.layout.layout_dialog,null);
                Spinner spinner= (Spinner) view.findViewById(R.id.spinner);
                final EditText editText= (EditText) view.findViewById(R.id.editText);
                final EditText editText_time= (EditText) view.findViewById(R.id.editText_time);
                Button button= (Button) view.findViewById(R.id.button3);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Calendar calendar = Calendar.getInstance();
                        MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(
                                MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                editText_time.setText( i + "-" + (i1+1) );
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                        datePickerDialog.show();

                    }
                });
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, obj);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        position=i;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spinner.setAdapter(adapter);

                builder.setView(view);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String money=editText.getText().toString();
                        String date=editText_time.getText().toString();
                        if(money!=null&&!money.equals("")&&date!=null&&!date.equals("")){
                            Bill bill=new Bill();
                            bill.setMoney(Float.parseFloat(money));
                            bill.setDate(date);
                            Category category=new Category();
                            category.setTypeId(num[position]);
                            bill.setCategory(category);
                            try {
                                dbUtils.save(bill);
                                Toast.makeText(MainActivity.this, "添加成功！",Toast.LENGTH_LONG).show();
                                reload();
                            } catch (DbException e) {
                                Toast.makeText(MainActivity.this,"添加失败，请重新添加",Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"请输入值！",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                builder.show();
                break;
            case R.id.action_code:
                Intent intent1=new Intent(this,BarCodeTestActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final long id=totalList.get(info.position).getId();
        switch (item.getItemId()){
            case R.id.action_del:
                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setIcon(R.drawable.ic_launcher);
                builder1.setTitle("删除");
                builder1.setMessage("确认删除吗！");
                builder1.setNegativeButton("取消", null);
                builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bill bill=new Bill();
                        bill.setId(id);
                        try {
                            dbUtils.delete(bill);
                            Toast.makeText(MainActivity.this, "删除OK", Toast.LENGTH_SHORT).show();
                            reload();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder1.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

}
