package tehama.society.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tehama.society.model.Payment;
import tehama.society.model.Result;
import tehama.society.model.dto.PaymentDTO;
import tehama.society.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Payment> makeCoursePayment(@RequestBody PaymentDTO paymentDTO, @PathVariable long studentId, @PathVariable long courseId) {
        Payment coursePayment = paymentService.makeCoursePayment(paymentDTO.getAmount(), studentId, courseId);
        return new ResponseEntity<>(coursePayment, HttpStatus.CREATED);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> allPayments = paymentService.getAllPayments();
        return new ResponseEntity<>(allPayments, HttpStatus.OK);
    }


    @PutMapping("/update/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment, @PathVariable long paymentId) {
        Payment updatePayment = paymentService.updatePayment(payment.getAmount(), paymentId);
        return new ResponseEntity<>(updatePayment, HttpStatus.OK);
    }


    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> findPayment(@PathVariable long paymentId) {
        Payment payment = paymentService.findPayment(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }


    @GetMapping("/delete/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable long paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
