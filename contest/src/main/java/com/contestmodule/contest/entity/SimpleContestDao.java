package com.contestmodule.contest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SimpleContestDao {

    String getName();
    String getDescription();
    LocalDate getStartDate();
    LocalDate getEndDate();
    BigDecimal getEntryFee();
    String getContestLevel();
    String getWinningAward();

}
