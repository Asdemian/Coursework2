import exception.IncorrectTaskParameterException;
import exception.TaskNotFoundExceptio;
import model.*;
import service.TaskService;
import util.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        label:
        while (true) {
            printMenu();
            System.out.print("Выберите пункт меню: ");
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                switch (menu) {
                    case 1:
                        addTask(scanner);
                        break;
                    case 2:
                        removeTask(scanner);
                        break;
                    case 3:
                        printTaskByDay(scanner);
                        break;
                    case 0:
                        break label;
                }
            } else {
                scanner.next();
                System.out.println("Выберите пункт меню из списка!");
            }
        }
    }

    private static void printTaskByDay(Scanner scanner) {
        do {
            System.out.print("Введите дату в формате \""+LocalDate.now().format(Constant.DATE_FORMATTER)+"\": ");
            if (scanner.hasNext(DATE_PATTERN)) {
                LocalDate day = parseDate(scanner.next(DATE_PATTERN));
                if (day == null) {
                    System.out.println("Некорректный формат даты");
                    continue;
                }
                Collection<Task> tasksByDay = TaskService.getTasksByDay(day);
                if (tasksByDay.isEmpty()) {
                    System.out.println("Задачи на " + day.format(Constant.DATE_FORMATTER) + " нету ");
                } else {
                    System.out.println("Задачи на " + day.format(Constant.DATE_FORMATTER) + " : ");
                    for (Task task : tasksByDay) {
                        System.out.println(task);
                    }
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);

    }

    private static void removeTask(Scanner scanner) {
        try {
            do {
                System.out.print("Введите id задачи: ");
                if (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    TaskService.removeById(id);
                    System.out.println("Задача с id = "+id+" удалена");
                    break;
                } else {
                    scanner.next();
                }
            } while (true);
        } catch (TaskNotFoundExceptio e) {
            System.out.println(e.getMessage());
        }
    }


    private static void addTask(Scanner scanner) {
        try {
            System.out.print("Введите название задачи: ");
            String title = scanner.next();
            System.out.print("Введите описание задачи: ");
            String description = scanner.next();
            Type type = inputType(scanner);
            LocalDateTime dateTime = inputDateTime(scanner);
            Repeat repeat = inputRepeat(scanner);
            Task task = new Task(title, description, type, dateTime, repeat);
            TaskService.add(task);
            System.out.println("Задача  добавлена");
            System.out.println(task);
        } catch (IncorrectTaskParameterException e) {
            System.out.println(e.getMessage());
        }

    }

    private static Type inputType(Scanner scanner) {

        Type type;
        do {
            System.out.print("Введите тип задачи: \n1. Личная\n" +
                    "2. Рабочая\n Тип задачи: ");
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                if (number != 1 && number != 2) {
                    System.out.println("Введите цифру 1 или 2");
                    continue;
                }
                if (number == 1) {
                    type = Type.PERSONAL;
                } else {
                    type = Type.WORK;
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
        return type;
        // todo
    }

    private static LocalDateTime inputDateTime(Scanner scanner) {
        LocalDateTime dateTime;
        do {
            System.out.print("Введите дату и время задачи в формате \""+LocalDateTime.now().format(Constant.DATE_TIME_FORMATTER)+"\":  ");
            if (scanner.hasNext(DATE_TIME_PATTERN)) {
                dateTime = parseDateTime(scanner.next(DATE_TIME_PATTERN));
                if (dateTime == null) {
                    System.out.println("Некорректный формат даты и времени");
                    continue;
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
        return dateTime;
    }

    private static LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, Constant.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }

    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, Constant.DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }

    }

    private static Repeat inputRepeat(Scanner scanner) {

        Repeat repeat;
        do {
            System.out.print("Введите тип повторяемости задачи: \n1. Однократная\n" +
                    "2. Ежедневная\n3. Еженедельная\n4. Ежемесечная\n" +
                    "5. Годовая\n Тип повторяемости: ");
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                if (number < 1 || number > 5) {
                    System.out.println("Введите цифру от 1 до 5");
                    continue;
                }
                switch (number) {
                    default:
                    case 1:
                        repeat = new OneTime();
                        break;
                    case 2:
                        repeat = new Daily();
                        break;
                    case 3:
                        repeat = new Weekly();
                        break;
                    case 4:
                        repeat = new Monthly();
                        break;
                    case 5:
                        repeat = new Yearly();
                        break;
                }

                break;
            } else {
                scanner.next();
            }
        } while (true);
        return repeat;
    }

    private static void printMenu() {
        System.out.println(
                "1. Добавить задачу\n2. Удалить задачу\n" +
                        "3. Получить задачу на указанный день\n0. Выход");
    }
}