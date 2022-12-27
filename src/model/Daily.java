package model;

import java.time.LocalDateTime;

public class Daily implements Repeat{
    @Override
    public LocalDateTime nextTime(LocalDateTime currentDateTime) {
        return currentDateTime.plusDays(1);


    }

    @Override
    public String toString() {
        return "ежедневная";
    }
}
