package model;

import java.time.LocalDateTime;

public class OneTime implements Repeat{

    @Override
    public LocalDateTime nextTime(LocalDateTime currentDateTime) {
        return null;
    }

    @Override
    public String toString() {
        return "одноразовая";
    }
}
