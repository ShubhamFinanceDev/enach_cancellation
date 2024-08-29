package com.eNach_Cancellation.Controller;

import com.eNach_Cancellation.Model.SaveStatusRequest;
import com.eNach_Cancellation.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private Service service;

    @PostMapping("/save-status")
    public ResponseEntity<?> saveStatus(@RequestBody SaveStatusRequest statusRequest){
        try {
            return ResponseEntity.ok(service.statusRequest(statusRequest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
