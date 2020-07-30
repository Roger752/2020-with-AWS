package com.codesample.story.dto;

public class startStory {

    // 힌트내용 or 파일이름, story_only의 순서, 문제내용, 문화재이름
    private int c_num;
    private String c_name;
    private int q_num;
    private String q_content;
    private int h_num;
    private String h_content;
    private String h_file;
    private int s_order;
    private String c_latitude;
    private String c_longitude;
    private String s_file;

    public startStory() {
    }

    public int getC_num() {
        return c_num;
    }

    public void setC_num(int c_num) {
        this.c_num = c_num;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getQ_num() {
        return q_num;
    }

    public void setQ_num(int q_num) {
        this.q_num = q_num;
    }

    public String getQ_content() {
        return q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public int getH_num() {
        return h_num;
    }

    public void setH_num(int h_num) {
        this.h_num = h_num;
    }

    public String getH_content() {
        return h_content;
    }

    public void setH_content(String h_content) {
        this.h_content = h_content;
    }

    public String getH_file() {
        return h_file;
    }

    public void setH_file(String h_file) {
        this.h_file = h_file;
    }

    public int getS_order() {
        return s_order;
    }

    public void setS_order(int s_order) {
        this.s_order = s_order;
    }

    public String getS_file() {
        return s_file;
    }

    public void setS_file(String s_file) {
        this.s_file = s_file;
    }

    public String getC_latitude() {
        return c_latitude;
    }

    public void setC_latitude(String c_latitude) {
        this.c_latitude = c_latitude;
    }

    public String getC_longitude() {
        return c_longitude;
    }

    public void setC_longitude(String c_longitude) {
        this.c_longitude = c_longitude;
    }
}
