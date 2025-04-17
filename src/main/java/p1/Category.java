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

    public static ArrayList<String> GetCategory(){
        return categories;
    }

    @Override
    public String toString() {
        return this.category;
    }
}
