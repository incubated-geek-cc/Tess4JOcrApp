package app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

class TextAreaOutputStream extends OutputStream {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final JTextArea LOG_TEXT_AREA;

    public TextAreaOutputStream(JTextArea LOG_TEXT_AREA) {
        super();
        this.LOG_TEXT_AREA = LOG_TEXT_AREA;
    }

    @Override
    public void flush() throws IOException {
        LOG_TEXT_AREA.append(buffer.toString("UTF-8"));
        buffer.reset();
    }

    @Override
    public void write(int b) {
        buffer.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        buffer.write(b, off, len);
    }
}