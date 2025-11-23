import java.util.*;

/**
 * Типізована колекція, що реалізує інтерфейс List.
 * Внутрішня структура: Масив.
 * Початковий розмір: 15.
 * Коефіцієнт зростання: 30%.
 * @param <E> Тип елементів, що зберігаються (має наслідувати Ammunition згідно завдання)
 */
public class MyArrayList<E> implements List<E> {

    // Початкова ємність згідно варіанту 0
    private static final int INITIAL_CAPACITY = 15;
    
    // Внутрішній масив для зберігання даних
    private Object[] elementData;
    
    // Поточна кількість елементів
    private int size;

    /**
     * Конструктор 1: Порожній.
     * Ініціалізує масив на 15 елементів.
     */
    public MyArrayList() {
        this.elementData = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Конструктор 2: З одним об'єктом.
     * @param element Елемент, який додається одразу.
     */
    public MyArrayList(E element) {
        this();
        add(element);
    }

    /**
     * Конструктор 3: Зі стандартною колекцією.
     * @param collection Колекція, елементи якої треба додати.
     */
    public MyArrayList(Collection<? extends E> collection) {
        this(); // Спочатку створюємо масив на 15
        addAll(collection);
    }

    /**
     * Метод збільшення ємності масиву.
     * Реалізує логіку збільшення на 30%.
     */
    private void increaseCapacity() {
        int oldCapacity = elementData.length;
        // Збільшення на 30% (множення на 1.3 або додавання 30%)
        int newCapacity = oldCapacity + (int)(oldCapacity * 0.3);
        
        // Якщо 30% це 0 (при малих розмірах), збільшуємо хоча б на 1
        if (newCapacity == oldCapacity) {
            newCapacity = oldCapacity + 1;
        }
        
        // Копіюємо старі дані в новий, більший масив
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        // Якщо масив заповнений, збільшуємо його
        if (size == elementData.length) {
            increaseCapacity();
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, elementData[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (size == elementData.length) {
            increaseCapacity();
        }
        // Зсув елементів вправо
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            // Зсув елементів вліво
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        // Зануляємо останній елемент для Garbage Collector
        elementData[--size] = null; 

        return oldValue;
    }
    
    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // --- Реалізація інших методів List (базово або Unsupported для економії місця) ---

    @Override
    public boolean contains(Object o) { return indexOf(o) >= 0; }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() { return currentIndex < size; }
            @Override
            public E next() { return get(currentIndex++); }
        };
    }

    @Override
    public Object[] toArray() { return Arrays.copyOf(elementData, size); }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) if (!contains(e)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) if (add(e)) modified = true;
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
             while (contains(e)) {
                 remove(e);
                 modified = true;
             }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) elementData[i] = null;
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) if (Objects.equals(o, elementData[i])) return i;
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) if (Objects.equals(o, elementData[i])) return i;
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() { return listIterator(0); }

    @Override
    public ListIterator<E> listIterator(int index) {
        // Спрощена реалізація, щоб код компілювався
        return new ListIterator<E>() {
            int cursor = index;
            @Override public boolean hasNext() { return cursor < size; }
            @Override public E next() { return get(cursor++); }
            @Override public boolean hasPrevious() { return cursor > 0; }
            @Override public E previous() { return get(--cursor); }
            @Override public int nextIndex() { return cursor; }
            @Override public int previousIndex() { return cursor - 1; }
            @Override public void remove() { MyArrayList.this.remove(cursor - 1); cursor--; }
            @Override public void set(E e) { MyArrayList.this.set(cursor - 1, e); }
            @Override public void add(E e) { MyArrayList.this.add(cursor++, e); }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Метод subList не реалізовано в рамках цієї лабораторної");
    }
    
    // Метод для перевірки поточної ємності (для демонстрації лабораторної)
    public int getCurrentCapacity() {
        return elementData.length;
    }
}