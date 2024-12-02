package org.park_easy_backend.controller;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.LocationDTO;
import org.park_easy_backend.service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public ResponseEntity<?> getSingleLocation(@ModelAttribute LocationDTO locationDTO, HttpSession session){
        LocationDTO mapResult = mapService.findByCity(locationDTO);
        if(mapResult != null){
            session.setAttribute("city", mapResult.getCity());
            session.setAttribute("lat", mapResult.getLat());
            session.setAttribute("lng", mapResult.getLng());
            return ResponseEntity.ok(mapResult);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error within map procedure");
    }
}
