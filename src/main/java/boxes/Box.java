package boxes;

import com.geekbrains.erth.exceptions.SelfTransmitException;
import java.util.ArrayList;

public class Box<T extends Fruit> {

    private ArrayList<T> fruitArr;

    public Box() {
        fruitArr = new ArrayList();
    }

    public ArrayList<? extends T> getFruitArr() {
        return fruitArr;
    }

    public void addFruit(T fruit) {
        fruitArr.add(fruit);
    }

    public void addFruits(T fruit, int count) {
        for (int i = 0; i < count; i++) {
            T tempFruit = (T) new Fruit();
            addFruit(tempFruit);
        }
    }

    public void addFruits(ArrayList<? extends T> fruitArr) {
        for (int i = 0; i < fruitArr.size(); i++) {
            addFruit(fruitArr.get(i));
        }
    }
    
    public void addFruits(T... fruit) {
        for (int i = 0; i < fruit.length; i++) {
            addFruit(fruit[i]);
        }
    }

    public int getFruitCount() {
        return fruitArr.size();
    }

    public float getWeight() {
        if (fruitArr.size() == 0) {
            return 0.0f;
        }
        Fruit fruit = (Fruit) fruitArr.get(0);
        return fruitArr.size() * fruit.getWeight();
    }

    public boolean compare(Box<? extends Fruit> box) {
        return Math.abs(box.getWeight() - this.getWeight()) < 0.0001f;
    }

    private T getFirstFruit() {
        if (fruitArr.size() > 0) {
            return (T) fruitArr.get(0);
        } else {
            return null;
        }
    }

    public void transmit(Box<T> box) {
        if (this.equals(box)){
            throw new SelfTransmitException("Нельзя пересыпать фрукты из себя в себя");
        }
        
        box.addFruits(getFruitArr());
        fruitArr.clear();
    }

    
}
