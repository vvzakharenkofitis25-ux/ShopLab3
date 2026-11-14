package service;

public class BinarySearchTree {
    private Node root;

    private static class Node {
        String key;
        Node left, right;

        public Node(String key) {
            this.key = key;
            left = right = null;
        }
    }

    public void insert(String key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, String key) {
        if (root == null) {
            return new Node(key);
        }
        if (key.compareTo(root.key) < 0) {
            root.left = insertRec(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }

    public void inorder() {
        System.out.print("Обхід бінарного дерева (In-Order): ");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }
}