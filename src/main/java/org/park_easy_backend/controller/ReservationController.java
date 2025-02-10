package org.park_easy_backend.controller;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ReservationDTO;
import org.park_easy_backend.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody ReservationDTO reservationDTO) {
        try {
            reservationService.makeReservation(reservationDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Parking space is occupied");
        } catch(Exception ee) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during reservation creation");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllReservations() {
        try {
            List<ReservationDTO> result = reservationService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("getAll error");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSingleReservation(@RequestParam Long Id) {
        ReservationDTO result = reservationService.findReservationEntityById(Id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while getting single reservation by id");

    }

    @GetMapping("/getAllInCity")
    public ResponseEntity<?> getAllReservationsInCity(@RequestParam Long Id) {
        try {
            List<ReservationDTO> result = reservationService.findAllReservationsByCityId(Id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("getAllInCity error");
        }
    }

    @GetMapping("/getUserReservations")
    public ResponseEntity<?> getAllUserReservations(@RequestParam Long Id) {
        try {
            List<ReservationDTO> result = reservationService.findAllReservationsByUserId(Id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("get all user reservations error");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeReservationById(@RequestParam Long Id) {
        try {
            reservationService.removeReservation(Id);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing reservation");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/completeReservationPayment")
    public ResponseEntity<?> completeReservationPayment(@RequestParam Long Id) {
        try {
            reservationService.updatePaymentStatus(Id, 1);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("getAllInCity error");
        }
    }

    @PostMapping("/failReservationPayment")
    public ResponseEntity<?> failReservationPayment(@RequestParam Long Id) {
        try {
            reservationService.updatePaymentStatus(Id, 0);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("getAllInCity error");
        }
    }
}
