package com.eNach_Cancellation.Controller;

import com.eNach_Cancellation.Model.CommonResponse;
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
        CommonResponse commonResponse=new CommonResponse();
        try {
            statusRequest.validate();
            commonResponse.setMsg(service.statusRequest(statusRequest));
            return ResponseEntity.ok(commonResponse);
        } catch (IllegalArgumentException e) {
            commonResponse.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(commonResponse);
        }
        catch (Exception e) {
            commonResponse.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(commonResponse);
        }
    }
}
