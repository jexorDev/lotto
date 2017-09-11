package com.lotto.controller;

import com.lotto.helpers.TotalSummaryHelper;
import com.lotto.models.*;

import java.util.Date;

import javassist.expr.Cast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class PaymentController {
    @RequestMapping(value="/payment/create", method=POST )
    @ResponseBody
    public String create(@RequestParam(value="payerId") String payerId, @RequestParam(value="recipientId") String recipientId, Date paymentDate, Integer amount) {
        Payment payment;

        User payer = userDao.findOne(Long.parseLong(payerId));
        User recipient = userDao.findOne(Long.parseLong(recipientId));
        try {
            payment = new Payment(payer, recipient, paymentDate, amount);
            paymentDao.save(payment);

            TotalSummaryHelper summaryHelper = new TotalSummaryHelper(totalSummaryDao);
            summaryHelper.createOrUpdateTotalSummary(payer, recipient, amount);
        }
        catch (Exception ex) {
            return "Error creating the payment: " + ex.toString();
        }
        return "Payment successfully created! (id = " + payment.getId() + ")";
    }

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TotalSummaryDao totalSummaryDao;
}

