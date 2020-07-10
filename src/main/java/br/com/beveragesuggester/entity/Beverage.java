package br.com.beveragesuggester.entity;

import java.util.List;

/**
 *
 * @author Felipe
 */
public class Beverage {

    private final String name;
    private final List<Category> categories;

    public Beverage(final String name, final List<Category> categories) {
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
