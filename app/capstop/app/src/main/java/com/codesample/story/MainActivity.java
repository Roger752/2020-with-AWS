package com.codesample.story;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.codesample.story.databinding.ActivityMainBinding;
import com.codesample.story.login.loginActivity;
import com.codesample.story.story.StoryActivity;


// 안드로이드에서 네트워크 작업시 주의 사항
// 1."반드시, 백그라운드 스레드로 작업해야한다."
//      시간이 많이 걸리기 때문에 메인스레드에서 할 수 없게 되어 있다.
// 2. 백그라운드 스레드에서는 메인화면에 접근 할 수 없다.
//      그래서 대신에 handler를 사용 => 백그라운드와 메인 스레드 사이의 통신(중계)를 담당
//          => 즉, 백그라운드 스레드는 핸들러에게 "네가 대신 화면 제어를 해줘" 라고 sendMessage() 메서드를 통해 요청
//              => 그러면, 핸들러는 이 메시지를 받아 처리할 작업을 대신해주게 되는 것
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.story.setOnClickListener(v-> {
            startActivity(new Intent(this, StoryActivity.class));
        });

        binding.bingo.setOnClickListener(v-> {
            startActivity(new Intent(this, loginActivity.class));
        });

    }
}

