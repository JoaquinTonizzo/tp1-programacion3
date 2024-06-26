package interfaz;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import logica.Juego2048;
import logica.Ranking;

public class UI2048 {
	private JFrame frame;
	private JLabel[][] tableroIG;
	private JLabel nroTurno;
	private JLabel valorPuntaje;
	private Juego2048 juego2048;
	private boolean movimientoProcesado;
	private Ranking ranking;

	private static final Color[] COLORES_NUMEROS = { new Color(0xFFFFFF), // Color 2
			new Color(0xEDE0C8), // Color 4
			new Color(0xF2B179), // Color 8
			new Color(0xF59563), // Color 16
			new Color(0xF67C5F), // Color 32
			new Color(0xF65E3B), // Color 64
			new Color(0xEDCF72), // Color 128
			new Color(0xEDCC61), // Color 256
			new Color(0xEDC850), // Color 512
			new Color(0xEDC53F), // Color 1024
			new Color(0xEDC22E) // Color 2048
	};

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI2048 window = new UI2048();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UI2048() {
		iniciarMenu();
	}

	private void iniciarMenu() {
		setearVentana("Menu 2048");
		this.ranking = new Ranking();
		ranking.cargarRanking();

		JPanel panelPrincipal = crearPanel(new Color(0xEDE0C8), new BorderLayout(0, 0));
		frame.getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		JLabel lblTitulo = crearJLabel("2048", Color.getColor("0xEDC22E"), new Font("Tahoma", Font.BOLD, 150), SwingConstants.CENTER);
		panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

		JPanel panelBotones = crearPanel(new Color(0xEDE0C8), new GridLayout(3, 1, 0, 15));
		panelPrincipal.add(panelBotones, BorderLayout.CENTER);

		agregarBoton(panelBotones, "Jugar", new Color(0xF59563), Color.WHITE, e -> iniciarJuego());
		agregarBoton(panelBotones, "Ranking", new Color(0xEDC850), Color.WHITE, e -> mostrarRanking());
		agregarBoton(panelBotones, "Salir", new Color(0xF65E3B), Color.WHITE, e -> salirDelJuego());
	}

	private void iniciarJuego() {
		frame.setVisible(false); // Cierra la ventana del menu

		juego2048 = new Juego2048();
		movimientoProcesado = false;

		setearVentana("Juego 2048");

		JPanel panelSuperior = crearPanel(new Color(250, 248, 239), new Dimension(frame.getWidth(), 100), null);
		frame.getContentPane().add(panelSuperior, BorderLayout.NORTH);

		JPanel panelPuntaje = crearPanel(new Color(205, 193, 180), new Dimension(68, 53), new Point(219, 22),
				new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelSuperior.add(panelPuntaje);

		JPanel panelTurno = crearPanel(new Color(205, 193, 180), new Dimension(68, 53), new Point(297, 22),
				new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelSuperior.add(panelTurno);
		
		JPanel panelMatriz = crearPanel(new Color(187, 173, 160), new GridLayout(4, 4));
		panelMatriz.setBorder(new LineBorder(new Color(187, 173, 160), 4));
		frame.getContentPane().add(panelMatriz, BorderLayout.CENTER);
		
		setearJLabels(panelSuperior, panelPuntaje, panelTurno);	

		generarCeldas(panelMatriz);

		actualizarValoresEnTablero(juego2048.obtenerTablero());

		detectarTecla();
	}

	private void setearJLabels(JPanel panelSuperior, JPanel panelPuntaje, JPanel panelTurno) {
		JLabel puntaje = crearJLabel("Puntaje", new Color(250, 248, 239), new Font("Tahoma", Font.BOLD, 14), SwingConstants.CENTER);
	    panelPuntaje.add(puntaje);

	    valorPuntaje = crearJLabel(juego2048.obtenerPuntosString(), new Color(255, 255, 255), new Font("Tahoma", Font.BOLD, 14), SwingConstants.CENTER);
	    panelPuntaje.add(valorPuntaje);

	    JLabel lblTurno = crearJLabel("N-Turno", new Color(250, 248, 239), new Font("Tahoma", Font.BOLD, 14), SwingConstants.CENTER);
	    panelTurno.add(lblTurno);

	    nroTurno = crearJLabel(juego2048.obtenerTurno(), new Color(255, 255, 255), new Font("Tahoma", Font.BOLD, 14), SwingConstants.CENTER);
	    panelTurno.add(nroTurno);

	    JLabel titulo2048 = crearJLabel("2048", new Color(119, 110, 101), new Font("Tahoma", Font.BOLD, 56), SwingConstants.CENTER);
	    titulo2048.setBounds(10, 0, 156, 96);
	    panelSuperior.add(titulo2048);
	}

	private void detectarTecla() {
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!movimientoProcesado) {
					int direccion = 0;
					switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						direccion = 1;
						break;
					case KeyEvent.VK_DOWN:
						direccion = 2;
						break;
					case KeyEvent.VK_LEFT:
						direccion = 3;
						break;
					case KeyEvent.VK_RIGHT:
						direccion = 4;
						break;
					}
					if (direccion != 0) {
						mover(direccion);
						movimientoProcesado = true;
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				movimientoProcesado = false;
			}
		});
	}

	private void mover(int direccion) {
		if (juego2048.mover(direccion)) {
			juego2048.agregarNumero();
			actualizarValoresEnTablero(juego2048.obtenerTablero());
			verificarEstadoJuego();
		}
	}

	private void setearVentana(String nombreVentana) {
		frame = new JFrame();
		frame.setTitle(nombreVentana);
		frame.setBounds(100, 100, 400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ranking.guardarRanking();
				if (nombreVentana == "Juego 2048") {
					consultarNombreParaRanking();
				}
			}
		});
	}
	
	private JLabel crearJLabel(String texto, Color colorTexto, Font fuente, int alineacion) {
	    JLabel label = new JLabel(texto);
	    label.setForeground(colorTexto);
	    label.setFont(fuente);
	    label.setHorizontalAlignment(alineacion);
	    return label;
	}

	private JPanel crearPanel(Color colorFondo, LayoutManager layoutManager) {
		JPanel panel = new JPanel();
		panel.setBackground(colorFondo);
		panel.setLayout(layoutManager);
		return panel;
	}

	private JPanel crearPanel(Color colorFondo, Dimension dimension, LayoutManager layoutManager) {
		JPanel panel = crearPanel(colorFondo, layoutManager);
		panel.setPreferredSize(dimension);
		return panel;
	}

	private JPanel crearPanel(Color colorFondo, Dimension dimension, Point posicion, LayoutManager layoutManager) {
		JPanel panel = crearPanel(colorFondo, dimension, layoutManager);
		panel.setBounds(posicion.x, posicion.y, dimension.width, dimension.height);
		return panel;
	}

	private void agregarBoton(JPanel panel, String texto, Color colorFondo, Color colorTexto,
			ActionListener actionListener) {
		JButton boton = new JButton(texto);
		boton.setPreferredSize(new Dimension(150, 50));
		boton.setBackground(colorFondo);
		boton.setForeground(colorTexto);
		boton.setFont(new Font("Tahoma", Font.BOLD, 20));
		boton.addActionListener(actionListener);
		panel.add(boton);
	}

	private void generarCeldas(JPanel panelMatriz) {
		// GENERACION DE CELDAS
		tableroIG = new JLabel[4][4];

		for (int fila = 0; fila < 4; fila++) {
			for (int columna = 0; columna < 4; columna++) {
				tableroIG[fila][columna] = new JLabel(); // Asigno la etiqueta
				tableroIG[fila][columna].setHorizontalAlignment(SwingConstants.CENTER); // Centro el texto de forma
																						// horizontal
				tableroIG[fila][columna].setOpaque(true);
				tableroIG[fila][columna].setFont(new Font("Arial", Font.BOLD, 24)); // Asigno tamanio y fuente de letra.
				tableroIG[fila][columna].setBorder(new LineBorder(new Color(187, 173, 160), 4));
				tableroIG[fila][columna].setBackground(new Color(205, 193, 180));
				panelMatriz.add(tableroIG[fila][columna]); // Agrego cada etiqueta creada al panel.
			}
		}
	}

	private void actualizarValoresEnTablero(int[][] tablero) {
		// Recorro el tablero para obtener sus valores y agregarlos a la interfaz
		// grafica
		for (int fila = 0; fila < 4; fila++) {
			for (int columna = 0; columna < 4; columna++) {
				if (tablero[fila][columna] == 0) {
					tableroIG[fila][columna].setText(""); // Dejo vacias las posiciones que no tienen valor.
				} else {
					tableroIG[fila][columna].setText("" + tablero[fila][columna]); // Le asigno el valor, convirtiendolo
																					// a string.
				}
				actualizarColorEtiqueta(tableroIG[fila][columna], tablero[fila][columna]);
			}
		}
		valorPuntaje.setText(juego2048.obtenerPuntosString());
		nroTurno.setText(juego2048.obtenerTurno());
	}

	private void actualizarColorEtiqueta(JLabel etiqueta, int valor) {
		if (valor == 0) {
			etiqueta.setBackground(Color.LIGHT_GRAY);
		} else {
			int indiceColor = (int) (Math.log(valor) / Math.log(2)) - 1;
			etiqueta.setBackground(COLORES_NUMEROS[indiceColor]);
		}
	}

	private void verificarEstadoJuego() {
		if (juego2048.verificarVictoria()) {
			mostrarMensajeJuegoTerminado("¡Ganaste! ¿Qué deseas hacer?", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
		} else if (juego2048.verificarDerrota()) {
			mostrarMensajeJuegoTerminado("¡Perdiste! ¿Qué deseas hacer?", "¡Derrota!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mostrarMensajeJuegoTerminado(String mensaje, String titulo, int tipoMensaje) {
		int choice = JOptionPane.showOptionDialog(frame, mensaje, titulo, JOptionPane.YES_NO_OPTION, tipoMensaje, null,
				new String[] { "Volver a jugar", "Salir" }, "Volver a jugar");
		if (choice == JOptionPane.YES_OPTION) {
			consultarNombreParaRanking();
			juego2048.reiniciarJuego();
			valorPuntaje.setText("0");
			nroTurno.setText("0");
			actualizarValoresEnTablero(juego2048.obtenerTablero());
		} else {
			consultarNombreParaRanking();
			salirDelJuego();
		}
	}

	private void mostrarRanking() {
		JOptionPane.showMessageDialog(frame, ranking.mostrarRanking(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
	}

	private void consultarNombreParaRanking() {
		String nombreJugador = JOptionPane.showInputDialog(frame, "Ingresa tu nombre:");
		if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
			System.exit(0);
		}
		ranking.agregarAlRanking(nombreJugador, juego2048.obtenerPuntosInt());
		ranking.guardarRanking();
	}

	private void salirDelJuego() {
		ranking.guardarRanking();
		System.exit(0);
	}

}
