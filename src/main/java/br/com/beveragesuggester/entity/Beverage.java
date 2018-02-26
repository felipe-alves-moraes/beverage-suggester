package br.com.beveragesuggester.entity;

import java.util.List;

/**
 *
 * @author Felipe
 */
public class Beverage {

    private String name;
    private List<Category> categories;

    public Beverage(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
