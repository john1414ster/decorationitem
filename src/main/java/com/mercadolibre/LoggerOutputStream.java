package com.mercadolibre;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class LoggerOutputStream extends OutputStream {

    StringBuilder unflushedContent = new StringBuilder();
    private final Logger logger;

    public LoggerOutputStream(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void write(int b) throws IOException {
        this.unflushedContent.append((char)b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.unflushedContent.append(new String(b));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.unflushedContent.append((new String(b)).substring(off, off + len));
    }

    @Override
    public void flush() throws IOException {
        this.logger.info(unflushedContent.toString());
        this.unflushedContent.setLength(0);
    }

    @Override
    public void close() throws IOException {
        this.flush();
    }
}