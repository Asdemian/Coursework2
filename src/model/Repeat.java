package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Repeat {
    LocalDateTime nextTime(LocalDateTime currentDateTime);


}
