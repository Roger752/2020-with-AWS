package com.codesample.story.story;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.codesample.story.databinding.ActivityStoryBinding;
import com.codesample.story.dto.Story;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.codesample.story.network.HttpClient;

import com.codesample.story.Adapter.StoryAdapter;

public class StoryActivity extends AppCompatActivity implements  StoryAdapter.OnItemClickListener{

    private ActivityStoryBinding binding;
    private StoryAdapter storyAdapter;
    private String url = "http://172.26.1.189:4007";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        try {
            GetTask getTask = new GetTask();
            Map<String, String> params = new HashMap<String, String>();
            //params.put("rr_rider", LoginId); //파라미터 값 넘길때 없으면 안써도 됨
            getTask.execute(params);
        } catch (Exception e) {

        }
    }

    @Override
    public void onItemClick(View v, int position, Story s) {

        // 누르면 스토리 제목 설명 을 보여주고 버튼으로 시작하기만들어줌
        Intent intent = new Intent(this, InfoActivity.class);

        intent.putExtra("s_num", s.getS_num());
        intent.putExtra("title", s.getS_title());
        intent.putExtra("content", s.getS_content());

        startActivity(intent);
    }


    public class GetTask extends AsyncTask<Map<String, String>, Integer, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) {
            // Http 요청 준비 작업
            //URL은 현재 자기 아이피번호를 입력해야합니다.
            HttpClient.Builder http = new HttpClient.Builder("GET", url + "/storymake/bbb");   // 학교
            //HttpClient.Builder http = new HttpClient.Builder("GET", "http://192.168.0.6:4007/storymake/bbb");    // 자취방
            // Parameter 를 전송한다.(없으면 빼도됨)
            http.addAllParameters(maps[0]);
            //Http 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 본문 가져오기
            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("로그: ",s);

            try {
                //JSONArray jsonArray = new JSONArray(s);
                Log.d("마이리포트", s + "\n");
                //StringBuffer sb = new StringBuffer();
                String tempData = s; //json넘어온 데이터

                Gson gson = new Gson();
                ArrayList<Story> itemList = new ArrayList<>();
                Story[] items = gson.fromJson(tempData, Story[].class); //배열선언

                // spinner 설정
                ArrayList<String> area = new ArrayList<String>();
                area.add("지역");

                for (Story item : items) {
                    Log.i("지역:", item.getArea_name());
                    if(area.contains(item.getArea_name()) == false) {
                        area.add(item.getArea_name());
                    }

                    itemList.add(item); //RidingRecord dto에 알아서 들어감
                }

                ArrayAdapter adapter = new ArrayAdapter<>(StoryActivity.this, android.R.layout.simple_spinner_item, area);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.spinner.setAdapter(adapter);

                // 기본값 설정
                ArrayAdapter myAdap = (ArrayAdapter) binding.spinner.getAdapter();
                String want = "지역";
                int spinnerPosition = myAdap.getPosition(want);
                //Log.i("지역은 몇번째", spinnerPosition);
                binding.spinner.setSelection(spinnerPosition, false);

                Log.i("t1", String.valueOf(binding.spinner.getSelectedItem()));

                if(String.valueOf(binding.spinner.getSelectedItem()).equals("지역")) {
                    // 스토리 목록 Adapter 설정
                    storyAdapter = new StoryAdapter((ArrayList<Story>) itemList, StoryActivity.this::onItemClick);
                    binding.recyclerView.setAdapter(storyAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(StoryActivity.this));
                }


                // spinner 이벤트 생성
                binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        // 포지션 숫자의 지역이름이랑 똑같은 이름의 지역을 가지고 있는 story객체만 표시
                        if(position != 0) {
                            String choice_area = String.valueOf(binding.spinner.getSelectedItem());
                            Log.i("선택", choice_area);   // 성공

                            int count = 0;
                            int f_count = 0;
                            ArrayList<Story> choice_list = new ArrayList<>();
                            for (Story item : items) {
                                Log.i("카운트:", "foreach 횟수 : " + String.valueOf(f_count));
                                f_count = f_count + 1;
                                if(item.getArea_name().equals(choice_area)) {
                                    Log.i("카운트", "if 횟수 : " + String.valueOf(count));
                                    count = count+ 1;
                                    Log.i("test", "안쪽에 들어온 횟수는ㄴㄴㄴㄴㄴ");
                                    choice_list.add(item); //RidingRecord dto에 알아서 들어감
                                }
                            }
                            // 스토리 목록 Adapter 설정
                            storyAdapter = new StoryAdapter((ArrayList<Story>) choice_list, StoryActivity.this::onItemClick);
                            binding.recyclerView.setAdapter(storyAdapter);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(StoryActivity.this));
                        }
                        else {
                            // 스토리 목록 Adapter 설정
                            storyAdapter = new StoryAdapter((ArrayList<Story>) itemList, StoryActivity.this::onItemClick);
                            binding.recyclerView.setAdapter(storyAdapter);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(StoryActivity.this));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
                // spinner 설정 끝

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
