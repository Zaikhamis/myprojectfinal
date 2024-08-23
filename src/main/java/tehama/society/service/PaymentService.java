package tehama.society.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tehama.society.model.*;
import tehama.society.repository.PaymentRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final CourseService courseService;

    public Payment makeCoursePayment(double amount, long studentId, long courseId) {
        Payment payment = new Payment();
        Student student = (Student) userService.findUserByUserId(studentId);
        Course course = courseService.findCourse(courseId);
        payment.setStudent(student);
        payment.setCourse(course);
        payment.setAmount(amount);
        payment.setStatus("Paid");
        payment.setDate(new Date());
        return paymentRepository.save(payment);
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public Payment updatePayment(double amount, long paymentId) {
        Payment updatePayment = findPayment(paymentId);
        updatePayment.setAmount(amount);
        paymentRepository.save(updatePayment);
        return updatePayment;
    }

    public Payment findPayment(long paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId);
        if (userService == null) {
            throw new RuntimeException("Payment not found");
        }

        return payment;
    }


    public void deletePayment(long paymentId) {
        paymentRepository.deleteById(paymentId);
    }




}
