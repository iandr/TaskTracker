
import com.geekbrains.erth.lesson3.entities.Task;
import com.geekbrains.erth.lesson3.services.TaskService;

public class MainApp {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        
        taskService.addTask(new Task(1, "Таск А"));
        taskService.addTask(new Task(1, "Таск Б"));
        taskService.addTask(new Task(3, "Таск Й"));
        taskService.addTask(new Task(2, "Таск Ц"));
        taskService.printTaskList();
        System.out.println("--------");
        taskService.deleteTask(1);
        taskService.printTaskList();
        System.out.println("--------");
        taskService.addTask(new Task(20, "Таск 3"));
        
    }
}
