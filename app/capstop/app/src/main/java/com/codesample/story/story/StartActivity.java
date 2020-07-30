package com.codesample.story.story;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codesample.story.R;
import com.codesample.story.databinding.ActivityStartBinding;
import com.codesample.story.dto.startStory;
import com.google.gson.Gson;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.codesample.story.network.HttpClient;

public class StartActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    private ActivityStartBinding binding;

    private String url = "http://172.26.1.189:4007";  // 학교

    //private String url = "http://192.168.0.6:4007/uploads/";    // 자취방

    private String fileName;

    Bitmap bitmap;

//    private String[] question;  // 문제 텍스트 배열
//    private String[] hintText;  // 힌트 텍스트 배열
//    private String[] hintPicture;   // 힌트 사진 파일이름 배열
//    private String[] narration; // 나레이션 파일 이름 배열
    private int s_length; // 스토리 문화재 갯수

    private ArrayList<startStory> startStoryList; // 스토리당 문제, 힌트, 나레이션을 가지고있는 startStory 리스트

    private startStory[] startStorys;

    private MapView mapView;
    private MapPOIItem marker;
    private ViewGroup mapViewContainer;

    // 위도, 경도 변수
    private double myLatitude;
    private double myLongitude;

    private MapCircle cultureRange;

    // 원의 위도, 경도 변수 및 원의 반지름 범위
    private double circleLatitude;
    private double circleLongitude;
    private int circleRadius;

    private Double[] array_circleLatitudes;
    private Double[] array_circleLongitudes;
    private Integer[] array_circleRadiuses;


    private ArrayList<Double> circleLatitudes = new ArrayList<Double>();
    private ArrayList<Double> circleLongitudes = new ArrayList<Double>();
    private ArrayList<Integer> circleRadiuses = new ArrayList<Integer>();

    private MediaPlayer player = new MediaPlayer();


    int music_count = 0;

    // 스토리 순서대로 듣기위한 배열 1=현재들어야할 스토리, 0=아직 안들어도되는 스토리
    int now_c_num[];

    // 해당 스토리 문화재 갯수만큼 생성 => 듣기전=0, 듣기후=1
    int check[];

    // 버튼 관련 변수 및 리스너
    private int buttonHintCheck = 0;
    private View.OnClickListener clickListener = v -> {
        if(v.getId() == R.id.buttonHint) {
            Log.i("힌트버튼1 test : ", binding.textViewHint.getText().toString());
            if(buttonHintCheck % 2 == 0) {
                binding.buttonHint.setText("힌트 감추기");
                if(binding.textViewHint.getText().toString() != "") {
                    Log.i("힌트 텍스트 : ", binding.textViewHint.getText().toString());
                    binding.textViewHint.setVisibility(View.VISIBLE);
                }
                else {
                    Log.i("힌트 이미지 : ", binding.textViewHint.getText().toString());
                    binding.imageView.setVisibility(View.VISIBLE);
                }
                buttonHintCheck++;
                Log.i("숫자", "짝수2 : " + String.valueOf(buttonHintCheck));
            }
            else {
                binding.buttonHint.setText("힌트 보기");
                binding.textViewHint.setVisibility(View.GONE);
                binding.imageView.setVisibility(View.GONE);

                buttonHintCheck++;
                Log.i("숫자", "홀수2 : " + String.valueOf(buttonHintCheck));
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent =  getIntent();
        int s_num = intent.getIntExtra("s_num", 0);
        Log.i("시작된 스토리", String.valueOf(s_num));

        try {
            GetTask getTask = new GetTask();
            Map<String, String> params = new HashMap<String, String>();
            params.put("s_num", String.valueOf(s_num)); //파라미터 값 넘길때 없으면 안써도 됨
            getTask.execute(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*binding.button.setOnClickListener(v->{
            Intent intent2 = new Intent(this, MapActivity.class);
            startActivity(intent2);
        });*/

        //권한 체크
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한 없을경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        }

        mapView = new MapView(this);
        // POIItem 이벤트 리스너 등록
        mapView.setPOIItemEventListener(this);
        // CurrentLocation 이벤트 리스너 등록
        mapView.setCurrentLocationEventListener(this);

        // 버튼 리스너 등록
        binding.buttonHint.setOnClickListener(clickListener);

        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        // 지도 화면에 등록
        mapViewContainer.addView(mapView);

        // 트래킹 모드 On, 나침반 기능 Off (현재 내 위치 추적 가능 On/Off)
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setShowCurrentLocationMarker(true);
        Log.i("CHECK", "mapView.getMapPointBounds : " + mapView.getPOIItems());

        marker = new MapPOIItem();
        Log.i("SHOWING", mapView.isShowingCurrentLocationMarker() + "");

        Log.i("onCreate", String.valueOf(s_length));
    }


    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        Log.i("MAIN", "marker : " + marker);

        Log.i("asdf", String.valueOf(s_length));

        if(marker == null) {
            // 정문 35.895198, 128.623674
            //본관 35.896730, 128.620985
            //연서관 35.896782, 128.622694
            // 정보관 35.895456, 128.621604
            marker.setMapPoint(mapPoint);
            //marker.setMapPoint(MapPoint.mapPointWithGeoCoord(35.896730, 128.620985));   // 현재 위치로 바꿔주기
            marker.setItemName("Me");
            marker.setTag(0);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

            // 마커 등록
            mapView.addPOIItem(marker);
        }
        else if(marker != null && marker.getTag() == 0) {
            // 기존 마커 제거
            mapView.removePOIItem(marker);

            marker.setMapPoint(mapPoint);
            //marker.setMapPoint(MapPoint.mapPointWithGeoCoord(35.896730, 128.620985));   // 현재 위치로 바꿔주기
            marker.setItemName("Me");
            marker.setTag(0);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

            // 마커 등록
            mapView.addPOIItem(marker);
        }

        //Log.i("MAIN", "getAlpha : " + marker.getAlpha() + ", getRotation : " + marker.getRotation()
        //        + ", getMapPoint : " + marker.getMapPoint().getMapPointGeoCoord().latitude + ", " + marker.getMapPoint().getMapPointGeoCoord().longitude);


        myLatitude = marker.getMapPoint().getMapPointGeoCoord().latitude;
        myLongitude = marker.getMapPoint().getMapPointGeoCoord().longitude;

        /*for(int q =0; q<s_length; q++) {
            Log.i("진입 1  : ", String.valueOf(circleLatitudes[q]));
            Log.i("진입 1  : ", String.valueOf(circleLongitudes[q]));
            Log.i("진입 1  : ", String.valueOf(circleRadiuses[q]));
        }*/


        array_circleLatitudes = circleLatitudes.toArray(new Double[circleLatitudes.size()]);
        array_circleLongitudes = circleLongitudes.toArray(new Double[circleLongitudes.size()]);
        array_circleRadiuses = circleRadiuses.toArray(new Integer[circleRadiuses.size()]);

        Log.i("MAIN", Math.pow( Double.parseDouble(String.format("%,5f", (myLatitude - circleLatitude))), 2) + ", "
                + Math.pow( Double.parseDouble(String.format("%,5f", (myLongitude - circleLongitude))), 2));
        for(int i=0; i<s_length; i++) {
            Log.i("진입", Math.pow( Double.parseDouble(String.format("%,5f", (myLatitude - array_circleLatitudes[i]))), 2) + ", "
                    + Math.pow( Double.parseDouble(String.format("%,5f", (myLongitude - array_circleLongitudes[i]))), 2));
            if((Math.pow(array_circleRadiuses[i] * 0.00001, 2) >= Math.pow( Double.parseDouble(String.format("%,5f", (myLatitude - array_circleLatitudes[i]))), 2)
                    + Math.pow( Double.parseDouble(String.format("%,5f", (myLongitude - array_circleLongitudes[i]))), 2)) && now_c_num[i] == 1) {
                Log.i("진입", "햇어요");
                Toast.makeText(this, "원 진입", Toast.LENGTH_SHORT).show();


                Log.i("음악쪽", "music_count" + String.valueOf(music_count));

                try {
                    if(player.isPlaying()) {
                        music_count = music_count + 1;
                        Log.i("음악쪽", "음악중");
                        Log.i("음악쪽", "i : " + String.valueOf(i));
                        return;
                    }
                    else if(music_count == 0 && check[i] == 0) {

                        player.setDataSource(url + "/uploads/" + startStoryList.get(i).getS_file());

                        Log.i("음악쪽", "음악시작");
                        player.prepare();

                        player.start();
                    }

                    else {
                        player.release();
                        music_count = 0;
                        now_c_num[i+1] = 1;
                        player = new MediaPlayer();
                        Log.i("음악쪽", "음악끝");
                        Log.i("음악쪽", String.valueOf(music_count));

                        Toast.makeText(this, (i+1) + "번째 문화재 클리어", Toast.LENGTH_SHORT).show();

                        check[i] = 1;

                        binding.textViewProblem.setText(startStoryList.get(i+1).getQ_content());

                        // 만약에 파일이 있다면 힌트이미지를 띄워준다.

                        Log.i("힌트 텍스트 : ", String.valueOf(startStoryList.get(i+1).getH_content()));
                        Log.i("다운로드 할 파일이름 : ", String.valueOf(startStoryList.get(i+1).getH_file()));

                        if((check[(s_length-1)]) == 1) {
                            Intent in = new Intent(this, EndActivity.class);
                            startActivity(in);
                        }

                        if(startStoryList.get(i+1).getH_file().equals("없음")) {
                            binding.textViewHint.setText(startStoryList.get(i+1).getH_content());
                            binding.textViewHint.setVisibility(View.GONE);
                        }
                        else {
                            FileTask fileTask = new FileTask();

                            fileTask.execute(url + "/uploads/" +startStoryList.get(i+1).getH_file());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.i("진입else : ", "Circle doesn't Contain me");
            }


        }


    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    // POIItem 리스너
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public class GetTask extends AsyncTask<Map<String, String>, Integer, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) {
            // Http 요청 준비 작업
            //URL은 현재 자기 아이피번호를 입력해야합니다.
            HttpClient.Builder http = new HttpClient.Builder("GET", url + "/storymake/storyStart");    // 학교
            //HttpClient.Builder http = new HttpClient.Builder("GET", "http://192.168.0.6:4007/storymake/storyStart");    // 자취방
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
                startStoryList = new ArrayList<>();
                startStory[] items = gson.fromJson(tempData, startStory[].class); //배열선언

                for(startStory asdf : items) { ///////////////////////////////////////////////////////////////////   이건 array인데 밑에서 ArrayList에 넣엇다가 다시 array로 바꿔주는 불필요한 작업 수행중
                    Log.i("문화재1", asdf.getC_name());
                    Log.i("파일1", asdf.getH_file());
                }

                Log.i("길이", String.valueOf(items.length));
                s_length = items.length;

                // 해당 나레이션을 들엇는지 체크하기 위한 배열 => 듣기전 0, 듣기후 1
                check = new int[s_length];

                // 스토리 순서대로 듣기위한 배열 1=현재들어야할 스토리, 0=아직 안들어도되는 스토리
                now_c_num = new int[s_length];
                now_c_num[0] = 1;
//                question = new String[items.length];
//                hintText = new String[items.length];
//                hintPicture = new String[items.length];
//                narration = new String[items.length];


                for (startStory item : items) {
                    Log.i("문화재", item.getC_name());
                    Log.i("파일", item.getH_file());

                    startStoryList.add(item);
                }

                //////////////// startStory객체들이 들어있는 arrayList를 array로 바꿈 => 이유가 머였지
                ///////////////////////////////startStorys = startStoryList.toArray(new startStory[startStoryList.size()]);


                //Log.i("리스트", startStoryList.get(0).getC_name());

                // 첫번째 문화재 문제 설정
                binding.textViewProblem.setText(startStoryList.get(0).getQ_content());

                Log.i("힌트", "ㅁㄴㅇㄹ : " + startStoryList.get(0).getH_file());
                Log.i("힌트", "ㅁㄴㅇㄹ : " + startStoryList.get(0).getH_content());

                // 1번째 문화재 힌트 설정
                if(startStoryList.get(0).getH_file().equals("없음")) {
                    binding.textViewHint.setText(startStoryList.get(0).getH_content());
                    binding.textViewHint.setVisibility(View.GONE);
                }
                else {
                    FileTask fileTask = new FileTask();
                    fileTask.execute(url + "/uploads/" +startStoryList.get(0).getH_file());
                }

                for(int i = 0; i<s_length; i++) {

                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName("Default Marker");
                    marker.setTag(0);
                    /////// startStoryList라는 ArrayList를 Array로 바꿨는데 아래쪽에서 startStoryList를 다시 사용한다? =>  아주 개판이다.
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(startStoryList.get(i).getC_latitude()), Double.valueOf(startStoryList.get(i).getC_longitude())));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    mapView.addPOIItem(marker);

                    // 지도에 Circle 생성
                    cultureRange = new MapCircle(
                            MapPoint.mapPointWithGeoCoord(Double.valueOf(startStoryList.get(i).getC_latitude()), Double.valueOf(startStoryList.get(i).getC_longitude())), // center
                            15, // radius
                            Color.argb(128, 255, 0, 0), // strokeColor
                            Color.argb(128, 0, 255, 0) // fillColor
                    );
                    cultureRange.setTag(1234);

                    // 지도에 원형 생성
                    mapView.addCircle(cultureRange);

                    Log.i("MAIN", "CircleCenterPoint : " + cultureRange.getCenter().getMapPointGeoCoord().latitude + ", "
                            + cultureRange.getCenter().getMapPointGeoCoord().longitude);

                    circleLatitude = cultureRange.getCenter().getMapPointGeoCoord().latitude;
                    circleLongitude = cultureRange.getCenter().getMapPointGeoCoord().longitude;

                    Log.i("확인", "원 위도" + String.valueOf(circleLatitude));
                    Log.i("확인", "원 경도" + String.valueOf(circleLongitude));

                    //circleLatitudes = new double[s_length];
                    circleLatitudes.add(circleLatitude);
                    //Log.i("확인", i + "번째 배열 위도 값 : " + String.valueOf(circleLatitudes[i]));

                    //circleLongitudes = new double[s_length];
                    circleLongitudes.add(circleLongitude);
                    //Log.i("확인", i + "번째 배열 경도 값 : " +  String.valueOf(circleLongitudes[i]));

                    circleRadius = cultureRange.getRadius();
                    Log.i("확인", "circleRadius : " + String.valueOf(circleRadius));
                    //circleRadiuses = new int[s_length];
                    circleRadiuses.add(circleRadius);
                    //Log.i("확인", i + "번째 배열 반지름 값 : " + String.valueOf(circleRadiuses[i]));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class FileTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL fileURL = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)fileURL.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bitmap = BitmapFactory.decodeStream(is);
            }
             catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            binding.imageView.setImageBitmap(bitmap);
            binding.imageView.setVisibility(View.GONE);
            binding.textViewHint.setText("");
        }
    }

}
