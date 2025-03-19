package util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class MyArrayList<T> implements MyList<T> {
    private T[] array; // null
    private int cursor; // по умолчанию = 0 (по типу данных)

    // Методы, расширяющие функционал массива

    // Подавляю предупреждение компилятора о непроверяемом приведении типа
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        // Стирание типов. Невозможно создать объект типа Т
        this.array = (T[]) new Object[10];
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(T[] array) {

        if (array == null || array.length == 0) {
            this.array = (T[]) new Object[10];
        } else {
            this.array = (T[]) new Object[array.length * 2];
            addAll(array);
        }
    }

    // Добавление в массив одного элемента
    public void add(T value) {

        if (cursor == array.length) {
            // Расширить внутренний массив перед добавлением нового значения
            expandArray();
        }
        array[cursor] = value;
        cursor++;
    }

    // Динамическое расширение массива
    @SuppressWarnings("unchecked")
    private void expandArray() {
        System.out.println("Расширяем внутренний массив! Курсор равен = " + cursor);

        T[] newArray = (T[]) new Object[array.length * 2];

        for (int i = 0; i < cursor; i++) {
            newArray[i] = array[i];
        }
        //Перебрасываем ссылку. Переменная array хранит ссылку на "новый" массив
        array = newArray;
    }

    // Добавление в массив нескольких элементов
    // int... - это называется СИКВЕНЦИЯ (последовательность)
    public void addAll(T... numbers) {

        // Перебираю все значения. Для каждого вызываю метод add()
        for (int i = 0; i < numbers.length; i++) {
            add(numbers[i]);
        }
    }

    // Возвращает строковое представление массива
    @Override
    public String toString() {

        if (cursor == 0) return "[]";

        String result = "["; // + 5 , + 25 , ...
        for (int i = 0; i < cursor; i++) {
            result += array[i] + (i < cursor - 1 ? ", " : "]");
        }
        return result;
    }

    // Текущее кол-во элементов в нашем массиве
    public int size() {
        return cursor;
    }

    // Возвращает значение по индексу
    public T get(int index) {
        // Проконтролировать входящий индекс!
        if (index >= 0 && index < cursor) {
            return array[index];
        }
        return null;
    }

    // Удалить элемент по индексу
    public T remove(int index) {

        if (index >= 0 && index < cursor) {
            // Логика удаления
            T value = array[index]; // запомнить старое значение

            // Перебираем элементы начиная с индекса и перезаписываем значения из ячейки справа
            for (int i = index; i < cursor - 1; i++) { // граница перебора индексов cursor - 1
                array[i] = array[i + 1];
            }
            cursor--;
            return value; // вернуть старое значение

        } else {
            return null;
        }
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return cursor == 0;
    }

    // Переписать значение по указанному индексу
    @Override
    public void set(int index, T value) {

        if (index >= 0 && index < cursor) {
            // если индекс корректный, присваиваем новое значение
            array[index] = value;
        }
    }

    // Поиск по значению
    public int indexOf(T value) {

        for (int i = 0; i < cursor; i++) {
            // null - безопасное сравнение (Objects.equals()) !
            if (Objects.equals(array[i], value)) {
                //if (array[i] !=null && array[i].equals(value)) {
                // Значения совпали. Возвращаю индекс
                return i;
            }
        }
        // Сюда попадем, если ни одно значение в массиве не совпало
        return -1;
    }


    // Индекс последнего вхождения.
    public int lastIndexOf(T value) {
        // оптимизированный вариант
        for (int i = cursor - 1; i >= 0; i--) {

            if (Objects.equals(array[i], value)) return i;
            //if (array[i].equals(value)) return i;
        }
        return -1;
    }

    // Удаление элемента по значению
    @Override
    public boolean remove(T value) {

        int index = indexOf(value); // получим индекс первого по очереди значения или -1
        if (index < 0) return false;

        //в эту строчку кода попадем только, при index = 0 или больше
        remove(index);
        return true;
    }

    // Массив, состоящий из элементов магического массива
    @SuppressWarnings("unchecked")
    public T[] toArray() {


//        T[] result = (T[]) new Object[cursor]; // ошибка при вызове!
//        T[] res = new T[11]; // нельзя создать объект
//        T obj = new T();


        // ---------------------РЕФЛЕКСИЯ------------------------
        if (cursor == 0) return null; // нет 0-го элемента

        Class<T> clazz = (Class<T>) array[0].getClass(); // определим класс 0-го элемента массива с помощью рефлексии
        System.out.println("clazz" + clazz);

        // Создаю массив того же типа, что и 0-й элемент
        T[] result = (T[]) Array.newInstance(clazz, cursor);
        // -------------------------------------------------------

        for (int i = 0; i < cursor; i++) {
            result[i] = array[i];
        }
        return result;
    }

    // Невозможно вернуть объект типа интерфейса.
    // Если тип возвращаемого значения (или параметр метода) имеет тип интерфейс
    // это значит, что я должен вернуть объект класса, который имплементировал (реализовал) этот интерфейс

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {

        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < cursor;
        }

        @Override
        public T next() {
            return array[currentIndex++];

            // T value = array[currentIndex];
            // currentIndex++;
            // return value;
        }
    } // End class MyIterator


    public void test() {
        System.out.println(Arrays.toString(array));
    }

}
