package org.park_easy_backend.controller;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ParkingSpaceDTO;
import org.park_easy_backend.repository.ParkingSpaceRepository;
import org.park_easy_backend.service.ParkingSpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingSpaceController {
    private final ParkingSpaceService parkingSpaceService;

    @GetMapping
    public String saveForm(){return "save";}

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute ParkingSpaceDTO parkingSpaceDTO){
        try{
            parkingSpaceService.save(parkingSpaceDTO);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error within parking procedure");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllParkingSpaces(){
        try{
            List<ParkingSpaceDTO> result = parkingSpaceService.findAll();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error within parking retrieval");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSingleParkingSpace(@RequestParam Long Id){
        ParkingSpaceDTO result = parkingSpaceService.findParkingSpaceEntityById(Id);
        if(result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error within parking retrieval");
    }

    @GetMapping("/getAllInCity")
    public ResponseEntity<?> getAllParkingSpacesInCity(@RequestParam Long Id){
        try{
            List<ParkingSpaceDTO> result = parkingSpaceService.findAllByCityId(Id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error within parking space retriebal");
        }
    }
}
