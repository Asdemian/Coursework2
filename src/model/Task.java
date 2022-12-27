package model;

import exception.IncorrectTaskParameterException;
import util.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private static int idGenerator = 1;
    private final int id;
    private String title;
    private String description;
    private Type type;
    private LocalDateTime dateTime;
    private Repeat repeat;


    public Task(String title, String description,
                Type type, LocalDateTime dateTime,
                Repeat repeat) throws IncorrectTaskParameterException {
        id = idGenerator++;
        setTitle(title);
        setDescription(description);
        setType(type);
        setDateTime(dateTime);
        setRepeat(repeat);
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IncorrectTaskParameterException {
        if (title == null || title.isEmpty()) {
            throw new IncorrectTaskParameterException("заголовок задачи");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IncorrectTaskParameterException {
        if (description == null || description.isEmpty()) {
            throw new IncorrectTaskParameterException("описание задачи");
        }
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        if (type == null) {
            type = Type.PERSONAL;
        }
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) throws IncorrectTaskParameterException {
        if (dateTime == null) {
            throw new IncorrectTaskParameterException("Дата и время задачи");
        }
        this.dateTime = dateTime;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        if (repeat == null) {
            repeat = new OneTime();
        }
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id:").append(id).append("\n")
                .append("НАЗВАНИЕ: ").append(title).append("\n")
                .append("ОПИСАНИЕ: ").append(description).append("\n")
                .append("ТИП: ").append(type == Type.PERSONAL ? "личная" : "рабочая").append("\n")
                .append("ДАТА и ВРЕМЯ: ").append(dateTime.format(Constant.DATE_TIME_FORMATTER)).append("\n")
                .append("ПОВТОРЯЕМОСТЬ: ").append(repeat).append("\n");
        return stringBuilder.toString();
    }
}
