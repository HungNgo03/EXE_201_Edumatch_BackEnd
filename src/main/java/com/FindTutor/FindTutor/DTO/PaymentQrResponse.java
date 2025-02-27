package com.FindTutor.FindTutor.DTO;

import lombok.Data;

@Data
public class PaymentQrResponse {
    private String urlQr;
    private double totalPrice;
    private Long registerId;

    public PaymentQrResponse(String urlQr, double totalPrice,Long registerId) {
        this.urlQr = urlQr;
        this.totalPrice = totalPrice;
        this.registerId = registerId;
    }
}
