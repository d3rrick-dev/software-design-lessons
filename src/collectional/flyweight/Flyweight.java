void main() {
 var treeType = TreeFactory.getTreeType("my tree", "green", "heavyObj");
 var tree = new Tree(1,2, treeType);
 var tre2 = new Tree(3,4, treeType);
 tree.draw();
 tre2.draw();
}

static class TreeType {
    final private String name;
    private String color;
    private String otherTreeData; // Imagine this is a heavy 10MB texture

    public TreeType(String name, String color, String otherTreeData) {
        this.name = name;
        this.color = color;
        this.otherTreeData = otherTreeData;
    }

    public void draw(int x, int y) {
        System.out.println("Drawing " + name + " at (" + x + "," + y + ")");
    }
}

static class TreeFactory {
    private static Map<String, TreeType> treeTypes = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String data) {
        var key = name + "-" + color;
        if (!treeTypes.containsKey(key)) {
            treeTypes.put(key, new TreeType(name, color, data));
            System.out.println("Creating new TreeType: " + name);
        }
        return treeTypes.get(key);
    }
}

//The unique "lightweight" object that references the Flyweight.
static class Tree {
    private int x, y;
    private TreeType type; // Reference to the shared Flyweight

    public Tree(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw() {
        type.draw(x, y);
    }
}