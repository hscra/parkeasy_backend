package org.park_easy_backend.controller;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.LocationDTO;
import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.park_easy_backend.service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @GetMapping("/save")
    public String saveForm(){ return "save"; }

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute LocationDTO locationDTO){
        try{
            mapService.save(locationDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error within map procedure");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/getAllLocations")
    public ResponseEntity<?> getAllLocations(){
        try{
            List<LocationDTO> mapResult = mapService.findAll();
            return ResponseEntity.ok(mapResult);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error within map retrieval");
        }
    }
  
    @GetMapping("/get")
    public ResponseEntity<?> getSingleLocation(@RequestParam Long Id){
        LocationDTO mapResult = mapService.findCityEntityById(Id);
        if(mapResult != null){
            return ResponseEntity.ok(mapResult);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error within map procedure");
    }

    @GetMapping("/getAllCitySpaces")
    public ResponseEntity<?> getAllCitySpaces(@RequestParam Long Id){
        LocationDTO result = mapService.findCityEntityById(Id);
        if(result != null){
            List<ParkingSpaceEntity> parkingSpaces = result.getParkingSpaces();
            if(parkingSpaces != null) {
                return ResponseEntity.ok(result);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error within map procedure");
    }
}
