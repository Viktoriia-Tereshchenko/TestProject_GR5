package shmahailo;

import java.util.Objects;

public class Task_1 {
    private String model;
    private int price;

    public Task_1(String model, int price) {
        this.model = model;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Task_1{" +
                "model='" + model + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task_1)) return false;
        Task_1 task1 = (Task_1) o;
        return price == task1.price && Objects.equals(model, task1.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price);
    }
}
