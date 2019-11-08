
import com.geekbrains.erth.lesson4.entities.Task;
import com.geekbrains.erth.lesson4.exceptions.MyArrayDataException;
import com.geekbrains.erth.lesson4.exceptions.MyArraySizeException;
import com.geekbrains.erth.lesson4.services.TaskService;

public class MainApp {

    public static void main(String[] args) {
        
        //Тест исключений ТаскМенеджера
        TaskService taskService = new TaskService();        
        taskService.addTask(new Task(1, "Таск А"));
        taskService.addTask(new Task(1, "Таск Б"));
        taskService.deleteTask(2);
        
        //Тест исключений матриц
        String[][] strMatrix = {{"1", "1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}};
        try {
            int sum = sumArrayElements(strMatrix);
            System.out.println(sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e);
        }
        
        String[][] strMatrix2 = {{"1", "asd", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}};

        try {
            int sum = sumArrayElements(strMatrix2);
            System.out.println(sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e);
        }
        
        String[][] strMatrix3 = {{"1", "asd", "1", "1"}, {"1", "1", "1"}, {"1", "1", "1", "1"}, {"1", "1", "1", "1"}};

        try {
            int sum = sumArrayElements(strMatrix3);
            System.out.println(sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e);
        }
    }

    public static int sumArrayElements(String[][] matrix) {
        final int MATRIX_SIZE = 4;
        if (matrix.length != MATRIX_SIZE) {
            throw new MyArraySizeException("Размер матрицы не "+MATRIX_SIZE+"х"+MATRIX_SIZE);
        }
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != MATRIX_SIZE) {
                throw new MyArraySizeException("Размер матрицы не "+MATRIX_SIZE+"х"+MATRIX_SIZE);
            }
        }

        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }
        return sum;
    }
}
