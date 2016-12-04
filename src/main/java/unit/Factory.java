package unit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Factory<T, R> {
    protected final Map<T, Supplier<? extends R>> creators = new HashMap<>();

    public R getInstance(T id) {
        if (creators.containsKey(id)) {
            return creators.get(id).get();
        } else {
            System.out.println(id.toString());
            return null;
//            throw new NotImplementedException();
        }
    }

    public boolean add(T id, Supplier<? extends R> creator) {
        if (creators.containsKey(id)) {
            return false;
        }
        creators.put(id, creator);

        return true;
    }

    public boolean remove(T id) {
        if (creators.containsKey(id)) {
            creators.remove(id);
            return true;
        }

        return false;
    }
}