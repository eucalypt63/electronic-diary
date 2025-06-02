package com.example.postgresql.controller.Education;

import com.example.postgresql.controller.RequiredRoles;
import com.example.postgresql.model.Education.EducationInfo.Region;
import com.example.postgresql.model.Education.EducationInfo.Settlement;
import com.example.postgresql.service.Education.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressControl {

    @Autowired
    private AddressService addressService;

    //Получить все регионы
    @GetMapping("/getRegions")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<Region>> getRegions() {
        List<Region> regions = addressService.getAllRegion();

        if (regions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(regions);
    }

    //Получить все Settlements определённого региона
    @GetMapping("/getSettlements")
    @RequiredRoles({"Main admin", "Local admin", "Administration", "Teacher", "School student", "Parent"})
    @ResponseBody
    public ResponseEntity<List<Settlement>> getSettlements(@RequestParam Long region) {
        List<Settlement> settlements = addressService.findSettlementByRegionId(region);

        if (settlements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(settlements);
        }

        return ResponseEntity.ok(settlements);
    }
}
