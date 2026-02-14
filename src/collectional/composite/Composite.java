void main() {
    var f1 = new File(1);
    var f2 = new File(2);
    var f3 = new File(3);

    var dir = new Directory();
    var dir1 = new Directory();
    dir1.add(f1);
    dir1.add(f2);
    dir.add(f3);
    dir.add(dir1);
    IO.println(dir.getSize()); //6
    IO.println(dir1.getSize()); // 3
}

// 1. The Component
interface FileSystemItem {
    int getSize();
}

// 2. The Leaf
static class File implements FileSystemItem {
    private final int size;
    public File(int size) { this.size = size; }

    public int getSize() { return size; }
}

// 3. The Composite
static class Directory implements FileSystemItem {
    private final List<FileSystemItem> children = new ArrayList<>();

    public void add(FileSystemItem item) { children.add(item); }

    public int getSize() {
        int total = 0;
        for (FileSystemItem item : children) {
            total += item.getSize(); // File OR another Directory!
        }
        return total;
    }
}