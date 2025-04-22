package p1;

public class Category {
    //static ArrayList<String> categories = new ArrayList<>();

    private String category = "Default";

    public Category() {}
    public Category(String category) throws Exception {
        if (!Database.GetCategories().contains(category))
            System.out.println("This Category doesn't exist!");
        this.category = category;
    }

    @Override
    public String toString() {
        return this.category;
    }

    static void AddCategory(String category) {
        String sql = "INSERT INTO category (name) VALUES ('" + category + "')";
        Database.Execute(sql);
    }
    static void RemoveCategory(String category) throws Exception {
        if(category.equals("Default"))
            throw new Exception("Cannot Remove Default Category");
        String sql = "DELETE FROM category WHERE name = '" + category + "'";
        Database.Execute(sql);
    }
}