package test;



import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class SWTest {
    public static void main(String[] args) {
        createWindow();
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Swing Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setSize(560, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createUI(final JFrame frame) {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);

        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);
        HTMLDocument doc = (HTMLDocument) jEditorPane.getDocument();
        File f = new File("/home/ls/IdeaProjects/Tray/src/main/resources/templates/login.html");
        try {
            FileInputStream reader = new FileInputStream(f);
            kit.read(reader, doc, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540, 400));

        panel.add(jScrollPane);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

    }
}

