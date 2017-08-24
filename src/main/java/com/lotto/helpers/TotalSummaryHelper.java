package com.lotto.helpers;

import com.lotto.models.TotalSummary;
import com.lotto.models.TotalSummaryDao;
import com.lotto.models.User;
import org.springframework.beans.factory.annotation.Autowired;

public class TotalSummaryHelper {

    public TotalSummaryHelper(TotalSummaryDao totalSummaryDao) {
        this.totalSummaryDao = totalSummaryDao;
    }

    public String createOrUpdateTotalSummary(User userA, User userB, int amount) {

        String successString;

        try
        {
            TotalSummary perspectiveUser = totalSummaryDao.findByUserAAndUserB(userA, userB);
            TotalSummary inRelationToUser = totalSummaryDao.findByUserAAndUserB(userB, userA);

            if (perspectiveUser != null) {
                //record exists, so update it
                perspectiveUser.setAmount(perspectiveUser.getAmount() + amount);
                inRelationToUser.setAmount(inRelationToUser.getAmount() + (amount *-1));

                successString = "updated";
            }
            else
            {
                //record does not exist, need to create a new one
                perspectiveUser = new TotalSummary(userA , userB, amount);
                inRelationToUser = new TotalSummary(userB, userA, amount * -1);

                successString =  "created";
            }

            totalSummaryDao.save(perspectiveUser);
            totalSummaryDao.save(inRelationToUser);

        }
        catch (Exception ex) {
            return "Error: " + ex.toString();
        }

        return "Record successfully " + successString;
    }

    @Autowired
    private TotalSummaryDao totalSummaryDao;

    public TotalSummaryDao getTotalSummaryDao() {
        return totalSummaryDao;
    }

    public void setTotalSummaryDao(TotalSummaryDao totalSummaryDao) {
        this.totalSummaryDao = totalSummaryDao;
    }
}
