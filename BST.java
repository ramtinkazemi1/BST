import java.util.ArrayList;
import java.util.List;

/**
 * This is the BST class that sorts entries based on their key.
 * @param <K> The type of the keys of this BST.
 * @param <V> The type of the values of this BST.
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {

    private Node<K, V> root;
    private int size;

    // Constructor
    public BST() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public boolean put(K key, V value) throws IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        } else if (containsKey(key)) {
            return false;
        } else {
            Node<K, V> currentNode = new Node<>(key, value);
            Node<K, V> current = root;
            root = putRecursive(current, currentNode);
            size++;
            return true;
        }
    }

    private Node<K, V> putRecursive(Node<K, V> current, Node<K, V> newData) {
        if (current == null) {
            current = newData;
        } else {
            if (newData.getKey().compareTo(current.getKey()) < 0)
                current.setLeft(putRecursive(current.getLeft(), newData));
            else if (newData.getKey().compareTo(current.getKey()) > 0)
                current.setRight(putRecursive(current.getRight(), newData));
        }
        return current;
    }

    @Override
    public boolean replace(K key, V newValue) throws IllegalArgumentException {
        if (key == null || newValue == null) {
            throw new IllegalArgumentException();
        } else if (!containsKey(key)) {
            return false;
        } else {
            Node<K, V> current = root;
            replaceRecursive(current, key, newValue);
            return true;
        }
    }

    private V replaceRecursive(Node<K, V> current, K key, V newValue) {
        if (current == null) {
            return null;
        } else if (current.getKey().compareTo(key) == 0) {
            current.setValue(newValue);
            return current.getValue();
        } else {
            V left = replaceRecursive(current.getLeft(), key, newValue);
            if (left == null) {
                return replaceRecursive(current.getRight(), key, newValue);
            } else {
                return left;
            }
        }
    }

    @Override
    public boolean remove(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (!containsKey(key)) {
            return false;
        } else {
            Node<K, V> current = root;
            root = removeRecursive(current, key);
            size--;
            return true;
        }
    }

    private Node<K, V> removeRecursive(Node<K, V> current, K key) {
        if (current == null) {
            return null;
        } else if (current.getKey().compareTo(key) > 0) {
            current.setLeft(removeRecursive(current.getLeft(), key));
        } else if (current.getKey().compareTo(key) < 0) {
            current.setRight(removeRecursive(current.getRight(), key));
        } else {
            if (current.getLeft() != null && current.getRight() != null) {
                Node<K, V> min = findMinimumLeft(current.getRight());
                current.setKey(min.getKey());
                current.setValue(min.getValue());
                current.setRight(removeRecursive(current.getRight(), min.getKey()));
            } else if (current.getRight() != null) {
                current = current.getRight();
            } else if (current.getLeft() != null) {
                current = current.getLeft();
            } else {
                current = null;
            }
        }
        return current;
    }

    private Node<K, V> findMinimumLeft(Node<K, V> root) {
        if (root.getLeft() != null) {
            return findMinimumLeft(root.getLeft());
        }
        return root;
    }

    @Override
    public void set(K key, V value) throws IllegalArgumentException {
        if (containsKey(key)) {
            replace(key, value);
        } else {
            put(key, value);
        }
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        } else {
            Node<K, V> current = root;
            return getRecursive(current, key);
        }
    }

    private V getRecursive(Node<K, V> current, K key) {
        if (current == null) {
            return null;
        } else if (current.getKey().compareTo(key) == 0) {
            return current.getValue();
        } else {
            V result = getRecursive(current.getLeft(), key);
            if (result == null) {
                return getRecursive(current.getRight(), key);
            } else {
                return result;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        } else {
            Node<K, V> current = root;
            return containsKeyRecursive(current, key);
        }
    }

    private boolean containsKeyRecursive(Node<K, V> current, K key) {
        if (current == null) {
            return false;
        } else if (current.getKey().compareTo(key) == 0) {
            return true;
        } else {
            return containsKeyRecursive(current.getLeft(), key) || containsKeyRecursive(current.getRight(), key);
        }
    }

    @Override
    public List<K> keys() {
        List<K> list = new ArrayList<>();
        Node<K, V> current = root;
        findKeys(current, list);
        return list;
    }

    private void findKeys(Node<K, V> current, List<K> list) {
        if (current != null) {
            findKeys(current.getLeft(), list);
            list.add(current.getKey());
            findKeys(current.getRight(), list);
        }
    }

    private static class Node<K extends Comparable<? super K>, V> implements DefaultMap.Entry<K, V> {
        // Attributes..
        private K key;
        private V value;
        private Node<K, V> left, right;

        public Node(K key, V val) {
            this.key = key;
            this.value = val;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public Node<K, V> getRight() {
            return right;
        }

        public void setLeft(Node<K, V> left) {
            this.left = left;
        }

        public void setRight(Node<K, V> right) {
            this.right = right;
        }
    }
}
