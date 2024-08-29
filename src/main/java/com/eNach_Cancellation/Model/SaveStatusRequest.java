package com.eNach_Cancellation.Model;

import lombok.Data;
import java.util.Objects;

@Data
public class SaveStatusRequest {

    private String applicationNo;
    private String loanNo;
    private String cancelCause;

    public boolean hasNullFields() {
        return Objects.isNull(loanNo) ||
                Objects.isNull(applicationNo) ||
                Objects.isNull(cancelCause);
    }

    public void validate() {
        if (hasNullFields()) {
            throw new IllegalArgumentException("One or more fields are null or empty.");
        }
    }
}
