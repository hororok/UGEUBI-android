package duksung.android.hororok.ugeubi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import duksung.android.hororok.ugeubi.search.Search_Result;

public class Search_fragment extends Fragment {


    ImageButton search_btn;

    ToggleButton dur_none, dur_option1, dur_option2, dur_option3, dur_option4, dur_option5, dur_option6, dur_option7, dur_option8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_search, container, false);

        search_btn = rootView.findViewById(R.id.search_btn);

        // DUR 유형 버튼
        dur_none = rootView.findViewById(R.id.none);
        dur_option1 = rootView.findViewById(R.id.durOption1);
        dur_option2 = rootView.findViewById(R.id.durOption2);
        dur_option3 = rootView.findViewById(R.id.durOption3);
        dur_option4 = rootView.findViewById(R.id.durOption4);
        dur_option5 = rootView.findViewById(R.id.durOption5);
        dur_option6 = rootView.findViewById(R.id.durOption6);
        dur_option7 = rootView.findViewById(R.id.durOption7);
        dur_option8 = rootView.findViewById(R.id.durOption8);


        // DUR 유형 버튼리스너 설정
        DURTypeBtnOnClickListener durTypeBtnOnClickListener = new DURTypeBtnOnClickListener();
        dur_none.setOnClickListener(durTypeBtnOnClickListener);
        dur_option1.setOnClickListener(durTypeBtnOnClickListener);
        dur_option2.setOnClickListener(durTypeBtnOnClickListener);
        dur_option3.setOnClickListener(durTypeBtnOnClickListener);
        dur_option4.setOnClickListener(durTypeBtnOnClickListener);
        dur_option5.setOnClickListener(durTypeBtnOnClickListener);
        dur_option6.setOnClickListener(durTypeBtnOnClickListener);
        dur_option7.setOnClickListener(durTypeBtnOnClickListener);
        dur_option8.setOnClickListener(durTypeBtnOnClickListener);



        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search_Result.class);

                startActivity(intent);
            }
        });





        return rootView;
    }



    /** DUR유형 버튼 리스너 **/
    class DURTypeBtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            setAllBtnUnChecked();


            switch (view.getId()) {
                case R.id.none :
                    dur_none.setChecked(true);
                    break ;
                case R.id.durOption1 :
                    dur_option1.setChecked(true);
                    break ;
                case R.id.durOption2 :
                    dur_option2.setChecked(true);
                    break ;
                case R.id.durOption3 :
                    dur_option3.setChecked(true);
                    break ;
                case R.id.durOption4 :
                    dur_option4.setChecked(true);
                    break ;
                case R.id.durOption5 :
                    dur_option5.setChecked(true);
                    break ;
                case R.id.durOption6 :
                    dur_option6.setChecked(true);
                    break ;
                case R.id.durOption7 :
                    dur_option7.setChecked(true);
                    break ;
                case R.id.durOption8 :
                    dur_option8.setChecked(true);
                    break ;
            }
        }

        void setAllBtnUnChecked() {
            dur_none.setChecked(false);
            dur_option1.setChecked(false);
            dur_option2.setChecked(false);
            dur_option3.setChecked(false);
            dur_option4.setChecked(false);
            dur_option5.setChecked(false);
            dur_option6.setChecked(false);
            dur_option7.setChecked(false);
            dur_option8.setChecked(false);
        }
    }

}