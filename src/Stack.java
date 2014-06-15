import java.util.Deque;

/**
 * Created by webserg on 14.06.2014.
 *
 */
public class Stack<E> {
    Deque<E> deque;

    public Stack(Deque<E> deque) {
        this.deque = deque;
    }

    public E take() {
        return deque.poll();
    }

    public boolean put(E e) {
        return deque.offerFirst(e);
    }

    public boolean isEmpty(){
        return deque.isEmpty();
    }
}
