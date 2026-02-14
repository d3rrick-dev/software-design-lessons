
The Composite design pattern is essentially the "Matryoshka doll" of software engineering. It’s a structural pattern used when you need to treat a single object and a group of objects exactly the same way.
Think of it as the ultimate "I don't care" button for hierarchies. Whether you're dealing with a single leaf or an entire branch of a tree, you call the same method, and the pattern handles the recursion for you.

The Composite pattern organizes objects into a tree structure to represent part-whole hierarchies.There are three main players in this setup:ComponentThe InterfaceDefines the common operations for both simple and complex objects (e.g., execute() or getSize()).LeafThe IndividualThe "atomic" unit that has no children. it actually does the work.CompositeThe ContainerA complex object that stores Leaves or other Composites. It delegates work to its children.

A "Real World" Example: The File System
Imagine you are building a tool to calculate the total disk space used by a directory. A directory can contain Files (Leaves) or other Sub-directories (Composites).

Without this pattern, you’d have to constantly check: "Is this a file? Okay, get its size. Is this a folder? Okay, loop through it and get the size of everything inside..." That gets messy fast.

The Composite Solution
Component (FileSystemItem): An interface with a method getSize().

Leaf (File): Implements getSize() by returning its actual file size (e.g., 10 KB).

Composite (Directory): Implements getSize() by looping through its list of children and summing up their results.

Use this pattern whenever you realize you're writing a lot of if (isGroup) or if (isSingle) logic in your UI or file processing code.