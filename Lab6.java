import java.util.ArrayList;
import java.util.List;


public class Lab6 {
    public static void main(String[] args) {
        System.out.println("=== Тестування власної колекції MyArrayList ===\n");

        // 1. Тест конструктора порожнього
        MyArrayList<Ammunition> myList1 = new MyArrayList<>();
        System.out.println("1. Створено порожній список. Ємність: " + myList1.getCurrentCapacity());
        
        // Додаємо 16 елементів, щоб викликати розширення (15 -> +30%)
        System.out.println("Додаємо 16 шоломів...");
        for (int i = 0; i < 16; i++) {
            myList1.add(new Helmet("Шолом " + i, 1.5, 10 + i));
        }
        System.out.println("Розмір списку: " + myList1.size());
        System.out.println("Нова ємність (має бути 15 + 30% = 19): " + myList1.getCurrentCapacity());


        // 2. Тест конструктора з одним елементом
        System.out.println("\n2. Створення списку з одним елементом:");
        Ammunition sword = new Weapon("Екскалібур", 5.0, 1000.0);
        MyArrayList<Ammunition> myList2 = new MyArrayList<>(sword);
        System.out.println("Елемент: " + myList2.get(0));


        // 3. Тест конструктора зі стандартною колекцією
        System.out.println("\n3. Створення списку зі стандартної колекції (ArrayList):");
        List<Ammunition> standardList = new ArrayList<>();
        standardList.add(new Armor("Латна кіраса", 12.0, 500.0));
        standardList.add(new Helmet("Топхельм", 3.0, 200.0));

        MyArrayList<Ammunition> myList3 = new MyArrayList<>(standardList);
        
        // Використання ітератора (foreach працює завдяки інтерфейсу Iterable/List)
        System.out.println("Вміст myList3:");
        for (Ammunition item : myList3) {
            System.out.println(" - " + item);
        }

        // 4. Тест видалення
        System.out.println("\n4. Видалення першого елементу з myList3:");
        myList3.remove(0);
        System.out.println("Вміст після видалення:");
        System.out.println(myList3.get(0)); // Тепер Топхельм має бути першим
    }
}