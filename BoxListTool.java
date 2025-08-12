package useful.Random;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class BoxListTool extends DefaultListModel<String>{


    public void export(File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (String s : export_by_array()) {
                fileWriter.write(s);
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void inducts(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] i = new char[32];
            int len;
            while ((len = fileReader.read(i)) != -1) {
                stringBuilder.append(new String(i, 0, len));
            }

            String string = stringBuilder.toString();
            inducts_by_array(string.split("\n"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] export_by_array() {
        String[] strings = new String[size()];
        for (int i = 0; i < getSize(); i++) {
            strings[i] = get(i);
        }
        return strings;
    }

    public void inducts_by_array(String[] array) {
        clear();
        for (String s : array) {
            addElement(s);
        }
    }

    private boolean inBox(String text) {
        for (int i = 0; i < getSize(); i++) {
            String s = get(i);
            if (Objects.equals(s, text)) {
                return true;
            }
        }
        return false;
    }
    public void addText(String text) {
        if ((inBox(text) || Objects.equals(text, "")))
            return;

        addElement(text);
    }

    public void addText_setFiled(JTextField addTextField) {
        String text = addTextField.getText();

        addText(text);
        addTextField.setText("");
    }

    public void set_random_text(JLabel label) {
        Random r = new Random();
        int size = getSize();
        if (size==0) return;
        int index = r.nextInt(size);
        String text = get(index);

        label.setFont(new Font("", Font.PLAIN, 20));
        label.setText(text);
    }

    public void deletes_selection(JList<String> list) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex==-1||selectedIndex>=getSize()) return;
        remove(list.getSelectedIndex());
    }

}
