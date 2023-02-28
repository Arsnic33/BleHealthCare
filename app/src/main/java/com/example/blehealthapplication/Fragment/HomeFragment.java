package com.example.blehealthapplication.Fragment;

import static com.example.blehealthapplication.MainActivity.uAge;
import static com.example.blehealthapplication.MainActivity.uCommon;
import static com.example.blehealthapplication.MainActivity.uGender;
import static com.example.blehealthapplication.MainActivity.uName;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blehealthapplication.Activity.LoginActivity;
import com.example.blehealthapplication.DataBase.InBodyDB;
import com.example.blehealthapplication.MainActivity;
import com.example.blehealthapplication.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private MainActivity activity;

    //user db
    InBodyDB inBodyDB;
    SQLiteDatabase sql, db;
    private String Final_UserID, Final_UserName, Final_UserAge, Final_UserGender, Final_UserTall;
    private TextView tv_name, tv_age, tv_tall;
    private ImageView iv_gender;
    private Button btn_inBodyLook;
    private boolean bodyLook = false;

    //user
    private Menu menu;
    //유저 리스트 다이어로그
    public ArrayList<String> mUserNameArrayList = new ArrayList<String>();
    public AlertDialog mUserListDialog;

    //inBody tablelayout
    private TableLayout tableLayout;
    private String Select_name;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //인바디 정보 입력

        tableLayout = (TableLayout) view.findViewById(R.id.tablelayout_ib);

//        ibTablelayout();
        tableLayout.setVisibility(View.INVISIBLE);

        tv_name = (TextView) view.findViewById(R.id.tv_homeName);
        tv_age = (TextView) view.findViewById(R.id.tv_homeAge);
        tv_tall = (TextView) view.findViewById(R.id.tv_homeTall);
        iv_gender = (ImageView) view.findViewById(R.id.imageView_gender);
        btn_inBodyLook = (Button) view.findViewById(R.id.btn_inBodyLook);

        // setting
        tv_name.setText(uName);
        tv_age.setText(uAge);
        tv_tall.setText(uCommon);
        if(uGender.equals("남")){
            iv_gender.setImageResource(R.drawable.ic_baseline_male_48);
        } else if(uGender.equals("여")){
            iv_gender.setImageResource(R.drawable.ic_baseline_female_48);
        }



        btn_inBodyLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bodyLook){
                    tableLayout.setVisibility(View.VISIBLE);
                    ibTablelayout();
                    bodyLook = true;
                } else if(bodyLook) {
                    tableLayout.setVisibility(View.INVISIBLE);
                    bodyLook = false;
                }
            }
        });
        return view;
    }


    private void init() {
        activity.getSupportActionBar().setTitle("홈");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);

        //DB
        inBodyDB = new InBodyDB(getActivity());


    }

    private void ibTablelayout() {

        tableLayout.removeAllViews();

        TableRow tableRowTitle = new TableRow(getContext()); //tablerow 생성
        tableRowTitle.setPadding(0,0,0,10);
        tableRowTitle.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout lnTitle = new LinearLayout(getContext());
        lnTitle.setPadding(0, 20,0,20);

        for(int i=0; i<5; i++) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            switch (i) {
                case 0:
                    textView.setText("날짜");
                    params.weight = 6;
                    textView.setLayoutParams(params);
                    break;
                case 1:
                    textView.setText("체중");
                    params.weight = 4;
                    textView.setLayoutParams(params);
                    break;
                case 2:
                    textView.setText("BMI");
                    params.weight = 4;
                    textView.setLayoutParams(params);
                    break;
                case 3:
                    textView.setText("체지방량");
                    params.weight = 5;
                    textView.setLayoutParams(params);
                    break;
                case 4:
                    textView.setText("골격근량");
                    params.weight = 5;
                    textView.setLayoutParams(params);
                    break;

            }
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            lnTitle.setBackground(getResources().getDrawable(R.drawable.table_outside_title));
            textView.setTextSize(14);
            lnTitle.addView(textView);
        }
        tableRowTitle.addView(lnTitle);
        tableLayout.addView(tableRowTitle); //tablelayout에 tableRow 추가

        for (int j = 0 ; j < 4 ; j++) {
            TableRow tableRowData = new TableRow(getContext()); //tablerow 생성
            tableRowData.setPadding(0,0,0,5);
            tableRowData.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout ln = new LinearLayout(getContext());
            ln.setPadding(0, 20,0,20);

            for (int i = 0; i < 5; i++) {
                TextView textView = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
                switch (i) {
                    case 0:
                        textView.setText("02-22");
                        params.weight = 6;
                        textView.setLayoutParams(params);
                        break;
                    case 1:
                        textView.setText("72kg");
                        params.weight = 4;
                        textView.setLayoutParams(params);
                        break;
                    case 2:
                        textView.setText("20.1");
                        params.weight = 4;
                        textView.setLayoutParams(params);
                        break;
                    case 3:
                        textView.setText("13.7%");
                        params.weight = 5;
                        textView.setLayoutParams(params);
                        break;
                    case 4:
                        textView.setText("9.8%");
                        params.weight = 5;
                        textView.setLayoutParams(params);
                        break;

                }
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                ln.setBackground(getResources().getDrawable(R.drawable.table_outside));
                textView.setTextSize(14);
                ln.addView(textView);
            }
            tableRowData.addView(ln);
            tableLayout.addView(tableRowData); //tablelayout에 tableRow 추가
        }

        db = inBodyDB.getReadableDatabase();
        Cursor cursor;
        if(Final_UserName != null) {
            Select_name = Final_UserName + " ";
            Log.d("mmak", Final_UserName + "Final 이름임");
            Log.d("mmak", Select_name + "select 이름임");
            cursor = db.rawQuery("SELECT * FROM inbody Where name = '"+Final_UserName+"';", null);
            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
//                    Log.d("mmak", String.valueOf(cursor.moveToNext()));
//                    Log.d("mmak", "0 + " + cursor.getString(0));
                    Log.d("mmak", "1 + " + cursor.getString(1)+ "end");
//                    Log.d("mmak", "2 + " + cursor.getString(2));
//                    Log.d("mmak", "3 + " + cursor.getString(3));
//                    Log.d("mmak", "4 + " + cursor.getString(4));
                    Log.d("mmak", "5 + " + cursor.getString(5)+ "end");
                    Log.d("mmak", "6 + " + cursor.getString(6));
                    Log.d("mmak", "7 + " + cursor.getString(7));
                    Log.d("mmak", "8 + " + cursor.getString(8));
                    Log.d("mmak", "9 + " + cursor.getString(9));
                    tableRowTitle = new TableRow(getContext()); //tablerow 생성
                    tableRowTitle.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));


                    for(int i=5; i<10; i++){


                        TextView textView = new TextView(getContext());
                        textView.setText(cursor.getString(i));
                        textView.setGravity(Gravity.CENTER);
                        textView.setTextColor(Color.BLACK);
                        textView.setBackground(getResources().getDrawable(R.drawable.table_inside));
                        textView.setTextSize(18);
                        tableRowTitle.addView(textView); //tableRow에 view추가
                    }
                    tableLayout.addView(tableRowTitle); //tablelayout에 tableRow 추가


                }


            }
            cursor.close();
            db.close();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_setting_menu, menu);
        this.menu = menu;
        menu.findItem(R.id.UserSetting1).setTitle(uName);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.UserSetting2:         //로그아웃
                logoutDialog();
                break;
        }
        return true;
    }

    public void logoutDialog() {  //로그아웃


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("로그아웃 하시겠습니까?");
        //유저를 선택하면 선택한 값을 토대로 앱 재실행시 사용자가 자동으로 연결되게 DB에 저장
        builder.setPositiveButton("확인",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().finishAffinity();
                startActivity(intent);

            }
        });
        //유저를 선택하지 않고 취소를 누르면 Dialog 창 종료
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }








}
