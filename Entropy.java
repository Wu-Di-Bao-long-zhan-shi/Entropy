package useful.Random;

import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static useful.Random.Box_frame.Box_frame_ListModel;


public class Entropy extends JFrame {
    final static String RELATIVE_PATH = Objects.requireNonNull(Entropy.class.getResource("/")).getPath().substring(1);

    final static String GAME_NAME = "Random";
    final static String SAMPLE_PATH = RELATIVE_PATH+"useful/Random/sample/";
    final static String ICON_IMAGE = "src/useful/Random/sieve.jpg";

    public static Image get_icon_image() {
        try {
             return ImageIO.read(new File(ICON_IMAGE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File get_sample_path(String name){
        return new File("src/useful/Random/sample/"+name);
    }


    public static void main(String[] args) {
        new Entropy();
    }

    public Entropy() {
        FlatLightLaf.setup();  // 导入美化包
        init();
        drawMain();
        setVisible(true);
    }


    private void drawMain() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());

        JLabel random_label = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(random_label, gbc);

        JButton start_button = new JButton("开始抽取");
        start_button.addActionListener((_) -> Box_frame.getBox_frame_ListModel().set_random_text(random_label));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(start_button, gbc);

        JButton add_box_setting = new JButton("查看样本");
        add_box_setting.addActionListener((_) ->Box_frame.add());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(add_box_setting, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(50, 0, 0, 0);
        add(panel, gbc);
    }




    public void init() {
        setTitle(GAME_NAME);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setIconImage(get_icon_image());
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Box_frame_ListModel.export(get_sample_path("cache/cache.txt"));
            }
        });
    }
}