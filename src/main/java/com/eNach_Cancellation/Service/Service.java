package com.eNach_Cancellation.Service;

import com.eNach_Cancellation.Model.CommonResponse;
import com.eNach_Cancellation.Model.SaveStatusRequest;

public interface Service {

    String statusRequest(SaveStatusRequest statusRequest) throws Exception;
}
