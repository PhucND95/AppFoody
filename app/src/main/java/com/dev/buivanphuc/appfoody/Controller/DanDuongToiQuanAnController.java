package com.dev.buivanphuc.appfoody.Controller;

import android.util.Log;

import com.dev.buivanphuc.appfoody.Model.DowloadPolyLine;
import com.dev.buivanphuc.appfoody.Model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongToiQuanAnController {

    ParserPolyline parserPolyline;
    DowloadPolyLine dowloadPolyLine;

    public DanDuongToiQuanAnController() {

    }

    public void HienThiDanDuongToiQuanAn(GoogleMap googleMap,String duongdan){
        parserPolyline = new ParserPolyline();
        dowloadPolyLine = new DowloadPolyLine();
        dowloadPolyLine.execute(duongdan);

        try {
            String dataJSON = dowloadPolyLine.get();
            List<LatLng> latLngList = parserPolyline.LayDanhSachToaDo(dataJSON);

            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng toado : latLngList){
                polylineOptions.add(toado);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
