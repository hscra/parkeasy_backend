package org.park_easy_backend.controller;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ReservationDTO;
import org.park_easy_backend.service.HistoricalReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/historical_reservation")
@RequiredArgsConstructor
public class HistoricalReservationController {
    private final HistoricalReservationService historicalReservationService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllReservations() {
        try {
            List<ReservationDTO> result = historicalReservationService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("getAll error");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSingleReservation(@RequestParam Long Id) {
        ReservationDTO result = historicalReservationService.findReservationEntityById(Id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while getting single reservation by id");

    }

    @GetMapping("/getAllInCity")
    public ResponseEntity<?> getAllReservationsInCity(@RequestParam Long Id) {
        try {
            List<ReservationDTO> result = historicalReservationService.findAllReservationsByCityId(Id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("getAllInCity error");
        }
    }

    @GetMapping("/getUserReservations")
    public ResponseEntity<?> getAllUserReservations(@RequestParam Long Id) {
        try {
            List<ReservationDTO> result = historicalReservationService.findAllReservationsByUserId(Id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("get all user reservations error");
        }
    }

}
