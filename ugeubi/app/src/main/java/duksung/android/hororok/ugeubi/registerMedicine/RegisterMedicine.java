package duksung.android.hororok.ugeubi.registerMedicine;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import duksung.android.hororok.ugeubi.MainActivity;
import duksung.android.hororok.ugeubi.R;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineDTO;
import duksung.android.hororok.ugeubi.retrofit.medicine.MedicineResultDTO;
import duksung.android.hororok.ugeubi.medicine.Medicine_kit_fragment;
import duksung.android.hororok.ugeubi.retrofit.RetrofitClient;
import duksung.android.hororok.ugeubi.retrofit.RetrofitInterface;
import duksung.android.hororok.ugeubi.retrofit.medicine.TakingInfoDayDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class RegisterMedicine extends AppCompatActivity {

    MainActivity mainActivity = (MainActivity) MainActivity.mainActivity;



    // retrofit
    RetrofitInterface apiService;

    public final String PREFERENCE = "ugeubi.preference";

    // medicineDTO
    MedicineDTO medicineDTO = null;

    // 약 이름 EditText
    EditText medicineName, memo;

    // 약 종류 버튼
    ToggleButton pill, liquidMedicine, powderedMedicine, ointment, prescriptionDrug;

    // 유통기한 TextView
    TextView expirationDate;

    // DatePickerDialog
    SpinnerDatePickerDialog spinnerDatePickerDialog;

    // 약 복용유무 버튼그룹
    RadioGroup takingBtnGroup;

    // 복용기간 EditText
    EditText takingDays;

    // 복용타입 그룹
    RadioGroup takingTypeBtnGroup;

    // 요일 버튼
    ToggleButton mon, tue, wed, thu, fri, sat, sun;
    ToggleButton[] week = new ToggleButton[7];


    // 언제 약을 복용하시나요
    TextView takingMedicine_q, medicinetext;

    LinearLayout takinglayout;

    // 복용간격 버튼
    ToggleButton term_everyday, term2, term3, term4, term5, term6, term_week;

    // 약 복용횟수 스피너
    Spinner spinner;

    // 약 복용시간 리스트
    RecyclerView takingTimeList;
    TakingTimeListAdapter takingTimeListAdapter;

    TimePickerDialog timePickerDialog;

    // 약 복용갯수 EditText
    EditText takingDoseNum;

    TextView textView3;

    // 취소 및 등록 버튼
    Button back_btn, add_btn;


    // section
    LinearLayout expiration_date_section, isTaken_section, prescription_section, takingType_section, takingDay_section, takingTerm_section, takingTime_section;


    public String medicineType_txt = "PILL";
    public String medicineValidterm;
    public boolean isTaken = false;
    public Date date_txt;
    public List<String> takingTime;
    public List<TakingTimeData> takingTimeDataList;
    public List<String> takingDayOfWeek;


    public boolean checkedName = false;
    public boolean checkedTakingNum = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        /************************** init ***************************/

        // retrofit
        apiService = RetrofitClient.getService();

        // 약 이름 EditText
        medicineName = findViewById(R.id.medicine_name);

        // 약 메모
        memo = findViewById(R.id.medicine_memo);

        takingMedicine_q = findViewById(R.id.textView6);
        medicinetext = findViewById(R.id.textView7);
        takinglayout = findViewById(R.id.taking_layout);
        textView3 = findViewById(R.id.textView3);



        // 약 종류 버튼
        pill = findViewById(R.id.pill);
        liquidMedicine = findViewById(R.id.liquidMedicine);
        powderedMedicine = findViewById(R.id.powderedMedicine);
        ointment = findViewById(R.id.ointment);
        prescriptionDrug = findViewById(R.id.prescriptionDrug);

        // 유통기한 TextView
        expirationDate = findViewById(R.id.expiration_date);

        // 약 복용유무 버튼그룹
        takingBtnGroup = findViewById(R.id.takingBtnGroup);

        // 복용기간 EditText
        takingDays = findViewById(R.id.taking_days);

        // 복용타입 그룹
        takingTypeBtnGroup = findViewById(R.id.takingtype);

        // 요일 버튼
        week[0] = findViewById(R.id.mon);
        week[1] = findViewById(R.id.tue);
        week[2] = findViewById(R.id.wed);
        week[3] = findViewById(R.id.thu);
        week[4] = findViewById(R.id.fri);
        week[5] = findViewById(R.id.sat);
        week[6] = findViewById(R.id.sun);


        // 복용간격 버튼
        /*
        term_everyday = findViewById(R.id.term_everyday);
        term2 = findViewById(R.id.term_2);
        term3 = findViewById(R.id.term_3);
        term4 = findViewById(R.id.term_4);
        term5 = findViewById(R.id.term_5);
        term6 = findViewById(R.id.term_6);
        term_week = findViewById(R.id.term_week);
        */
        // 약 복용횟수 스피너
        spinner = findViewById(R.id.taking_num_spinner);

        // 약 복용시간 리스트
        takingTimeList = findViewById(R.id.takingTimeList);

        // 약 복용갯수 EditText
        takingDoseNum = findViewById(R.id.taking_dose_num);

        // section
        expiration_date_section = findViewById(R.id.expiration_date_section);
        isTaken_section = findViewById(R.id.isTaken_section);
        prescription_section = findViewById(R.id.prescription_section);
        takingType_section = findViewById(R.id.taking_type_section);
        takingDay_section = findViewById(R.id.taking_day_section);
        takingTerm_section = findViewById(R.id.taking_term_section);
        takingTime_section = findViewById(R.id.taking_time_section);

        // 취소 및 등록 버튼
        back_btn = findViewById(R.id.btn_back);
        add_btn = findViewById(R.id.btn_ok);


        week[0].setChecked(true);


        /************************** init end ***************************/



        medicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(medicineName.getText().toString().length() != 0){
                    checkedName = true;
                }
                else{
                    checkedName = false;
                }
            }
        });

        takingDoseNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(takingDoseNum.getText().toString().length() != 0){
                    checkedTakingNum = true;
                }
                else{
                    checkedTakingNum = false;
                }
            }
        });



        // 뒤로가기 및 약 등록 버튼 리스너 설정
        View.OnClickListener btn_listener = v -> {

            // 버튼의 id를 받아와 동작
            switch (v.getId()){

                // back_btn
                case R.id.btn_back:
                    onBackPressed();
                    break;



                // add_btn
                case R.id.btn_ok:

                    // 날짜 포맷
                    try {
                        date_txt = new SimpleDateFormat("yyyy년 MM월 dd일").parse(expirationDate.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        medicineValidterm = sdf.format(date_txt);

                    } catch (ParseException e) { e.printStackTrace(); }

                    // 선택된 날짜 리스트에 저장
                    int count = 0;
                    for(int i=0; i<week.length; i++){
                        if(week[i].isChecked()){
                            count ++;
                        }
                    }

                    Log.i("info","count : " + count );


                    takingDayOfWeek = new ArrayList<>(count);
                    for(int i=0; i<week.length; i++){
                        if(week[i].isChecked()) {
                            takingDayOfWeek.add(week[i].getText().toString());
                        }
                    }

                    // 선택된 시간 리스트에 저장
                    int countList = takingTimeListAdapter.getList().size();
                    takingTimeDataList = takingTimeListAdapter.getList();
                    takingTime = new ArrayList<>(countList);

                    for(TakingTimeData takingTimeData : takingTimeDataList){
                        takingTime.add(takingTimeData.time);
                    }
                    Log.e("timeList", "countList: " + countList);
                    Log.e("timeList", "takingTime: " + takingTime.size());

                    // 연고, 물약, 가루약 확인
                    if(medicineType_txt.equals("CREAM") || medicineType_txt.equals("LIQUID") || medicineType_txt.equals("POWDER")  ){
                        takingDoseNum.setText("0");
                    }


                    if(medicineType_txt.equals("PRESCRIPTION")){
                        isTaken = true;
                    }


                    Log.i("info","약이름 : " + medicineName.getText().toString());
                    Log.i("info","약타입 : " + medicineType_txt);
                    Log.i("info","약유통기한 : " + medicineValidterm);
                    Log.i("info","약 복용유무 : " + isTaken);
                    Log.i("info","약 날짜 : " + takingDayOfWeek.size());
                    Log.i("info","약 시간 : " + takingTime.size());
                    Log.i("info","약 메모 : " + memo.getText().toString());



                    if(takingDoseNum.getText().length() !=0 && medicineName.getText().length() != 0){

                        /** 약 등록 API 호출 **/

                        Log.i("info","약이름 : " + medicineName.getText().toString());
                        Log.i("info","약타입 : " + medicineType_txt);
                        Log.i("info","약유통기한 : " + medicineValidterm);
                        Log.i("info","약 복용유무 : " + isTaken);
                        Log.i("info","약 날짜 : " + takingDayOfWeek.size());
                        Log.i("info","약 시간 : " + takingTime.size());
                        Log.i("info","약 메모 : " + memo.getText().toString());


                        registerMedicine(new MedicineDTO(medicineName.getText().toString(),
                                medicineType_txt, medicineValidterm, isTaken,
                                memo.getText().toString(), new TakingInfoDayDTO(takingTime,
                                takingDayOfWeek, Integer.parseInt(takingDoseNum.getText().toString()))));


                        break;

                    }
                    else if(medicineName.getText().length() !=0 && !isTaken){
                        takingDoseNum.setText("0");
                        registerMedicine(new MedicineDTO(medicineName.getText().toString(),
                                medicineType_txt, medicineValidterm, isTaken,
                                memo.getText().toString(), new TakingInfoDayDTO(takingTime,
                                takingDayOfWeek, Integer.parseInt(takingDoseNum.getText().toString()))));

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"입력란을 확인하세요!",Toast.LENGTH_SHORT).show();
                    }






            }
        };

        // 뒤로가기 및 등록 버튼 리스너 등록
        back_btn.setOnClickListener(btn_listener);
        add_btn.setOnClickListener(btn_listener);





        // 약 종류 버튼 리스너 설정
        MedicineTypeBtnOnClickListener medicineTypeBtnOnClickListener = new MedicineTypeBtnOnClickListener();

        pill.setOnClickListener(medicineTypeBtnOnClickListener);
        liquidMedicine.setOnClickListener(medicineTypeBtnOnClickListener);
        powderedMedicine.setOnClickListener(medicineTypeBtnOnClickListener);
        ointment.setOnClickListener(medicineTypeBtnOnClickListener);
        prescriptionDrug.setOnClickListener(medicineTypeBtnOnClickListener);



        /** 유통기한 DatePickerDialog **/
        Date currentTime = Calendar.getInstance().getTime();


        medicineValidterm = new SimpleDateFormat("yyyy-mm-dd",Locale.getDefault()).format(currentTime);
        String today = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);
        expirationDate.setText(today);

        spinnerDatePickerDialog = new SpinnerDatePickerDialog(this);

        spinnerDatePickerDialog.setDialogListener(new SpinnerDatePickerDialog.SpinnerDatePickerDialogListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveClicked(int yy, int mm, int dd) {
                expirationDate.setText(yy + "년 " + mm + "월 " + dd + "일");
                // medicineValidterm = yy + "-" + mm + "-" + dd ;
            }

            @Override
            public void onNegativeClicked() {

            }
        });

        expirationDate.setOnClickListener(v -> spinnerDatePickerDialog.show());


        // 약 복용유무 버튼 리스너
        TakingBtnOnClickListener takingBtnOnClickListener = new TakingBtnOnClickListener();
        takingBtnGroup.setOnCheckedChangeListener(takingBtnOnClickListener);


        // 약 복용타입 버튼 리스너
        TakingTypeBtnOnClickListener takingTypeBtnOnClickListener = new TakingTypeBtnOnClickListener();
        takingTypeBtnGroup.setOnCheckedChangeListener(takingTypeBtnOnClickListener);

        // 복용간격 버튼 리스너
        /*
        TakingTermBtnOnClickListener takingTermBtnOnClickListener = new TakingTermBtnOnClickListener();
        term_everyday.setOnClickListener(takingTermBtnOnClickListener);
        term2.setOnClickListener(takingTermBtnOnClickListener);
        term3.setOnClickListener(takingTermBtnOnClickListener);
        term4.setOnClickListener(takingTermBtnOnClickListener);
        term5.setOnClickListener(takingTermBtnOnClickListener);
        term6.setOnClickListener(takingTermBtnOnClickListener);
        term_week.setOnClickListener(takingTermBtnOnClickListener);
        */

        // 약 복용횟수 스피너에 아이템 담기
        String[] item = {"1번", "2번", "3번", "4번", "5번"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.taking_num_spinner_item, item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(spinnerAdapter);

        SpinnerOnItemSelectedListener spinnerOnItemSelectedListener = new SpinnerOnItemSelectedListener();
        spinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);


        // 약 복용시간 리스트
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false);
        takingTimeList.setLayoutManager(linearLayoutManagerWrapper);
        takingTimeListAdapter = new TakingTimeListAdapter(this);
        takingTimeList.setAdapter(takingTimeListAdapter);

        takingTimeList.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), takingTimeList, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Log.e("Test", position + "번째");

                Toast.makeText(getApplicationContext(), "날짜선택",Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(RegisterMedicine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        takingTimeListAdapter.changeTime(position, hourOfDay, minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

                timePickerDialog.show();


            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        takingTimeListAdapter.addItem("09:00");



    }


    // back func
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }



    /** 약종류 버튼 리스너 **/
    class MedicineTypeBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();
            takingType_section.setVisibility(View.GONE);
            takingDay_section.setVisibility(View.GONE);
            takingTerm_section.setVisibility(View.GONE);
            takingTime_section.setVisibility(View.GONE);
            prescription_section.setVisibility(View.GONE);
            expiration_date_section.setVisibility(VISIBLE);
            isTaken_section.setVisibility(VISIBLE);
            takingBtnGroup.check(R.id.notTakingBtn);


            //PILL(알약),LIQUID(물약), POWDER(가루약),CREAM(연고), PRESCRIPTION(처방약)
            switch (view.getId()) {
                case R.id.pill :
                    pill.setChecked(true);
                    medicineType_txt = "PILL";
                    textView3.setText("유통기한");
                    takingMedicine_q.setText("언제 약을 복용하시나요?");
                    medicinetext.setVisibility(VISIBLE); // 몇 알 복용
                    takinglayout.setVisibility(VISIBLE);
                    break ;
                case R.id.liquidMedicine :
                    liquidMedicine.setChecked(true);
                    medicineType_txt = "LIQUID";
                    textView3.setText("유통기한");
                    takingMedicine_q.setText("언제 약을 복용하시나요?");
                    medicinetext.setVisibility(View.GONE); // 몇 알 복용
                    takinglayout.setVisibility(View.GONE);
                    break ;
                case R.id.powderedMedicine :
                    powderedMedicine.setChecked(true);
                    medicineType_txt = "POWDER";
                    textView3.setText("유통기한");
                    takingMedicine_q.setText("언제 약을 복용하시나요?");
                    medicinetext.setVisibility(View.GONE); // 몇 알 복용
                    takinglayout.setVisibility(View.GONE);
                    break ;
                case R.id.ointment :
                    ointment.setChecked(true);
                    medicineType_txt = "CREAM";
                    textView3.setText("유통기한");
                    takingMedicine_q.setText("언제 약을 사용하시나요?");
                    medicinetext.setVisibility(View.GONE);
                    takinglayout.setVisibility(View.GONE);
                    break ;
                case R.id.prescriptionDrug :
                    prescriptionDrug.setChecked(true);
                    medicineType_txt = "PRESCRIPTION";
                    textView3.setText("최종복용일");
                    takingMedicine_q.setText("언제 약을 사용하시나요?");
                    takingType_section.setVisibility(VISIBLE);
                    takingDay_section.setVisibility(VISIBLE);
                    takingTime_section.setVisibility(VISIBLE);
                    medicinetext.setVisibility(View.GONE);
                    takinglayout.setVisibility(View.GONE);
                    isTaken_section.setVisibility(View.GONE);
                    break ;
            }
        }

        void setAllBtnUnChecked() {
            pill.setChecked(false);
            liquidMedicine.setChecked(false);
            powderedMedicine.setChecked(false);
            ointment.setChecked(false);
            prescriptionDrug.setChecked(false);
        }
    }




    /** 약 복용유무 버튼 리스너 **/
    class TakingBtnOnClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.takingBtn) {
                isTaken = true;
                takingType_section.setVisibility(VISIBLE);
                takingDay_section.setVisibility(VISIBLE);
                takingTime_section.setVisibility(VISIBLE);
                takingTypeBtnGroup.check(R.id.dayOption);
            } else {
                isTaken = false;
                takingType_section.setVisibility(View.GONE);
                takingDay_section.setVisibility(View.GONE);
                takingTerm_section.setVisibility(View.GONE);
                takingTime_section.setVisibility(View.GONE);
            }
        }
    }


    /** 약 복용타입 버튼 리스너 **/
    class TakingTypeBtnOnClickListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.dayOption) {
                takingTerm_section.setVisibility(View.GONE);
                takingDay_section.setVisibility(VISIBLE);
            } else {
                takingDay_section.setVisibility(View.GONE);
                takingTerm_section.setVisibility(VISIBLE);
            }
        }
    }


    /** 복용간격 버튼 리스너 **/
    /*
    class TakingTermBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();


            switch (view.getId()) {
                case R.id.term_everyday :
                    term_everyday.setChecked(true);
                    break ;
                case R.id.term_2 :
                    term2.setChecked(true);
                    break ;
                case R.id.term_3 :
                    term3.setChecked(true);
                    break ;
                case R.id.term_4 :
                    term4.setChecked(true);
                    break ;
                case R.id.term_5 :
                    term5.setChecked(true);
                    break ;
                case R.id.term_6 :
                    term6.setChecked(true);
                    break ;
                case R.id.term_week :
                    term_week.setChecked(true);
                    break ;
            }
        }

        void setAllBtnUnChecked() {
            term_everyday.setChecked(false);
            term2.setChecked(false);
            term3.setChecked(false);
            term4.setChecked(false);
            term5.setChecked(false);
            term6.setChecked(false);
            term_week.setChecked(false);
        }
    }
    */

    /** 약 복용횟수 스피너 리스너 **/
    class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view, int position, long id){
            takingTimeListAdapter.clear();
            for (int i = 0; i <= position; i++) {
                takingTimeListAdapter.addItem("09:00");
            }
        }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

        }
    }


    class LinearLayoutManagerWrapper extends LinearLayoutManager {
        LinearLayoutManagerWrapper(Context context) {
            super(context);
        }

        LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }



    /** 약 등록 API 호출 **/
    public void registerMedicine(MedicineDTO medicineDTO){

        // token
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String accessToken = "Bearer " + pref.getString("accessToken", "");
        apiService.register_medicine(accessToken, medicineDTO).enqueue(new Callback<MedicineResultDTO>() {

            @Override
            public void onResponse(Call<MedicineResultDTO> call, Response<MedicineResultDTO> response) {

                Log.e("error","code : " + response.code());

                if(response.isSuccessful()){


                    Log.i("info", "통신성공(register medicine)");
                    finish();

                    // 약 등록 후 > 우리집 구급상자 페이지로 이동
                    mainActivity.finish();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("tabPosition", 1);
                    startActivity(intent);

                }

                else if(response.code() == 400){
                    Toast.makeText(getApplicationContext(), "입력란을 확인하세요!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MedicineResultDTO> call, Throwable t) {
                Log.e("error", "통신실패(register medicine)" + t.getCause());
            }
        });

    }
}
