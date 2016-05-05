package process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class FileContent implements Iterable<String> {

    private List<String> lines;

    public FileContent() {
        this.lines = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        lines.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return lines.spliterator();
    }

}
