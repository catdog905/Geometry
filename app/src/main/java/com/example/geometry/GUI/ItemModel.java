package com.example.geometry.GUI;

public class ItemModel {

    public int icon;
    public String name;

    // модель данных используемая в адаптере DrawerItemCustomAdapter
    public ItemModel(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }
}