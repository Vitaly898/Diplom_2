package Models;

import java.util.List;

public class Order {
    private List <String> ingredients;
    //Конструктор для создания заказа

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    //Дефолтный конструктор
    public Order(){

    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }








}
