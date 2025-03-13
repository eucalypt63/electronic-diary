package com.example.postgresql.controller.Education;

import com.example.postgresql.model.Users.Education.Region;
import com.example.postgresql.model.Users.Education.Settlement;
import com.example.postgresql.service.Education.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AddressControl {

    @Autowired
    private AddressService addressService;


    //Получить все регионы
    @GetMapping("/getRegions")
    @ResponseBody
    public ResponseEntity<List<Region>> getRegions() {
        List<Region> regions = addressService.getAllRegion();

        if (regions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(regions);
        }

        return ResponseEntity.ok(regions);
    }

    //Получить все Settlements определённого региона
    @GetMapping("/getSettlements")
    @ResponseBody
    public ResponseEntity<List<Settlement>> getSettlements(@RequestParam Long region) {
        List<Settlement> settlements = addressService.getAllSettlement()
                .stream()
                .filter(settlement -> settlement.getRegion().getId().equals(region))
                .collect(Collectors.toList());

        if (settlements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(settlements);
        }

        return ResponseEntity.ok(settlements);
    }
}
