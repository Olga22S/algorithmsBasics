package ru.skypro.stringlist;

import ru.skypro.exception.AbsentItemException;
import ru.skypro.exception.FullStorageException;
import ru.skypro.exception.IndexOutsideException;
import ru.skypro.exception.NullItemException;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class StringListImpl implements StringList {

    private final String[] storage;
    private int size;

    public StringListImpl() {
        this.storage = new String[10];
    }

    public StringListImpl(int storageSize) {
        this.storage = new String[storageSize];
    }

    @Override
    public String add(String item) {
        validateItem(item);
        if (size == storage.length) {
            throw new FullStorageException("Storage if full!");
        }
        storage[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateItem(item);
        validateIndex(index);
        size++;
        System.arraycopy(storage, index, storage, index + 1, size - index - 1);
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateItem(item);
        validateIndex(index);
        storage[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        int index = indexOf(item);
        if (index == -1) {
            throw new AbsentItemException("This item is absent!");
        }
        return remove(index);
    }

    @Override
    public String remove(int index) {
        validateIndex(index);
        String item = storage[index];
        size--;
        if (index != size) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = size - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndex(index);
        return storage[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (isNull(otherList)) {
            throw new NullItemException("Item is null!");
        }
        if (otherList.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!otherList.get(i).equals(storage[i])) {
                return false;
            }
        }
        return true;
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
    public void clear() {
        while (--size != 0) {
            storage[size] = null;
        }
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(storage, size);
    }

    private void validateItem(String item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
    }
}
