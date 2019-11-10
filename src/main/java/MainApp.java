
import boxes.Apple;
import boxes.Box;
import boxes.Orange;
import com.geekbrains.erth.entities.Task;
import com.geekbrains.erth.exceptions.SelfTransmitException;
import com.geekbrains.erth.services.TaskService;

public class MainApp {

    public static void main(String[] args) {
        
        //Тест исключений ТаскМенеджера
        TaskService taskService = new TaskService();
        taskService.addTask(new Task(1, "Таск А"));
        taskService.addTask(new Task(1, "Таск Б"));
        taskService.addTask(new Task(10, "Таск Б"));
        taskService.deleteTask(2);
        Task t = new Task(3, "Таск для теста удаления объекта");
        taskService.addTask(t);
        System.out.println("-----");
        taskService.printTaskList();
        taskService.deleteTask(t);
        System.out.println("-----");
        taskService.printTaskList();                
        System.out.println("-----");
        
        Box<Apple> boxApples = new Box<>();
        Box<Apple> boxApples2 = new Box<>();
        Box<Orange> boxOranges = new Box<>();
                
        boxApples.addFruit(new Apple());
        boxApples2.addFruit(new Apple());
        boxApples.addFruits(new Apple(), 5);
        boxApples.addFruits(new Apple(),new Apple(),new Apple(),new Apple());
        
        boxOranges.addFruit(new Orange());
        boxOranges.addFruit(new Orange());
        boxOranges.addFruits(new Orange(), 2);
        
        System.out.println("boxApples: " + boxApples.getWeight());
        System.out.println("boxOranges: " + boxOranges.getWeight());
        System.out.println(boxApples.compare(boxOranges));
        System.out.println(boxApples.compare(boxApples));
        System.out.println("---");
        /*компилятор не позволяет такую конструкцию сделать
        try {
            boxApples.transmit(boxOranges);
            System.out.println(boxApples.getWeight());
            System.out.println(boxOranges.getWeight());  
        } catch (SelfTransmitException e){
            System.out.println(e);
        }*/
        
        System.out.println("---");
        try {
            boxApples.transmit(boxApples);
            System.out.println("boxApples: " + boxApples.getWeight());
        } catch (SelfTransmitException e){
            System.out.println(e);
        }
        
        System.out.println("---");
        try {
            System.out.println("boxApples: " + boxApples.getWeight());
            System.out.println("boxApples2: " + boxApples2.getWeight());
            System.out.println("transmitting from 1 to 2");
            boxApples.transmit(boxApples2);
            System.out.println("boxApples: " + boxApples.getWeight());
            System.out.println("boxApples2: " + boxApples2.getWeight());
        } catch (SelfTransmitException e){
            System.out.println(e);
        }
                
        System.out.println("---");
        try {
            System.out.println("boxApples: " + boxApples.getWeight());
            System.out.println("boxApples2: " + boxApples2.getWeight());
            System.out.println("transmitting from 2 to 1");
            boxApples2.transmit(boxApples);
            System.out.println("boxApples: " + boxApples.getWeight());
            System.out.println("boxApples2: " + boxApples2.getWeight());
        } catch (SelfTransmitException e){
            System.out.println(e);
        }
        
        System.out.println("---");
        try {
            System.out.println("boxApples: " + boxApples.getWeight());
            System.out.println("boxApples2: " + boxApples2.getWeight());
            System.out.println("transmitting from 2 to 1");
            boxApples2.transmit(boxApples);
            System.out.println("boxApples: " + boxApples.getWeight());
            System.out.println("boxApples2: " + boxApples2.getWeight());
        } catch (SelfTransmitException e){
            System.out.println(e);
        }
    }

}
