package useful.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static useful.Random.Entropy.get_icon_image;
import static useful.Random.Entropy.get_sample_path;

public class Box_frame extends JFrame {
    private static Box_frame settingWindow;
    public static BoxListTool Box_frame_ListModel = new BoxListTool();

    static {
        Box_frame_ListModel.inducts(get_sample_path("cache/cache.txt"));
    }


    public static BoxListTool getBox_frame_ListModel() {
        return Box_frame_ListModel;
    }

    public static void add() {
        if (settingWindow != null) {
            settingWindow.dispose();
        }
        settingWindow = new Box_frame();
    }

    private Box_frame() {
        setTitle("样本设置");
        setSize(600, 400);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(get_icon_image());
        setLocationRelativeTo(null);
        drawBox();
        setVisible(true);

    }


    private void drawBox() {
        String[] strings = Box_frame_ListModel.export_by_array();

        GridBagConstraints gbc;
        JPanel box = new JPanel(new GridBagLayout());


        // 添加 JList
        JList<String> list = new JList<>(Box_frame_ListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10); // 设置默认显示行数

        JScrollPane scrollPane = new JScrollPane(list);
        gbc = new GridBagConstraints();
        gbc.gridwidth = 3;
        gbc.weightx = 1; // 水平扩展
        gbc.weighty = 1; // 水平扩展
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 20, 10, 20); // 增加边距
        box.add(scrollPane, gbc);

//        添加工具区 ——————————————————————————
        JPanel tool_panel = new JPanel(new GridBagLayout());

        // 添加增按钮
        JButton add_button = new JButton("增加");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        tool_panel.add(add_button, gbc);

        JTextField add_TextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 0);
        tool_panel.add(add_TextField, gbc);

        add_button.addActionListener((_) -> Box_frame_ListModel.addText_setFiled(add_TextField));
        add_TextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) Box_frame_ListModel.addText_setFiled(add_TextField);
            }
        });


        // 添加删按钮
        JButton remove_button = new JButton("删除");

        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.insets=new Insets(0,20,0,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;

        remove_button.addActionListener((_) -> Box_frame_ListModel.deletes_selection(list));
        tool_panel.add(remove_button, gbc);



        // 添加清空按钮
        JButton empty_button = new JButton("清空");

        gbc = new GridBagConstraints();
        gbc.gridx=3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        gbc.insets=new Insets(0,0,0,20);

        empty_button.addActionListener((_) -> Box_frame_ListModel.clear());
        tool_panel.add(empty_button, gbc);



        // 添加确定按钮
        JButton sure_button = new JButton("确定");

        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;

        sure_button.addActionListener((_) -> this.dispose());

        tool_panel.add(sure_button, gbc);

        // 添加取消按钮
        JButton cancel_button = new JButton("取消");

        gbc.gridx=1;
        gbc.anchor=GridBagConstraints.LINE_START;
        gbc.insets=new Insets(0,10,0,0);

        cancel_button.addActionListener((_) -> {
            Box_frame_ListModel.inducts_by_array(strings);
            this.dispose();
        });
        tool_panel.add(cancel_button, gbc);


        //      添加提取方法…
        JButton export_button = new JButton("导出/导入");
        gbc = new GridBagConstraints();
        gbc.gridy=1;
        gbc.gridx=2;
        gbc.gridwidth=2;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(0,20,0,20);
        export_button.addActionListener((_) -> Sample_frame.add());
        tool_panel.add(export_button, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.insets=new Insets(0,20,20,0);
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        gbc.weightx=1;
        box.add(tool_panel, gbc);


//        结束工具区 ——————————————————————————
        add(box);
    }


}
