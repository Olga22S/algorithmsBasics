package ru.skypro.stringlist;

import ru.skypro.exception.AbsentItemException;
import ru.skypro.exception.FullStorageException;
import ru.skypro.exception.IndexOutsideException;
import ru.skypro.exception.NullItemException;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class StringListImpl implements StringList {

    private String[] storage;
    private int count = 0;

    public StringListImpl(int storageSize) {
        this.storage = new String[storageSize];
    }

    @Override
    public String add(String item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
        if (count == storage.length) {
            throw new FullStorageException("Storage if full!");
        }
        storage[count++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        count++;
        System.arraycopy(storage, index, storage, index + 1, count - index - 1);
        storage[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        if (isNull(item)) {
            throw new NullItemException("Item is null!");
        }
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
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
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        String item = storage[index];
        count--;
        System.arraycopy(storage, index + 1, storage, index, count - index);
        storage[count + 1] = null;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < count; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = count - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutsideException("Index outside of storage!");
        }
        return storage[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (isNull(otherList)) {
            throw new NullItemException("Item is null!");
        }
        for (int i = 0; i < count; i++) {
            if (!otherList.get(i).equals(storage[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count > 0;
    }

    @Override
    public void clear() {
        count = 0;
        storage = new String[0];
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(storage, count);
    }
}
