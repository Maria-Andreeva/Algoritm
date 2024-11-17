package dz;

// Определение класса узла дерева
class Node {
    int value;
    Node left, right, parent;
    boolean isRed;

    Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.isRed = true; // Новый узел всегда красный
    }
}

public class RedBlackTree {
    private Node root;

    // Конструктор
    public RedBlackTree() {
        this.root = null;
    }

    // Преобразование бинарного дерева поиска в красно-черное дерево
    public void transformBSTtoRBT(Node bstRoot) {
        if (bstRoot == null) {
            return;
        }
        add(bstRoot.value); // Добавляем корневое значение
        transformBSTtoRBT(bstRoot.left); // Рекурсивно добавляем левое поддерево
        transformBSTtoRBT(bstRoot.right); // Рекурсивно добавляем правое поддерево
    }

    // Добавление элемента в РЧД
    public void add(int value) {
        root = add(root, value);
        root.isRed = false; // Корень всегда черный
    }

    private Node add(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
            node.left.parent = node;
        } else if (value > node.value) {
            node.right = add(node.right, value);
            node.right.parent = node;
        }

        // Балансировка дерева после вставки
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    // Проверка, является ли узел красным
    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.isRed;
    }

    // Левый малый поворот
    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        if (x.left != null) {
            x.left.parent = node;
        }
        x.parent = node.parent;
        if (node.parent == null) {
            root = x;
        } else if (node == node.parent.left) {
            node.parent.left = x;
        } else {
            node.parent.right = x;
        }
        x.left = node;
        node.parent = x;
        x.isRed = node.isRed;
        node.isRed = true;
        return x;
    }

    // Правый малый поворот
    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        if (x.right != null) {
            x.right.parent = node;
        }
        x.parent = node.parent;
        if (node.parent == null) {
            root = x;
        } else if (node == node.parent.right) {
            node.parent.right = x;
        } else {
            node.parent.left = x;
        }
        x.right = node;
        node.parent = x;
        x.isRed = node.isRed;
        node.isRed = true;
        return x;
    }

    // Смена цвета узла и его дочерних элементов
    private void flipColors(Node node) {
        node.isRed = !node.isRed;
        if (node.left != null) {
            node.left.isRed = !node.left.isRed;
        }
        if (node.right != null) {
            node.right.isRed = !node.right.isRed;
        }
    }

    // Метод для вывода дерева в удобном виде
    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }
            System.out.println(node.value + (node.isRed ? "(R)" : "(B)"));
            printTree(node.left, indent, false);
            printTree(node.right, indent, true);
        }
    }

    // Основной метод для тестирования
    public static void main(String[] args) {
        // Пример бинарного дерева поиска
        Node bstRoot = new Node(10);
        bstRoot.left = new Node(5);
        bstRoot.right = new Node(15);
        bstRoot.left.left = new Node(2);
        bstRoot.left.right = new Node(7);
        bstRoot.right.left = new Node(12);
        bstRoot.right.right = new Node(20);

        // Преобразование в красно-черное дерево
        RedBlackTree rbt = new RedBlackTree();
        rbt.transformBSTtoRBT(bstRoot);

        // Вывод преобразованного дерева
        System.out.println("Red-Black Tree:");
        rbt.printTree();
    }
}
