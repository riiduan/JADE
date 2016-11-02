package gui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * Classe per l'output della console sulla GUI
 *
 */
public class JTextAreaOutputStream  extends OutputStream {
    private JTextArea textArea;

    /**
     * Costruttore
     * @param textArea sulla quale visualizzare l'output della console
     */
    public JTextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    /**
     * metodo che inserisce il testo nella JTextArea
     */
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
