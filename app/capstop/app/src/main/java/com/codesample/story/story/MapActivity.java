package com.codesample.story.story;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codesample.story.R;
import com.codesample.story.databinding.ActivityMapBinding;


import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.POIItemEventListener {
    private ActivityMapBinding binding;
    private MapView mapView;
    private MapPOIItem marker;
    private ViewGroup mapViewContainer;

    // 스토리 문제 관련 변수
    private String problem = "문제 1\n유관순 열사가 우리나라를 되찾기 위해\n투신을 다한 장소를 찾아가보세요";

    // 위도, 경도 변수
    private double myLatitude;
    private double myLongitude;

    private MapCircle cultureRange;

    // 원의 위도, 경도 변수 및 원의 반지름 범위
    private double circleLatitude;
    private double circleLongitude;
    private int circleRadius;

    // 버튼 관련 변수 및 리스너
    private int buttonHintCheck = 0;
    private View.OnClickListener clickListener = v -> {
        if(v.getId() == R.id.buttonHint) {
            if(buttonHintCheck % 2 == 0) {
                binding.textViewHint.setText("힌트");
                buttonHintCheck++;
            }
            else {
                binding.textViewHint.setText("");
                buttonHintCheck++;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        // 스토리 관련 문제
        binding.textViewProblem.setText(problem);


        // 트래킹 모드 On, 나침반 기능 Off (현재 내 위치 추적 가능 On/Off)
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setShowCurrentLocationMarker(true);
        Log.i("CHECK", "mapView.getMapPointBounds : " + mapView.getPOIItems());

        marker = new MapPOIItem();
        Log.i("SHOWING", mapView.isShowingCurrentLocationMarker() + "");

        // Generate Kakao Hash Key
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }*/

        // 지도에 Circle 생성
        cultureRange = new MapCircle(
                MapPoint.mapPointWithGeoCoord(35.896401, 128.620465), // center
                25, // radius
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
        circleRadius = cultureRange.getRadius();

        /*MapCircle circle2 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(35.896280, 128.622049), // center
                1000, // radius
                Color.argb(128, 255, 0, 0), // strokeColor
                Color.argb(128, 255, 255, 0) // fillColor
        );
        circle2.setTag(5678);
        mapView.addCircle(circle2);

        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        MapPointBounds[] mapPointBoundsArray = { circle1.getBound(), circle2.getBound() };
        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
        int padding = 50; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));*/

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        Log.i("MAIN", "marker : " + marker);

        if(marker == null) {
            marker.setMapPoint(mapPoint);
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

        // x^2 + y^2 = r^2
        /*Log.i("MAIN", "myLatitude : " + Math.floor((myLatitude * 10000) / 10000.0) + " ,myLongitude : " + String.format("%,5f",myLongitude)
                + " ,circleLatitude : " + circleLatitude + " ,circleLongitude : " + circleLongitude/10000.0
                + " ,circleRadius : " + circleRadius);*/
        Log.i("MAIN", Math.pow( Double.parseDouble(String.format("%,5f", (myLatitude - circleLatitude))), 2) + ", "
                + Math.pow( Double.parseDouble(String.format("%,5f", (myLongitude - circleLongitude))), 2));
        if(Math.pow(circleRadius * 0.00001, 2) >= Math.pow( Double.parseDouble(String.format("%,5f", (myLatitude - circleLatitude))), 2)
                + Math.pow( Double.parseDouble(String.format("%,5f", (myLongitude - circleLongitude))), 2) ) {
            Toast.makeText(this, "원 진입", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i("MAIN", "Circle doesn't Contain me");
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
}

