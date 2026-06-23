package com.wanted.crud.payment.controller;

import com.wanted.crud.course.model.dto.CourseDTO;
import com.wanted.crud.payment.model.dto.PaymentDTO;
import com.wanted.crud.payment.model.service.PaymentService;

import java.util.List;

public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) { this.service = service;}

    public List<PaymentDTO> selectAllUsers() { return service.selectAllPayment();}

    public Long createPayment(Long paymentAmount, String paymentMethod, boolean status ,Long studentId, Long courseId) {


        /* comment.
         *   타이틀과 설명은 논리적으로 묶여야 하는 데이터이다.
         *   authorId 는 나중에 로그인을 한 유저 객체에서 추출해서 넣어주어야 한다.*/
        PaymentDTO newPayment = new PaymentDTO(paymentAmount, paymentMethod, status, studentId, courseId);
        return service.savePayment(newPayment);
    }

    // 강좌별 누적 총금액 계산
    public List<PaymentDTO> getRevenueByCourse() {
        return service.getRevenueByCourse();
    }


}
