package model;

import java.time.LocalDateTime;

public class Yearly implements Repeat{
    @Override
    public LocalDateTime nextTime(LocalDateTime currentDateTime) {
        return currentDateTime.plusYears(1);
    }

    @Override
    public String toString() {
        return "ежегодная";
    }
}
