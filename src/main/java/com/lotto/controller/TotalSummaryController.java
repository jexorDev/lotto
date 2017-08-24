package com.lotto.controller;

import com.lotto.helpers.TotalSummaryHelper;
import com.lotto.models.TotalSummaryDao;
import com.lotto.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the TotalSummaryDao class.
 *
 * @author des
 */
@Controller
public class TotalSummaryController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * /update  --> Update the total summary record.  Create a new one if it does not exist
     *
     * */

    @RequestMapping("/totalSummary/update")
    @ResponseBody
    public String update(User userA, User userB, int amount) {

        TotalSummaryHelper totalSummaryHelper = new TotalSummaryHelper(totalSummaryDao);
        return totalSummaryHelper.createOrUpdateTotalSummary(userA, userB, amount);
    }

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private TotalSummaryDao totalSummaryDao;

}