package exception;

public class TaskNotFoundExceptio extends Exception {
    private final int id;

    public TaskNotFoundExceptio(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Задача с id = "+id+ " не найдена";
    }
}
