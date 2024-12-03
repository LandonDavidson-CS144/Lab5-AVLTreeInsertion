import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
        Random rand = new Random();

//        int size = 20;
//        int[] keys = new int[size];
//        for (int i = 0; i < size; i++) {
//            keys[i] = rand.nextInt(100);
//        }
//
        int[] keys = {1, 13, 9, 3, 7, 6, 5, 11, 4};

        System.out.println(Arrays.toString(keys));

        for (int key : keys) {
            tree.insert(key);
        }
        tree.printTree();
    }
}

class Node {
    int key;
    int balance;
    int height;
    Node left;
    Node right;
    Node parent;

    public Node(int key, Node parent) {
        height = 0;
        balance = 0;
        this.key = key;
        this.parent = parent;
    }
}

class AvlTree {
    Node root;

    public void insert(int key) {
        // If there is no root, set root as key and return
        if (root == null) {root = new Node(key, null); return;}

        // Start at root and descend tree until correct insert location is found
        Node curNode = root;
        while (true) {
            if (key < curNode.key) {
                if (curNode.left != null) {
                    curNode = curNode.left;
                } else {
                    curNode.left = new Node(key, curNode);
                    break;
                }
            } else {
                if (curNode.right != null) {
                    curNode = curNode.right;
                } else {
                    curNode.right = new Node(key, curNode);
                    break;
                }
            }
        }
        // Ascend back up tree while updating height and balance, and rotating tree if necessary
        do {
            // Balance tree
            updateBalance(curNode);
            if (curNode.balance == -2) {
                if (curNode.right.balance == 1) {
                    rotateRight(curNode.right);
                }
                rotateLeft(curNode);
                curNode = curNode.parent;
            } else if (curNode.balance == 2) {
                if (curNode.left.balance == -1) {
                    rotateLeft(curNode.left);
                }
                rotateRight(curNode);
                curNode = curNode.parent;
            }
            // update height
            int leftHeight;
            int rightHeight;

            if (curNode.left == null) {leftHeight = -1;}
            else {leftHeight = curNode.left.height;}

            if (curNode.right == null) {rightHeight = -1;}
            else {rightHeight = curNode.right.height;}

            curNode.height = Math.max(leftHeight, rightHeight) + 1;

            curNode = curNode.parent;
        } while (curNode != null);
    }

    public void updateBalance(Node curNode) {
        int leftHeight;
        int rightHeight;

        if (curNode.left == null) {leftHeight = -1;}
        else {leftHeight = curNode.left.height;}

        if (curNode.right == null) {rightHeight = -1;}
        else {rightHeight = curNode.right.height;}

        curNode.balance = leftHeight - rightHeight;
    }

    public void rotateRight(Node node) {
        Node leftNode = node.left;
        if (node == root) {
            root = node.left;
            node.left.parent = null;
        } else {
            if (node.left.key < node.parent.key) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
            node.left.parent = node.parent;
        }
        if (node.left.right != null) {
            node.left = node.left.right;
            node.left.parent = node;
        } else {
            node.left = null;
        }
        leftNode.right = node;
        node.parent = leftNode;
        leftNode.height++;
        node.height--;
    }

    public void rotateLeft(Node node) {
        Node rightNode = node.right;
        if (node == root) {
            root = node.right;
            node.right.parent = null;
        } else {
            if (node.right.key < node.parent.key) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
        }
        if (node.right.left != null) {
            node.right = node.right.left;
            node.right.parent = node;
        } else {
            node.right = null;
        }
        rightNode.left = node;
        node.parent = rightNode;
        rightNode.height++;
        node.height--;
    }

    public void printTree() {
        recPrintTree("", root);
    }

    public void recPrintTree(String space, Node cur) {
        if (cur.right != null) {
            recPrintTree(space + "     ", cur.right);
        }
        System.out.println(space + cur.key);
        if (cur.left != null) {
            recPrintTree(space + "     ", cur.left);
        }

    }
}
