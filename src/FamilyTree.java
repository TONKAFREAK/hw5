import java.util.*;

public class FamilyTree {
    private Node root;
    private Scanner scanner;

    public FamilyTree() {
        this.root = null;
        this.scanner = new Scanner(System.in);
    }

    public void buildTree() {
        System.out.println("Enter the name of the root:");
        String rootName = scanner.nextLine();
        root = new Node(rootName);

        System.out.println("Building family tree for " + rootName);
        buildTreeHelper(root);
    }

    private void buildTreeHelper(Node parent) {
        System.out.println("Enter the number of sons for " + parent.name + ":");
        int numSons = scanner.nextInt();
        scanner.nextLine();

        Node prevSon = null;
        for (int i = 0; i < numSons; i++) {
            System.out.println("Enter the name of son " + (i + 1) + " of " + parent.name + ":");
            String sonName = scanner.nextLine();
            Node son = new Node(sonName);

            if (prevSon == null) {
                parent.left = son;
            } else {
                prevSon.right = son;
            }
            prevSon = son;
            buildTreeHelper(son);
        }
    }

    public Node findNode(Node current, String name) {
        if (current == null)
            return null;
        if (current.name.equals(name))
            return current;

        Node foundInLeft = findNode(current.left, name);
        if (foundInLeft != null)
            return foundInLeft;

        return findNode(current.right, name);
    }

    public void answerQuestions() {
        while (true) {
            System.out.println("Enter the name of the person to query or 'exit' to stop:");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit"))
                break;

            Node person = findNode(root, name);
            if (person == null) {
                System.out.println("Person not found in the family tree.");
                continue;
            }

            System.out.println("Select a question:");
            System.out.println("1. Who is the father of " + name + "?");
            System.out.println("2. Who are all the sons of " + name + "?");
            System.out.println("3. Who are all the brothers of " + name + "?");
            System.out.println("4. Who is the oldest brother of " + name + "?");
            System.out.println("5. Who is the youngest brother of " + name + "?");
            System.out.println("6. Who is the oldest son of " + name + "?");
            System.out.println("7. Who is the youngest son of " + name + "?");
            System.out.println("8. Who are the uncles of " + name + "?");
            System.out.println("9. Who is the grandfather of " + name + "?");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Father: " + findFather(root, name));
                    break;
                case 2:
                    printSons(person);
                    break;
                case 3:
                    printBrothers(root, name);
                    break;
                case 4:
                    System.out.println("Oldest Brother: " + findOldestBrother(root, name));
                    break;
                case 5:
                    System.out.println("Youngest Brother: " + findYoungestBrother(root, name));
                    break;
                case 6:
                    System.out.println("Oldest Son: " + findOldestSon(person));
                    break;
                case 7:
                    System.out.println("Youngest Son: " + findYoungestSon(person));
                    break;
                case 8:
                    printUncles(root, name);
                    break;
                case 9:
                    System.out.println("Grandfather: " + findGrandfather(root, name));
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private String findFather(Node current, String name) {
        if (current == null || current.left == null)
            return null;
        if (current.left.name.equals(name) || hasSibling(current.left, name))
            return current.name;

        String foundInLeft = findFather(current.left, name);
        if (foundInLeft != null)
            return foundInLeft;

        return findFather(current.right, name);
    }

    private boolean hasSibling(Node sibling, String name) {
        while (sibling != null) {
            if (sibling.name.equals(name))
                return true;
            sibling = sibling.right;
        }
        return false;
    }

    private void printSons(Node parent) {
        if (parent.left == null) {
            System.out.println("No sons.");
            return;
        }
        Node son = parent.left;
        System.out.print("Sons: ");
        while (son != null) {
            System.out.print(son.name + " ");
            son = son.right;
        }
        System.out.println();
    }

    private void printBrothers(Node current, String name) {
        Node father = findNode(current, findFather(root, name));
        if (father == null) {
            System.out.println("No brothers.");
            return;
        }
        Node son = father.left;
        System.out.print("Brothers: ");
        while (son != null) {
            if (!son.name.equals(name))
                System.out.print(son.name + " ");
            son = son.right;
        }
        System.out.println();
    }

    private String findOldestBrother(Node current, String name) {
        Node father = findNode(current, findFather(root, name));
        return (father != null && father.left != null) ? father.left.name : null;
    }

    private String findYoungestBrother(Node current, String name) {
        Node father = findNode(current, findFather(root, name));
        if (father == null || father.left == null)
            return null;
        Node son = father.left;
        while (son.right != null)
            son = son.right;
        return son.name;
    }

    private String findOldestSon(Node parent) {
        return (parent.left != null) ? parent.left.name : null;
    }

    private String findYoungestSon(Node parent) {
        if (parent.left == null)
            return null;
        Node son = parent.left;
        while (son.right != null)
            son = son.right;
        return son.name;
    }

    private void printUncles(Node current, String name) {
        String fatherName = findFather(root, name);
        if (fatherName == null) {
            System.out.println("No uncles.");
            return;
        }
        Node grandfather = findNode(current, findFather(root, fatherName));
        if (grandfather == null) {
            System.out.println("No uncles.");
            return;
        }
        Node uncle = grandfather.left;
        System.out.print("Uncles: ");
        while (uncle != null) {
            if (!uncle.name.equals(fatherName))
                System.out.print(uncle.name + " ");
            uncle = uncle.right;
        }
        System.out.println();
    }

    private String findGrandfather(Node current, String name) {
        String fatherName = findFather(root, name);
        return (fatherName != null) ? findFather(root, fatherName) : null;
    }

    public static void main(String[] args) {
        FamilyTree familyTree = new FamilyTree();
        familyTree.buildTree();
        familyTree.answerQuestions();
    }
}
