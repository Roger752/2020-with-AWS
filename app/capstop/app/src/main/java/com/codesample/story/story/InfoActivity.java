package com.codesample.story.story;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codesample.story.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {

    private ActivityInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        int s_num = intent.getIntExtra("s_num",0);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        binding.storyTitle.setText(title);
        binding.storyContent.setText(content);

        binding.start.setOnClickListener(v -> {
            Intent next_intent = new Intent(this, StartActivity.class);
            Log.i("시작할 스토리", String.valueOf(s_num));
            next_intent.putExtra("s_num", s_num);
            startActivity(next_intent);
        });
    }
}
