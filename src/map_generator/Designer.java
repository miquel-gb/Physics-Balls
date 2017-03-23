/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map_generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author PC_15
 */
public class Designer extends JSplitPane {

    // @todo: Cambiar estas listas genéricas por listas de los objetos en concreto
    private ArrayList<Rectangle> obstacles = new ArrayList<>();
    private Rectangle obstacle;
    private ArrayList<Rectangle> bottlenecks = new ArrayList<>();
    private Rectangle bottleneck;
    private ArrayList<Point> balls = new ArrayList<>();
    private Point ball;

    // @todo: esto sobrará
    private int radius = 10;

    private String item = "BALL";

    // Pila usada para el botón de deshacer
    private Stack<String> undoList = new Stack<>();

    // Los dos paneles en los que se separa la ventana, el de los controles y
    // el del mapa
    private JPanel controlPanel;
    private JPanel mapPanel;

    // Objetos de labels de información
    private JLabel ballLb;
    private JLabel obsLb;
    private JLabel bottleneckLb;
    private JLabel mapOptLb;
    private JLabel selectedItemLb;
    private JLabel posXLb;
    private JLabel posYLb;
    private JLabel widthLb;
    private JLabel heightLb;
    private JLabel radiusLb;
    private JLabel speedXLb;
    private JLabel speedYLb;

    private JTextField posXTf;
    private JTextField posYTf;
    private JTextField widthTf;
    private JTextField heightTf;

    private JFormattedTextField radiusTf;
    private JFormattedTextField speedXTf;
    private JFormattedTextField speedYTf;

    // Botones del panel de controles
    private JButton openBtn;
    private JButton saveBtn;
    private JButton undoBtn;
    private JButton ballBtn;
    private JButton obsBtn;
    private JButton eraseBtn;
    private JButton bottleneckBtn;
    private JButton configBtn;

    public Designer() throws IOException {
        super(JSplitPane.VERTICAL_SPLIT);
        setDividerSize(0);

        // El tamaño total de la ventana se partirá en los dos contenedores,
        // El primero de 1100 x 200, el segundo de 1100 x 619
        createControlPanel();
        createMapPanel();

        setLeftComponent(controlPanel);
        setRightComponent(mapPanel);
    }

    private void createControlPanel() throws IOException {
        // Botón para cambiar de item
        controlPanel = new JPanel() {
            // Este método pinta las líneas que hacen de separadores de los paneles
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.GRAY);
                g.fillRect(0, 140, 1150, 3);
                g.fillRect(220, 0, 3, 140);
                g.fillRect(364, 0, 3, 140);
            }
        };
        controlPanel.setPreferredSize(new Dimension(1100, 143));
        controlPanel.setLayout(null);

        createMapControlForm();
        createObjectParamForm();
    }

    private void createMapControlForm() {

        ballLb = new JLabel("Bola", SwingConstants.CENTER);
        ballLb.setSize(40, 15);
        ballLb.setLocation(14, 5);
        controlPanel.add(ballLb);

        // Botón para seleccionar la bola
        ballBtn = new JButton();
        ballBtn.setSize(40, 40);
        ballBtn.setLocation(15, 20);
        ballBtn.setEnabled(false);
        ballBtn.setBorder(new RoundedBorder(10));
        ballBtn.setForeground(Color.GRAY);
        ballBtn.setText("Ba");
        ballBtn.setToolTipText("Colocar bolas");
        ballBtn.addActionListener((ActionEvent e) -> {
            if (!item.equals("BALL")) {
                item = "BALL";
                ballBtn.setEnabled(false);
                obsBtn.setEnabled(true);
                bottleneckBtn.setEnabled(true);
                radiusLb.setVisible(true);
                radiusTf.setVisible(true);
                widthLb.setVisible(false);
                widthTf.setVisible(false);
                heightLb.setVisible(false);
                heightTf.setVisible(false);
                speedXLb.setVisible(true);
                speedXTf.setVisible(true);
                speedYLb.setVisible(true);
                speedYTf.setVisible(true);
                selectedItemLb.setText("Bola");
            }
        });
        controlPanel.add(ballBtn);

        obsLb = new JLabel("Obstáculo", SwingConstants.CENTER);
        obsLb.setSize(60, 15);
        obsLb.setLocation(79, 5);
        controlPanel.add(obsLb);

        // Botón para seleccionar el obstáculo
        obsBtn = new JButton(new ImageIcon("img/obs.png"));
        obsBtn.setSize(40, 40);
        obsBtn.setLocation(88, 20);
        obsBtn.setBorder(new RoundedBorder(10));
        obsBtn.setForeground(Color.GRAY);
        obsBtn.setToolTipText("Colocar obstáculos");
        obsBtn.addActionListener((ActionEvent e) -> {
            if (!item.equals("OBSTACLE")) {
                item = "OBSTACLE";
                obsBtn.setEnabled(false);
                ballBtn.setEnabled(true);
                bottleneckBtn.setEnabled(true);
                radiusLb.setVisible(false);
                radiusTf.setVisible(false);
                widthLb.setVisible(true);
                widthTf.setVisible(true);
                heightLb.setVisible(true);
                heightTf.setVisible(true);
                speedXLb.setVisible(false);
                speedXTf.setVisible(false);
                speedYLb.setVisible(false);
                speedYTf.setVisible(false);
                selectedItemLb.setText("Obstáculo");
            }
        });
        controlPanel.add(obsBtn);

        bottleneckLb = new JLabel("Semáforo", SwingConstants.CENTER);
        bottleneckLb.setSize(60, 15);
        bottleneckLb.setLocation(150, 5);
        controlPanel.add(bottleneckLb);

        // Botón para seleccionar el semáforo
        bottleneckBtn = new JButton(new ImageIcon("img/bottle.png"));
        bottleneckBtn.setSize(40, 40);
        bottleneckBtn.setLocation(161, 20);
        bottleneckBtn.setBorder(new RoundedBorder(10));
        bottleneckBtn.setForeground(Color.GRAY);
        bottleneckBtn.setToolTipText("Colocar semáforos");
        bottleneckBtn.addActionListener((ActionEvent e) -> {
            item = "BOTTLENECK";
            bottleneckBtn.setEnabled(false);
            ballBtn.setEnabled(true);
            obsBtn.setEnabled(true);
            radiusLb.setVisible(false);
            radiusTf.setVisible(false);
            widthLb.setVisible(true);
            widthTf.setVisible(true);
            heightLb.setVisible(true);
            heightTf.setVisible(true);
            speedXLb.setVisible(false);
            speedXTf.setVisible(false);
            speedYLb.setVisible(false);
            speedYTf.setVisible(false);
            selectedItemLb.setText("Semáforo");
        });
        controlPanel.add(bottleneckBtn);

        // Botón para modificar la configuración general del mapa
        configBtn = new JButton(new ImageIcon("img/settings.png"));
        configBtn.setSize(186, 40);
        configBtn.setLocation(15, 80);
        configBtn.setBorder(new RoundedBorder(10));
        configBtn.setForeground(Color.BLACK);
        configBtn.setText("Configuración general");
        configBtn.setHorizontalAlignment(SwingConstants.LEFT);
        configBtn.setToolTipText("Configuración general del escenario");
        configBtn.addActionListener((ActionEvent e) -> {

        });
        controlPanel.add(configBtn);

        mapOptLb = new JLabel("Opc. del mapa", SwingConstants.CENTER);
        mapOptLb.setSize(102, 13);
        mapOptLb.setLocation(242, 5);
        controlPanel.add(mapOptLb);

        // Botón de cargar
        openBtn = new JButton(new ImageIcon("img/open.png"));
        openBtn.setSize(40, 40);
        openBtn.setLocation(242, 20);
        openBtn.setBorder(new RoundedBorder(10));
        openBtn.setForeground(Color.GRAY);
        openBtn.setToolTipText("Cargar escenario");
        openBtn.addActionListener((ActionEvent e) -> {
            // @todo: Aquí se implementará la persistencia a la base de datos 
            // del escenario
        });
        controlPanel.add(openBtn);

        // Botón de guardar
        saveBtn = new JButton(new ImageIcon("img/save.png"));
        saveBtn.setEnabled(false);
        saveBtn.setSize(40, 40);
        saveBtn.setLocation(304, 20);
        saveBtn.setBorder(new RoundedBorder(10));
        saveBtn.setForeground(Color.GRAY);
        saveBtn.setToolTipText("Guardar escenario");
        saveBtn.addActionListener((ActionEvent e) -> {
            // @todo: Aquí se implementará la persistencia a la base de datos 
            // del escenario
        });
        controlPanel.add(saveBtn);

        // Botón de deshacer
        undoBtn = new JButton(new ImageIcon("img/undo.png"));
        undoBtn.setEnabled(false);
        undoBtn.setSize(40, 40);
        undoBtn.setLocation(242, 80);
        undoBtn.setBorder(new RoundedBorder(10));
        undoBtn.setForeground(Color.GRAY);
        undoBtn.setToolTipText("Deshacer última acción");
        undoBtn.addActionListener((ActionEvent e) -> {
            if (!undoList.isEmpty()) {
                String toUndo = undoList.pop();
                switch (toUndo) {
                    case "BALL":
                        balls.remove(balls.size() - 1);
                        break;
                    case "OBSTACLE":
                        obstacles.remove(obstacles.size() - 1);
                        break;
                    case "BOTTLENECK":
                        bottlenecks.remove(bottlenecks.size() - 1);
                        break;
                    default:
                        // nada
                        break;
                }
            }
            if (undoList.isEmpty()) {
                undoBtn.setEnabled(false);
                eraseBtn.setEnabled(false);
                saveBtn.setEnabled(false);
            }
            repaint();
        });
        controlPanel.add(undoBtn);

        // Botón de borrar al completo
        eraseBtn = new JButton(new ImageIcon("img/erase.png"));
        eraseBtn.setEnabled(false);
        eraseBtn.setSize(40, 40);
        eraseBtn.setLocation(304, 80);
        eraseBtn.setBorder(new RoundedBorder(10));
        eraseBtn.setForeground(Color.GRAY);
        eraseBtn.setToolTipText("Borrar toda la información del escenario");
        eraseBtn.addActionListener((ActionEvent e) -> {
            obstacles.clear();
            bottlenecks.clear();
            balls.clear();
            undoBtn.setEnabled(false);
            eraseBtn.setEnabled(false);
            saveBtn.setEnabled(false);
            repaint();
        });
        controlPanel.add(eraseBtn);
    }

    private void createObjectParamForm() {
        selectedItemLb = new JLabel("Bola", SwingConstants.LEFT);
        selectedItemLb.setFont(new Font(selectedItemLb.getFont().getName(), Font.BOLD, 28));
        selectedItemLb.setSize(500, 30);
        selectedItemLb.setLocation(400, 5);
        controlPanel.add(selectedItemLb);

        posXLb = new JLabel("Pos. X: ", SwingConstants.RIGHT);
        posXLb.setSize(75, 20);
        posXLb.setLocation(357, 50);
        controlPanel.add(posXLb);

        posXTf = new JTextField("");
        posXTf.setSize(75, 20);
        posXTf.setLocation(435, 50);
        posXTf.setEditable(false);
        controlPanel.add(posXTf);

        posYLb = new JLabel("Pos. Y: ", SwingConstants.RIGHT);
        posYLb.setSize(75, 20);
        posYLb.setLocation(357, 90);
        controlPanel.add(posYLb);

        posYTf = new JTextField("");
        posYTf.setSize(75, 20);
        posYTf.setLocation(435, 90);
        posYTf.setEditable(false);
        controlPanel.add(posYTf);

        widthLb = new JLabel("Ancho: ", SwingConstants.RIGHT);
        widthLb.setSize(75, 20);
        widthLb.setLocation(500, 50);
        widthLb.setVisible(false);
        controlPanel.add(widthLb);

        widthTf = new JTextField("");
        widthTf.setSize(75, 20);
        widthTf.setLocation(578, 50);
        widthTf.setEditable(false);
        widthTf.setVisible(false);
        controlPanel.add(widthTf);

        heightLb = new JLabel("Alto: ", SwingConstants.RIGHT);
        heightLb.setSize(75, 20);
        heightLb.setLocation(500, 90);
        heightLb.setVisible(false);
        controlPanel.add(heightLb);

        heightTf = new JTextField("");
        heightTf.setSize(75, 20);
        heightTf.setLocation(578, 90);
        heightTf.setEditable(false);
        heightTf.setVisible(false);
        controlPanel.add(heightTf);

        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(intFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);

        radiusLb = new JLabel("Radio: ", SwingConstants.RIGHT);
        radiusLb.setSize(75, 20);
        radiusLb.setLocation(500, 50);
        controlPanel.add(radiusLb);

        radiusTf = new JFormattedTextField(numberFormatter);
        radiusTf.setText("5");
        radiusTf.setSize(75, 20);
        radiusTf.setLocation(578, 50);
        controlPanel.add(radiusTf);

        speedXLb = new JLabel("Vel. X: ", SwingConstants.RIGHT);
        speedXLb.setSize(75, 20);
        speedXLb.setLocation(643, 50);
        controlPanel.add(speedXLb);

        speedXTf = new JFormattedTextField(numberFormatter);
        speedXTf.setText("0");
        speedXTf.setSize(75, 20);
        speedXTf.setLocation(721, 50);
        controlPanel.add(speedXTf);

        speedYLb = new JLabel("Vel. Y: ", SwingConstants.RIGHT);
        speedYLb.setSize(75, 20);
        speedYLb.setLocation(643, 90);
        controlPanel.add(speedYLb);

        speedYTf = new JFormattedTextField(numberFormatter);
        speedYTf.setText("0");
        speedYTf.setSize(75, 20);
        speedYTf.setLocation(721, 90);
        controlPanel.add(speedYTf);
    }

    private void createMapPanel() {
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintMap(g);
            }
        };
        mapPanel.setPreferredSize(new Dimension(1100, 619));
        mapPanel.setBackground(Color.LIGHT_GRAY);

        MouseAdapter ma;
        ma = new MouseAdapter() {

            private Point clickPoint;

            @Override
            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
                switch (item) {
                    case "BALL":
                        int ballRadius = Integer.parseInt(radiusTf.getText());
                        int speedX = Integer.parseInt(speedXTf.getText());
                        int speedY = Integer.parseInt(speedYTf.getText());
                        // @todo: validar también la velocidad
                        if (ballRadius < 5 || ballRadius > 80) {
                            JOptionPane.showMessageDialog(mapPanel,
                                    "El radio de la bola debe estar entre 5 y 80.",
                                    "Parámetros incorrectos",
                                    JOptionPane.ERROR_MESSAGE);
                            radiusTf.setText("5");
                            radiusTf.requestFocus();
                        } else {
                            ball = clickPoint;
                        }
                        break;
                    case "OBSTACLE":
                        obstacle = null;
                        break;
                    case "BOTTLENECK":
                        bottleneck = null;
                        break;
                    default:
                        // nada
                        break;
                }
                posXTf.setText("" + clickPoint.x);
                posYTf.setText("" + clickPoint.y);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                int x, y, width, height;
                switch (item) {
                    case "BALL":
                        ball = dragPoint;
                        posXTf.setText("" + dragPoint.x);
                        posYTf.setText("" + dragPoint.y);
                        break;
                    case "OBSTACLE":
                        x = Math.min(clickPoint.x, dragPoint.x);
                        y = Math.min(clickPoint.y, dragPoint.y);
                        width = Math.max(clickPoint.x, dragPoint.x) - x;
                        height = Math.max(clickPoint.y, dragPoint.y) - y;
                        if (width > 0 && height > 0) {
                            if (obstacle == null) {
                                obstacle = new Rectangle(x, y, width, height);
                            } else {
                                obstacle.setBounds(x, y, width, height);
                            }
                            widthTf.setText("" + width);
                            heightTf.setText("" + height);
                        }
                        break;
                    case "BOTTLENECK":
                        x = Math.min(clickPoint.x, dragPoint.x);
                        y = Math.min(clickPoint.y, dragPoint.y);
                        width = Math.max(clickPoint.x, dragPoint.x) - x;
                        height = Math.max(clickPoint.y, dragPoint.y) - y;
                        if (width > 0 && height > 0) {
                            if (bottleneck == null) {
                                bottleneck = new Rectangle(x, y, width, height);
                            } else {
                                bottleneck.setBounds(x, y, width, height);
                            }
                            widthTf.setText("" + width);
                            heightTf.setText("" + height);
                        }
                        break;
                    default:
                        // nada
                        break;
                }
                mapPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                undoList.push(item);
                switch (item) {
                    case "BALL":
                        balls.add(ball);
                        ball = null;
                        break;
                    case "OBSTACLE":
                        if (obstacle != null) {
                            obstacles.add(obstacle);
                            obstacle = null;
                        }
                        break;
                    case "BOTTLENECK":
                        if (bottleneck != null) {
                            bottlenecks.add(bottleneck);
                            bottleneck = null;
                        }
                        break;
                    default:
                        // nada
                        break;
                }
                undoBtn.setEnabled(true);
                eraseBtn.setEnabled(true);
                saveBtn.setEnabled(true);
                mapPanel.repaint();
            }

        };

        // Añade los escuchadores de clicks
        mapPanel.addMouseListener(ma);
        mapPanel.addMouseMotionListener(ma);
    }

    private void paintMap(Graphics g) {

        // @todo: Habrá que cambiar los pintados por los métodos de pintar de los objetos
        // Pintado de los objetos ya existentes
        for (Point p : balls) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLUE);
            g2d.fillOval(p.x - radius, p.y - radius, radius * 2, radius * 2);
        }
        for (Rectangle r : obstacles) {
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
            g2d.fill(r);
            g2d.dispose();
            g2d = (Graphics2D) g.create();
            g2d.draw(r);
        }
        for (Rectangle r : bottlenecks) {
            g.setColor(Color.GREEN);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
            g2d.fill(r);
            g2d.dispose();
            g2d = (Graphics2D) g.create();
            g2d.draw(r);
        }

        // Pintado del objeto seleccionado
        if (ball != null) {
            g.setColor(Color.BLUE);
            g.fillOval(ball.x - radius, ball.y - radius, radius * 2, radius * 2);
        }
        if (obstacle != null) {
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.658f));
            g2d.fill(obstacle);
            g2d.dispose();
            g2d = (Graphics2D) g.create();
            g2d.draw(obstacle);
            g2d.dispose();
        }
        if (bottleneck != null) {
            g.setColor(Color.GREEN);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.65f));
            g2d.fill(bottleneck);
            g2d.dispose();
            g2d = (Graphics2D) g.create();
            g2d.draw(bottleneck);
            g2d.dispose();
        }
    }

}
