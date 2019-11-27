import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

public class GameOfLifeMainView extends JFrame{

    private ImageIcon iconPause = new ImageIcon(GameOfLifeMainView.class.getResource("images/pause.png"));
    private ImageIcon iconPlay = new ImageIcon(GameOfLifeMainView.class.getResource("images/play.png"));
    private ImageIcon iconArrow = new ImageIcon(GameOfLifeMainView.class.getResource("images/arrow.png"));
    private ImageIcon iconRestart = new ImageIcon(GameOfLifeMainView.class.getResource("images/restart.png"));
    private ImageIcon iconLinesOn= new ImageIcon(GameOfLifeMainView.class.getResource("images/lines_off.png"));
    private ImageIcon iconLinesOff= new ImageIcon(GameOfLifeMainView.class.getResource("images/lines_on.png"));
    private ImageIcon iconRotateLeft = new ImageIcon(GameOfLifeMainView.class.getResource("images/rotateLeft.png"));
    private ImageIcon iconRotateRight = new ImageIcon(GameOfLifeMainView.class.getResource("images/rotateRight.png"));
    private ImageIcon iconReverse = new ImageIcon(GameOfLifeMainView.class.getResource("images/reverse.png"));
    private ImageIcon iconChecked = new ImageIcon(GameOfLifeMainView.class.getResource("images/Radio2.png"));
    private ImageIcon iconUnChecked = new ImageIcon(GameOfLifeMainView.class.getResource("images/Radio1.png"));
    private ImageIcon iconInsert = new ImageIcon(GameOfLifeMainView.class.getResource("images/insert.png"));

    private Font fontName = new Font("Lato", Font.BOLD, 48);
    private Font fontLittle = new Font("Lato", Font.BOLD, 24);
    private Font fontData = new Font("Lato", Font.BOLD, 24);

    private JButton back = new JButton("back!");

    private JLabel name = new JLabel("GAME OF LIFE");

    private JPanel buttonsPanel = new JPanel();
    private JPanel figureButtonPanel = new JPanel();
    private JButton pause = new JButton(iconPlay);
    private JButton restart = new JButton(iconRestart);
    private JButton arrow = new JButton(iconArrow);
    private JButton lines = new JButton(iconLinesOn);


    private JButton rotateLeft = new JButton(iconRotateLeft);
    private JButton rotateRight = new JButton(iconRotateRight);
    private JButton reverse = new JButton(iconReverse);
    private JButton insert = new JButton(iconInsert);

    private JPanel dataPanel = new JPanel();

    private JLabel generationNumber = new JLabel("Generation #1");
    private JLabel alive = new JLabel("Alive: 0");
    private JSlider slider = new JSlider();
    private JPanel controllersPanel = new JPanel();
    private JPanel panel = new JPanel();

    private Color bcolor = Color.BLACK;
    private Color fcolor = Color.GREEN;

    private ButtonGroup group = new ButtonGroup();
    private JPanel figuresPanel = new JPanel();
    private JPanel figuresListPanel = new JPanel();

    private JPanel coordinatesPanel = new JPanel();
    private JTextField xCor = new JTextField(3);
    private JTextField yCor = new JTextField(3);

    private JScrollPane figuresScrollPane = new JScrollPane();

    private JCheckBox killEnvCheckBox = new JCheckBox("Kill Env");

    private GraphicsPanel graphicsPanel;
    private GraphicsPanel figureGraphicsPanel;

    GameOfLifeMainView(Field field) {

        this.setTitle("Game Of Life");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(bcolor);
        panel.setBounds(0, 0, this.getWidth() / 3, this.getHeight());
        panel.setBackground(bcolor);
        panel.setLayout(new GridLayout());
        panel.add(initControllersPanel());

        graphicsPanel = new GraphicsPanel(field.getCurrentGeneration());
        graphicsPanel.setBounds(this.getWidth() / 3 + 10, (int) (getHeight() * 0.025), (int) (this.getWidth() * 0.6), (int) (this.getHeight() * 0.9));

        this.add(panel);
        this.add(graphicsPanel);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                //name.setFont(name.getX() + name.getWidth() > graphicsPanel.getX() ? fontNameLittle : fontName);
                graphicsPanel.setBounds((Math.max((int) (getWidth() / 3), 240) + 10), 8, (int) (getWidth() - (Math.max((int) (getWidth() / 3), 240) + 30)), (int) (getHeight() - 50));
                graphicsPanel.update();
                panel.setSize((Math.max(getWidth() - graphicsPanel.getWidth() - 40, 240)), getHeight());
                figureGraphicsPanel.setBounds(0, 0, (int) (figuresPanel.getWidth() - figuresListPanel.getWidth() - 20), (int) (figuresPanel.getHeight()));
                figureGraphicsPanel.update();
            }
        });

        this.addWindowStateListener((WindowStateListener) e -> {
            graphicsPanel.setBounds((Math.max((int) (getWidth() / 3), 240) + 10), 8, (int) (getWidth() - (Math.max((int) (getWidth() / 3), 240) + 30)), (int) (getHeight() - 50));
            graphicsPanel.update();
            panel.setSize((Math.max(getWidth() - graphicsPanel.getWidth() - 40, 240)), getHeight());
            figureGraphicsPanel.setBounds(0, 0, (int) (figuresPanel.getWidth() - figuresListPanel.getWidth() - 20), (int) (figuresPanel.getHeight()));
            figureGraphicsPanel.update();
        });

    }


    public JPanel initControllersPanel(){
        controllersPanel.setLayout(new GridLayout(6, 0));
        controllersPanel.setBackground(bcolor);
        controllersPanel.add(initButtonsPanel());
        controllersPanel.add(initDataPanel());
        controllersPanel.add(initSlider());
        controllersPanel.add(initFiguresPanel());

        controllersPanel.add(initFigureButtonsPanel());
        controllersPanel.add(initName());
        return controllersPanel;
    }

    public JPanel initButtonsPanel(){
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(bcolor);
        Dimension dim = new Dimension(74, 74);
        pause.setPreferredSize(dim);
        restart.setPreferredSize(dim);
        arrow.setPreferredSize(dim);
        lines.setPreferredSize(dim);
        back.setPreferredSize(dim);

        pause.setBackground(fcolor);
        restart.setBackground(fcolor);
        arrow.setBackground(fcolor);
        lines.setBackground(fcolor);
        back.setBackground(fcolor);

        buttonsPanel.add(arrow);
        buttonsPanel.add(pause);
        buttonsPanel.add(restart);
        buttonsPanel.add(lines);
        buttonsPanel.add(back);
        return buttonsPanel;
    }

    public JPanel initFigureButtonsPanel(){
        figureButtonPanel.setLayout(new FlowLayout());

        figureButtonPanel.setBackground(bcolor);
        killEnvCheckBox.setBackground(bcolor);
        killEnvCheckBox.setForeground(fcolor);

        rotateLeft.setMargin(new Insets(2,2,2,2));
        rotateRight.setMargin(new Insets(2,2,2,2));
        reverse.setMargin(new Insets(2,2,2,2));

        rotateLeft.setBackground(fcolor);
        rotateRight.setBackground(fcolor);
        reverse.setBackground(fcolor);

        figureButtonPanel.add(rotateLeft);
        figureButtonPanel.add(reverse);
        figureButtonPanel.add(rotateRight);
        figureButtonPanel.add(killEnvCheckBox);
        figureButtonPanel.add(initCoordinatesPanel());
        return figureButtonPanel;
    }

    public JPanel initCoordinatesPanel(){
        coordinatesPanel.setBackground(bcolor);

        insert.setMargin(new Insets(2,2,2,2));

        insert.setBackground(bcolor);
        JLabel xCorLabel = new JLabel("X:");
        JLabel yCorLabel = new JLabel("Y:");
        xCorLabel.setForeground(fcolor);
        yCorLabel.setForeground(fcolor);

        xCor.setText("0");
        yCor.setText("0");
        xCor.setBackground(bcolor);
        yCor.setBackground(bcolor);
        xCor.setForeground(fcolor);
        yCor.setForeground(fcolor);
        xCor.setBorder(null);
        yCor.setBorder(null);

        coordinatesPanel.add(xCorLabel);
        coordinatesPanel.add(xCor);
        coordinatesPanel.add(yCorLabel);
        coordinatesPanel.add(yCor);
        coordinatesPanel.add(insert);
        return coordinatesPanel;
    }

    public JPanel initDataPanel(){

        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(bcolor);
        generationNumber.setFont(fontData);
        generationNumber.setForeground(fcolor);
        alive.setFont(fontData);
        alive.setForeground(fcolor);

        generationNumber.setName("GenerationLabel");
        alive.setName("AliveLabel");

        dataPanel.add(generationNumber);
        dataPanel.add(alive);
        return dataPanel;
    }




    public JSlider initSlider(){
        slider.setBackground(bcolor);
        slider.setForeground(fcolor);
        slider.setMinimum(0);
        slider.setMaximum(100);
        slider.setValue(50);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(5);

        return slider;
    }

    public JScrollPane initFiguresScrollPane(){
        figuresScrollPane.setBackground(bcolor);
        figuresListPanel.setBackground(bcolor);
        figuresListPanel.setLayout(new BoxLayout(figuresListPanel, BoxLayout.Y_AXIS));
        figuresScrollPane.setViewportView(figuresListPanel);
        figuresScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        figuresScrollPane.getVerticalScrollBar().setBackground(fcolor);

        return figuresScrollPane;
    }

    public JPanel initFiguresPanel(){

        figuresPanel.setBackground(bcolor);
        figuresPanel.setLayout(new BoxLayout(figuresPanel, BoxLayout.X_AXIS));
        figuresPanel.add(initFigureGraphicsPanel());
        figuresPanel.add(initFiguresScrollPane());
        return figuresPanel;
    }

    public JPanel initFigureGraphicsPanel(){
        figureGraphicsPanel = new GraphicsPanel(new boolean[][]{{false}});
        figureGraphicsPanel.setBackground(bcolor);
        figureGraphicsPanel.setBounds(0,0,(int)(figuresPanel.getWidth() - figuresListPanel.getWidth()), (int)(figuresPanel.getHeight()));
        figureGraphicsPanel.update();
        return figureGraphicsPanel;
    }

    public JLabel initName(){
        name.setForeground(fcolor);
        name.setBounds((int)(panel.getHeight() * 0.1) - name.getHeight(), panel.getHeight() - (int)(panel.getHeight() * 0.1) - name.getHeight(), 350, 48);
        name.setFont(fontName);
        return name;
    }

    public void addFigure(String name, ActionListener listener){
        if (name.indexOf(".") > 0)
            name = name.substring(0, name.lastIndexOf("."));


        JRadioButton button = new JRadioButton(name, false);
        button.setIcon(iconUnChecked);
        button.setSelectedIcon(iconChecked);
        group.add(button);
        if(name.equals("Cell")){
            button.setSelected(true);
        }
        button.setBackground(bcolor);
        button.setForeground(fcolor);
        button.addActionListener(listener);
        figuresListPanel.add(button);
    }



    public void addPauseListener(ActionListener listener) {
        pause.addActionListener(listener);
    }

    public void addRestartListener(ActionListener listener) {
        restart.addActionListener(listener);
    }

    public void addStepListener(ActionListener listener) {
        arrow.addActionListener(listener);
    }

    public void addLinesListener(ActionListener listener) {
        lines.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public void addRotateListener(ActionListener listener) {
        rotateLeft.addActionListener(listener);
        rotateRight.addActionListener(listener);
    }

    public void addReverseListener(ActionListener listener) {
        reverse.addActionListener(listener);
    }

    public void addInsertListener(ActionListener listener) {
       insert.addActionListener(listener);
    }

    public void addSliderListener(ChangeListener listener) {
        slider.addChangeListener(listener);
    }

    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }

    public void addGraphicsPanelListener(EventListener listener){
        graphicsPanel.addMouseListener((MouseListener)listener);
        graphicsPanel.addMouseMotionListener((MouseMotionListener) listener);
    }

    public void setIconPause(boolean isPaused){
        pause.setIcon(isPaused ? iconPause : iconPlay);
    }

    public void setIconLines(boolean drawLines){
        lines.setIcon(drawLines ? iconLinesOff : iconLinesOn);
    }

    public int getSliderValue(){
        return slider.getValue();
    }

    public boolean getKillEnvValue(){
        return killEnvCheckBox.isSelected();
    }

    public void updateFigure(boolean[][] figure, boolean drawLines){
        figureGraphicsPanel.setBounds(0,0,(int)(figuresPanel.getWidth() - figuresListPanel.getWidth() - 20), (int)(figuresPanel.getHeight()));
        figureGraphicsPanel.update(figure, drawLines);
        figureGraphicsPanel.update();
    }

    public int getAngle(JButton btn){
        return btn == rotateLeft ? 90 : -90;
    }

    public String getXCor(){
        return (xCor.getText());
    }

    public String getYCor(){
        return (yCor.getText());
    }

    public void setCors(int x, int y){
        xCor.setText(String.valueOf(x));
        yCor.setText(String.valueOf(y));
    }

    public void setGenerationNumber(int n){
        generationNumber.setText("Generation #" + n);
    }

    public void setCurrentAlive(int n){
        alive.setText("Alive:" + n);
    }

}
