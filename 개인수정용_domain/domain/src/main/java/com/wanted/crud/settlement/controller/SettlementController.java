package com.wanted.crud.settlement.controller;

import com.wanted.crud.settlement.model.dto.SettlementDTO;
import com.wanted.crud.settlement.model.service.SettlementService;
import com.wanted.crud.user.model.dto.UserDTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class SettlementController {
    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    public SettlementDTO findSettlementById(long id){
        return settlementService.findbyId(id);
    }

    // 강사 누적 총수익 계산
    public List<SettlementDTO> getRevenueByInstructor() {
        return settlementService.getRevenueByInstructor();
    }

    public Long saveSettlement(SettlementDTO settlementDTO){
        return settlementService. saveSettlement(settlementDTO);
    }

    // 정산 조회
    public List<SettlementDTO> viewAllSettlement(){
        return settlementService.viewAllSettlement();
    }

    // 미정산 조회
    public List<SettlementDTO> viewWaitSettlement() {
        return settlementService.viewWaitSettlement();
    }

    public void paymentToSettlement(Timestamp now) {
        settlementService.paymentToSettlement(now);
    }

    public boolean processFullSettlement() {
        return settlementService.processFullSettlement();
    }

}
