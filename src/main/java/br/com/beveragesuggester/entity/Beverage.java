package br.com.beveragesuggester.entity;

import java.util.Set;

/**
 * @author Felipe
 */
public record Beverage(String name, Set<Category> categories) { }
