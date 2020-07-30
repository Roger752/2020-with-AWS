package com.codesample.story.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.codesample.story.Adapter.StoryAdapter;
import com.codesample.story.databinding.ActivityLoginBinding;
import com.codesample.story.dto.Story;
import com.codesample.story.network.HttpClient;
import com.codesample.story.story.StartActivity;
import com.codesample.story.story.StoryActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String url = "http://172.26.1.189:4007";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.loginButton.setOnClickListener(v->{
            try {
                String id = binding.id.getText().toString();
                String pw = binding.password.getText().toString();

                Log.i("aaaa", id);
                Log.i("aaaa", pw);

                loginActivity.GetTask getTask = new loginActivity.GetTask();
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id); //파라미터 값 넘길때 없으면 안써도 됨
                params.put("pw", pw);
                getTask.execute(params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    public class GetTask extends AsyncTask<Map<String, String>, Integer, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) {
            // Http 요청 준비 작업
            //URL은 현재 자기 아이피번호를 입력해야합니다.
            HttpClient.Builder http = new HttpClient.Builder("GET", url + "/app_login/login");   // 학교
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
            Log.d("로그 : ", String.valueOf(s.length()));

                //JSONArray jsonArray = new JSONArray(s);
                Log.d("마이리포트", s + "\n");
                //StringBuffer sb = new StringBuffer();
                String tempData = s; //json넘어온 데이터

                Gson gson = new Gson();
                ArrayList<String> itemList = new ArrayList<>();
                String[] items = gson.fromJson(tempData, String[].class); //배열선언

                for(String data : items) {
                    Log.i("데이타", data);
                }
        }
    }
}
