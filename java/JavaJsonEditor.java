import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import org.json.JSONObject;
import org.json.JSONException;

public class JavaJsonEditor extends JFrame
{
    private  JEditorPane  editor ;  // наш редактор
    private  JTextField   url    ;  // текстовое поле с адресом
    private  JButton      save   ;
    private  final String unavailable = "Адрес недоступен";

    public JavaJsonEditor()
    {
        super("Редактор JSON на Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создаие пользовательского интерфейса
        createGUI();
        // Вывод окна на экран
        setSize(600, 600);
        setVisible(true);
    }
    /**
     * Процедура создания интерфейса
     */
    private void createGUI()
    {
        // Панель с адресной строкой
        JPanel pnlURL = new JPanel();
        pnlURL.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlURL.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // Поле URL адреса
        url = new JTextField("http://kappa.cs.petrsu.ru/~dimitrov/cross2021/test.json", 35);
        // Слушатель окончания ввода
        url.addActionListener(new URLAction());
        pnlURL.add(new JLabel("Адрес:"));
        pnlURL.add(url);

        try {
            // Создание редактора
            editor = new JEditorPane(url.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, unavailable);
        }
        editor.setContentType("text/html");
        editor.setEditable(true);
        // Поддержка ссылок
        editor.addHyperlinkListener(new LinkListener());

        save = new JButton("Save");
        save.addActionListener(new ListenerAction());

        // Размещение в форме
        getContentPane().add(pnlURL, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(editor));
        getContentPane().add(save, BorderLayout.SOUTH);
    }
    // Слушатель, получающий уведомления о вводе нового адреса
    class URLAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Переход по адресу
            String newAddress = url.getText();
            try {
                editor.setPage(newAddress);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JavaJsonEditor.this,
                        unavailable);
            }
        }
    }
    // Слушатель, обеспечивающий поддержку ссылок
    class LinkListener implements HyperlinkListener {
        public void hyperlinkUpdate(HyperlinkEvent he) {
            // Проверка типа события
            if ( he.getEventType() != HyperlinkEvent.EventType.ACTIVATED)
                return;
            // Переходим по адресу
            try {
                editor.setPage(he.getURL());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(JavaJsonEditor.this,
                        unavailable);
            }
        }
    }
    class ListenerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = editor.getText();
            try {
                JSONObject o = new JSONObject(text);
            } catch (JSONException ex) {
                JOptionPane.showMessageDialog(JavaJsonEditor.this,
                        "This is not JSON!");
                return;
            }
            try(FileWriter writer = new FileWriter("notes3.txt", false))
            {
                // запись всей строки
                writer.write(text);

                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        new JavaJsonEditor();
    }
}