package com.techbank.account_cmd.api.dto;

import com.techbank.account_commom.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountResponse extends BaseResponse {
    private String accountId;

    public OpenAccountResponse(String message, String accountId) {
        super(message);
        this.accountId = accountId;
    }
}
