package Services;

import entity.Curriculum;
import exception.WriterException;

public interface Writer {
    void write(Curriculum curriculum) throws WriterException;
}
