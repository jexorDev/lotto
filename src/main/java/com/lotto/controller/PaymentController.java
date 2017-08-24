package com.lotto.controller;

import com.lotto.models.PaymentDao;
import com.lotto.models.User;
import com.lotto.models.Payment;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class PaymentController {
    // TODO: value of request mapping
    @RequestMapping(value="", method=POST )
    @ResponseBody
    public String create(@RequestParam(value="payerID") User payerID, @RequestParam(value="recipientID") User recipientID, Date paymentDate, Integer amount) {
        Payment payment;
        try {
            payment = new Payment(payerID, recipientID, paymentDate, amount);
            paymentDao.save(payment);
        }
        catch (Exception ex) {
            return "Error creating the payment: " + ex.toString();
        }
        return "Payment successfully created! (id = " + payment.getId() + ")";
    }

    @Autowired
    private PaymentDao paymentDao;
}

