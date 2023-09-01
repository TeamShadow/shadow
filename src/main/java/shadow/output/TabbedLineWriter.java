package shadow.output;

import shadow.ShadowException;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

public class TabbedLineWriter {
  private int indent;
  private final Writer out;
  private boolean atLineStart = true;

  @SuppressWarnings("unused")
  public TabbedLineWriter(BufferedWriter writer) {
    out = writer;
  }

  public TabbedLineWriter(Writer writer) {
    out = new BufferedWriter(writer);
  }

  public TabbedLineWriter(OutputStream output) {
    out = new OutputStreamWriter(new BufferedOutputStream(output));
  }

  @SuppressWarnings("unused")
  public TabbedLineWriter(String file) throws ShadowException {
    try {
      out = new BufferedWriter(new FileWriter(file));
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public TabbedLineWriter(Path file) throws ShadowException {
    try {
      out = new BufferedWriter(new FileWriter(file.toFile()));
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void indent() {
    ++indent;
  }

  public void indent(int amount) {
    indent += amount;
  }

  public void outdent() {
    if (--indent < 0) indent = 0;
  }

  public void outdent(int amount) {
    if ((indent -= amount) < 0) indent = 0;
  }

  private static final int TAB_SIZE = 4, INDENT_BUFFER_SIZE = 8 * TAB_SIZE;
  private static char[] indentBuffer;

  private void writeIndent(int amount) throws IOException {
    if (amount < 0) return;
    char[] b = indentBuffer;
    if (b == null) {
      indentBuffer = b = new char[INDENT_BUFFER_SIZE];
      Arrays.fill(b, ' ');
    }
    while (amount > 0) {
      int current = Math.min(amount, INDENT_BUFFER_SIZE);
      out.write(b, 0, amount);
      amount -= current;
    }
  }

  private void writeIndent() throws IOException {
    writeIndent(indent * TAB_SIZE);
  }

  private static String newline;

  private void writeNewline() throws IOException {
    String nl = newline;
    if (nl == null) newline = nl = System.getProperty("line.separator");
    out.write(nl);
    // out.flush();

    atLineStart = true;
  }

  public void write() throws ShadowException {
    try {
      writeNewline();
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void write(String string) throws ShadowException {
    try {
      writeIndent();
      out.write(string);
      writeNewline();
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void writeLeft(String string) throws ShadowException {
    try {
      out.write(string);
      writeNewline();
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void write(char c) throws ShadowException {
    try {
      writeIndent();
      out.write(c);
      writeNewline();
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void writeNoLine(String string) throws ShadowException {
    try {
      if (atLineStart) {
        writeIndent();
        atLineStart = false;
      }

      out.write(string);
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void writeNoLine(char c) throws ShadowException {
    try {
      if (atLineStart) {
        writeIndent();
        atLineStart = false;
      }

      out.write(c);
    } catch (IOException ex) {
      throw new OutputException(ex.getLocalizedMessage());
    }
  }

  public void close() throws IOException {
    if (out != null) {
      out.flush();
      out.close();
    }
  }

  public void flush() throws IOException {
    if (out != null) {
      out.flush();
    }
  }
}
