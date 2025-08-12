package useful.Random;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;

import static useful.Random.Box_frame.Box_frame_ListModel;
import static useful.Random.Entropy.*;

public class Sample_frame extends JFrame {
    static Sample_frame settingWindow = null;
    static BoxListTool Sample_frame_ListModel = new BoxListTool();

    public static void add() {
        if (settingWindow != null) {
            settingWindow.dispose();
        }
        settingWindow = new Sample_frame();
    }

    public Sample_frame() {
        init();
        drawMain();
        inducts_sample();
        setVisible(true);
    }

    private void inducts_sample() {
        File file = new File("src/useful/Random/sample");
        if (file.exists() && file.isDirectory()) {
            File[] samples = file.listFiles();
            if (samples != null) {
                for (File sample : samples) {
                    if (sample.isFile()) {
                        String name = sample.getName();
                        Sample_frame_ListModel.addText(name.substring(0, name.length() - 4));
                    }
                }
            }
        }
    }

    private void save() {
        String name = JOptionPane.showInputDialog(settingWindow, "为保存的样本命名");
        if (name == null) return;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(settingWindow, "未输入名称");
            save();
        }

        Sample_frame_ListModel.addText(name);
        Box_frame_ListModel.export(new File(get_sample_path(""), name+".txt"));

    }

    private void remove(String name) {
        Sample_frame_ListModel.removeElement(name);
        boolean delete = get_sample_path(name + ".txt").delete();
    }


    private void drawMain() {
        GridBagConstraints gbc;

        JList<String> list = new JList<>(Sample_frame_ListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10); // 设置默认显示行数

        JScrollPane scrollPane = new JScrollPane(list);
        gbc = new GridBagConstraints();
        gbc.weightx=1; // 水平扩展
        gbc.weighty=1; // 水平扩展
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(10, 20, 10, 20); // 增加边距
        add(scrollPane, gbc);


        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

//——————————————————————————
        JButton inducts_button = new JButton("导入");

        inducts_button.addActionListener((_) -> Box_frame_ListModel.inducts(new File(get_sample_path(""), list.getSelectedValue()+".txt")));
        panel.add(inducts_button);


//——————————————————————————
        JButton save_button = new JButton("导出");

        save_button.addActionListener((_) -> save());
        panel.add(save_button);


//——————————————————————————
        JButton remove_button = new JButton("删除");
        remove_button.addActionListener((_) -> remove(list.getSelectedValue()));


//      由于刚打开窗口, 没有选择任何内容，所以不启用导入和删除按钮
        inducts_button.setEnabled(false);
        remove_button.setEnabled(false);

//      设置监听，如果选择一个内容，启用导入和删除，并删除监听
        list.addListSelectionListener(_ -> {
            inducts_button.setEnabled(true);
            remove_button.setEnabled(true);
            list.removeListSelectionListener(list.getListSelectionListeners()[0]);
        });





        panel.add(Box.createHorizontalStrut(20));
        panel.add(remove_button, gbc);

        gbc = new GridBagConstraints();
        gbc.gridy=1;
        gbc.insets=new Insets(30,0,20,0);
        gbc.anchor=GridBagConstraints.CENTER;

        add(panel, gbc);

//        结束工具区 ——————————————————————————

    }



    public void init() {
        setTitle("样本库");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 450);
        setIconImage(get_icon_image());
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
    }


}
