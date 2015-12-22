package com.example.john.grocerylist;

public class GroceryListItem {

    private String name;
    private String quantity;
    private int imgResourceId;

    public GroceryListItem(String name, String quantity, int imgResourceId) {
        this.imgResourceId = imgResourceId;
        this.name = name;
        this.quantity = quantity;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GroceryListItem)) {
            return false;
        }

        GroceryListItem item = (GroceryListItem) o;
        return name.equals(item.getName()) && quantity.equals(item.getQuantity()) && imgResourceId == item.getImgResourceId();
    }
}
