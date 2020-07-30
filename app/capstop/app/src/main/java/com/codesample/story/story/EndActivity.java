package com.codesample.story.story;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codesample.story.databinding.ActivityEndBinding;

public class EndActivity extends AppCompatActivity {

    private ActivityEndBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
