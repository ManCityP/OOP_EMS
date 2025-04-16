package p1;

import java.util.ArrayList;

public class Category {
    static ArrayList<String> categories = new ArrayList<>();

    String category;

    public Category(String category) throws Exception {
        if (!categories.contains(category))
            throw new Exception("This Category doesn't exist!");
        this.category = category;
    }

    @Override
    public String toString() {
        return this.category;
    }

    static void AddCategory(String category) throws Exception {
        if(categories.contains(category))
            throw new Exception("This Category already exists!");
        categories.add(category);
    }

    static void RemoveCategory(String category) throws Exception {
        if (!categories.contains(category))
            throw new Exception("This Category doesn't exist!");
        categories.remove(category);
    }
}
