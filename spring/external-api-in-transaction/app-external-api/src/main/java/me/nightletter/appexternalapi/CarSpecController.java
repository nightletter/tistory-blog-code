package me.nightletter.appexternalapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CarSpecController {

    @GetMapping("/car/specs")
    public ResponseEntity specs() throws InterruptedException {

        Thread.sleep(30_000);

        Map<String, Object> data = new HashMap<>();
        data.put("STATUS", "200");
        data.put("RESPONSE", "true");
        data.put("CARVENDER", "기아");
        data.put("CARNAME", "올 뉴 카니발");
        data.put("SUBMODEL", "2.2 디젤 7인승 아웃도어 리무진 VIP A/T");
        data.put("UID", "2AMEABD10H");
        data.put("CARYEAR", "2017");
        data.put("DRIVE", "FF");
        data.put("FUEL", "디젤");
        data.put("PRICE", "44910000");
        data.put("CC", "2199");
        data.put("MISSION", "A/T");
        data.put("CARURL", "201518_allnew_carnival.png");
        data.put("VIN", "KNACC81CGJA123456");
        data.put("RESULT", "SUCCESS");
        data.put("ERRMSG", "");
        data.put("FRONTTIRE", "235/55R19");
        data.put("REARTIRE", "235/55R19");
        data.put("EOILLITER", "6.3");
        data.put("WIPER", "D:650;P:450;R:전용");
        data.put("SEATS", "7");

        List<Map<String, String>> batteryList = new ArrayList<>();
        Map<String, String> battery = new HashMap<>();
        battery.put("BRAND", "");
        battery.put("MODEL", "90L");
        battery.put("TYPE", "일반");
        batteryList.add(battery);

        data.put("BATTERYLIST", batteryList);
        data.put("FUELECO", "10.3");
        data.put("FUELTANK", "80");

        return ok(data);
    }
}
