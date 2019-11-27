import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class addFigureView extends JFrame {
    private JPanel controllersPanel = new JPanel();
    private JPanel panel = new JPanel();

    private JTextField name = new JTextField(8);
    private JButton save = new JButton("save");
    private JButton back = new JButton("back!");
    private GraphicsPanel graphicsPanel;

    private Color bcolor = Color.BLACK;
    private Color fcolor = Color.GREEN;

    addFigureView(Field field){

        this.setTitle("Add New Figure");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(bcolor);
        panel.setBackground(bcolor);

        panel.setBounds(0, getHeight() - 100, getWidth(), 50);
        panel.setLayout(new GridLayout());
        panel.add(initControllersPanel());
        graphicsPanel = new GraphicsPanel(field.getCurrentGeneration());
        graphicsPanel.setBounds((int)(getWidth() * 0.05), (int)(getHeight() * 0.025), (int)(getWidth() * 0.85), (int)(getHeight() * 0.975 - 100));

        panel.add(initControllersPanel());
        this.add(panel);
        this.add(graphicsPanel);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                graphicsPanel.setBounds((int)(getWidth() * 0.05), (int)(getHeight() * 0.025), (int)(getWidth() * 0.85), (int)(getHeight() * 0.975 - 100));
                panel.setBounds(0, getHeight() - 100, getWidth(), 50);
                graphicsPanel.update();
                graphicsPanel.update();
            }
        });
    }


    public JPanel initControllersPanel(){
        controllersPanel.setBackground(bcolor);
        name.setBackground(bcolor);
        name.setForeground(fcolor);
        save.setBackground(bcolor);
        save.setForeground(fcolor);
        back.setBackground(bcolor);
        back.setForeground(fcolor);
        controllersPanel.add(name);
        controllersPanel.add(save);
        controllersPanel.add(back);
        return controllersPanel;
    }

    public void addSaveListener(ActionListener listener) {
        save.addActionListener(listener);
    }



    public void addGraphicsPanelListener(MouseListener listener){
        graphicsPanel.addMouseListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }

    public String getName(){
        return name.getText();
    }


}