package model;

import java.time.LocalDateTime;

public class Monthly implements Repeat{
    @Override
    public LocalDateTime nextTime(LocalDateTime currentDateTime) {
        return currentDateTime.plusMonths(1);
    }

    @Override
    public String toString() {
        return "ежемесячная";
    }
}
