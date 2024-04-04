package logica;

import java.util.Random;

public class Juego2048 {
	int TAMANO = 4;
	int[][] tablero;
	Random rand;
	Integer turno, puntos;

	public Juego2048() {
		reiniciarJuego();
	}

	public void reiniciarJuego() {
		tablero = new int[TAMANO][TAMANO];
		turno = 0;
		puntos = 0;
		agregarNumero();
		agregarNumero();
	}

	// Agregar numero 2 o 4;
	public void agregarNumero() {
		int fila, columna;
		rand = new Random();
		do {
			fila = rand.nextInt(4); // Genera un entero aleatorio entre 0 y 3;
			columna = rand.nextInt(4); // Genera un entero aleatorio entre 0 y 3;
		} while (tablero[fila][columna] != 0); // Si existe un valor en una posicion, seguir cambiando de forma
												// aleatoria los valores de fila y columna.
		// Cuando termina de encontrar una posicion vacia, asigno de forma aleatoria los
		// valores {2,4}
		// Por defecto, cualquier posicion de una matriz tiene asignado el valor 0;
		tablero[fila][columna] = rand.nextInt(2) * 2 + 2;
	}

	public int[][] obtenerTablero() {
		return tablero;
	}

	public String obtenerTurno() {
		return turno.toString();
	}

	public boolean mover(int direccion) {
		boolean movimientoValido = false;
		switch (direccion) {
		case 1: // Arriba
			movimientoValido = moverArriba();
			break;
		case 2: // Abajo
			movimientoValido = moverAbajo();
			break;
		case 3: // Izquierda
			movimientoValido = moverIzquierda();
			break;
		case 4: // Derecha
			movimientoValido = moverDerecha();
			break;
		}
		turno++;
		return movimientoValido;
	}

	private boolean moverArriba() {
		return moverColumnas(true);
	}

	private boolean moverAbajo() {
		return moverColumnas(false);
	}

	private boolean moverIzquierda() {
		return moverFilas(true);
	}

	private boolean moverDerecha() {
		return moverFilas(false);
	}

	// Función para mover las fichas hacia arriba o hacia abajo
	private boolean moverColumnas(boolean haciaArriba) {
		boolean movimientoRealizado = false; // Variable para verificar si se realizó algún movimiento durante el
												// proceso
		for (int columna = 0; columna < TAMANO; columna++) { // Iterar sobre todas las columnas del tablero
			int filaActual = haciaArriba ? 0 : TAMANO - 1; // Establecer la fila inicial dependiendo de la dirección del
															// movimiento
			int filaIncremento = haciaArriba ? 1 : -1; // Establecer el incremento de fila dependiendo de la dirección
														// del movimiento
			for (int fila = haciaArriba ? 1 : TAMANO - 2; haciaArriba ? fila < TAMANO
					: fila >= 0; fila += filaIncremento) {
				// Iterar sobre todas las filas de la columna, empezando desde la segunda o la
				// penúltima dependiendo de la dirección del movimiento
				if (tablero[fila][columna] != 0) { // Verificar si la celda actual no está vacía
					if (tablero[filaActual][columna] == 0) { // Si la celda de destino está vacía, mover la ficha actual
																// a esa posición
						tablero[filaActual][columna] = tablero[fila][columna];
						tablero[fila][columna] = 0;
						movimientoRealizado = true; // Indicar que se realizó un movimiento
					} else if (tablero[filaActual][columna] == tablero[fila][columna]) { // Si la celda de destino
																							// contiene la misma ficha
																							// que la actual,
																							// fusionarlas
						tablero[filaActual][columna] *= 2;
						tablero[fila][columna] = 0;
						movimientoRealizado = true; // Indicar que se realizó un movimiento
						sumarPuntos(tablero[filaActual][columna]); // Sumar puntos por la fusión
						filaActual += filaIncremento; // Mover la posición de destino hacia la siguiente fila
					} else { // Si la celda de destino no está vacía pero no se puede fusionar, mover la
								// ficha actual a esa posición
						filaActual += filaIncremento; // Mover la posición de destino hacia la siguiente fila
						if (filaActual != fila) { // Verificar si la posición de destino es diferente de la posición
													// actual
							tablero[filaActual][columna] = tablero[fila][columna];
							tablero[fila][columna] = 0;
							movimientoRealizado = true; // Indicar que se realizó un movimiento
						}
					}
				}
			}
		}
		return movimientoRealizado; // Devolver si se realizó algún movimiento durante el proceso
	}

	// Función para mover las fichas hacia la izquierda o hacia la derecha
	private boolean moverFilas(boolean haciaIzquierda) {
		boolean movimientoRealizado = false; // Variable para verificar si se realizó algún movimiento durante el
												// proceso
		for (int fila = 0; fila < TAMANO; fila++) { // Iterar sobre todas las filas del tablero
			int columnaActual = haciaIzquierda ? 0 : TAMANO - 1; // Establecer la columna inicial dependiendo de la
																	// dirección del movimiento
			int columnaIncremento = haciaIzquierda ? 1 : -1; // Establecer el incremento de columna dependiendo de la
																// dirección del movimiento
			for (int columna = haciaIzquierda ? 1 : TAMANO - 2; haciaIzquierda ? columna < TAMANO
					: columna >= 0; columna += columnaIncremento) {
				// Iterar sobre todas las columnas de la fila, empezando desde la segunda o la
				// penúltima dependiendo de la dirección del movimiento
				if (tablero[fila][columna] != 0) { // Verificar si la celda actual no está vacía
					if (tablero[fila][columnaActual] == 0) { // Si la celda de destino está vacía, mover la ficha actual
																// a esa posición
						tablero[fila][columnaActual] = tablero[fila][columna];
						tablero[fila][columna] = 0;
						movimientoRealizado = true; // Indicar que se realizó un movimiento
					} else if (tablero[fila][columnaActual] == tablero[fila][columna]) { // Si la celda de destino
																							// contiene la misma ficha
																							// que la actual,
																							// fusionarlas
						tablero[fila][columnaActual] *= 2;
						tablero[fila][columna] = 0;
						movimientoRealizado = true; // Indicar que se realizó un movimiento
						sumarPuntos(tablero[fila][columnaActual]); // Sumar puntos por la fusión
						columnaActual += columnaIncremento; // Mover la posición de destino hacia la siguiente columna
					} else { // Si la celda de destino no está vacía pero no se puede fusionar, mover la
								// ficha actual a esa posición
						columnaActual += columnaIncremento; // Mover la posición de destino hacia la siguiente columna
						if (columnaActual != columna) { // Verificar si la posición de destino es diferente de la
														// posición actual
							tablero[fila][columnaActual] = tablero[fila][columna];
							tablero[fila][columna] = 0;
							movimientoRealizado = true; // Indicar que se realizó un movimiento
						}
					}
				}
			}
		}
		return movimientoRealizado; // Devolver si se realizó algún movimiento durante el proceso
	}

	private void sumarPuntos(int valor) {
		puntos += valor; 
	}

	public String obtenerPuntosString() {
		return puntos.toString();
	}

	public int obtenerPuntosInt() {
		return puntos;
	}

	public boolean verificarVictoria() {
		for (int fila = 0; fila < TAMANO; fila++) {
			for (int columna = 0; columna < TAMANO; columna++) {
				if (tablero[fila][columna] == 2048) {
					return true; // Se llegó el valor 2048
				}
			}
		}
		return false;
	}

	public boolean verificarDerrota() {
		for (int fila = 0; fila < TAMANO; fila++) {
			for (int columna = 0; columna < TAMANO; columna++) {
				if (tablero[fila][columna] == 0) {
					return false; // Hay espacios vacíos
				}
				if (fila < TAMANO - 1 && tablero[fila][columna] == tablero[fila + 1][columna]) {
					return false; // Hay un movimiento posible en vertical
				}
				if (columna < TAMANO - 1 && tablero[fila][columna] == tablero[fila][columna + 1]) {
					return false; // Hay una movimiento posible en horizontal
				}
			}
		}
		return true; // No hay movimientos posibles
	}

}

//FUNCIONES DE MOVIMIENTO ANTERIORES QUE DAN ERROR EN LOS TEST YA QUE FUSIONAN NUMEROS DE MAS EN UN MISMO MOVIMEINTO
//LAS FUNCIONES QUE ESTAN ACTUALMENTE EN EL CODIGO SON HORRIBLES PERO PARECEN FUNCIONAR BIEN
/**
 * public boolean moverArriba() { boolean movimientoValido = false; for (int
 * columna = 0; columna < TAMANO; columna++) { movimientoValido |=
 * moverColumnaArriba(columna); } return movimientoValido; }
 * 
 * private boolean moverColumnaArriba(int columna) { boolean movimientoValido =
 * false; for (int fila = 1; fila < TAMANO; fila++) { if (tablero[fila][columna]
 * != 0) { movimientoValido |= moverFichaHaciaArriba(columna, fila); } } return
 * movimientoValido; }
 * 
 * private boolean moverFichaHaciaArriba(int columna, int fila) { boolean
 * movimientoValido = false; int filaActual = fila; while (filaActual > 0 &&
 * (tablero[filaActual - 1][columna] == 0 || tablero[filaActual - 1][columna] ==
 * tablero[filaActual][columna])) { if (tablero[filaActual - 1][columna] == 0) {
 * tablero[filaActual - 1][columna] = tablero[filaActual][columna];
 * tablero[filaActual][columna] = 0; filaActual--; movimientoValido = true; }
 * else if (tablero[filaActual - 1][columna] == tablero[filaActual][columna]) {
 * tablero[filaActual - 1][columna] *= 2; tablero[filaActual][columna] = 0;
 * movimientoValido = true; sumarPuntos(tablero[filaActual - 1][columna]);
 * break; } } return movimientoValido; }
 * 
 * public boolean moverIzquierda() { boolean movimientoValido = false; for (int
 * fila = 0; fila < TAMANO; fila++) { movimientoValido |=
 * moverFilaIzquierda(fila); } return movimientoValido; }
 * 
 * private boolean moverFilaIzquierda(int fila) { boolean movimientoValido =
 * false; for (int columna = 1; columna < TAMANO; columna++) { if
 * (tablero[fila][columna] != 0) { movimientoValido |=
 * moverFichaHaciaIzquierda(fila, columna); } } return movimientoValido; }
 * 
 * private boolean moverFichaHaciaIzquierda(int fila, int columna) { boolean
 * movimientoValido = false; int columnaActual = columna; while (columnaActual >
 * 0 && (tablero[fila][columnaActual - 1] == 0 || tablero[fila][columnaActual -
 * 1] == tablero[fila][columnaActual])) { if (tablero[fila][columnaActual - 1]
 * == 0) { tablero[fila][columnaActual - 1] = tablero[fila][columnaActual];
 * tablero[fila][columnaActual] = 0; columnaActual--; movimientoValido = true; }
 * else if (tablero[fila][columnaActual - 1] == tablero[fila][columnaActual]) {
 * tablero[fila][columnaActual - 1] *= 2; tablero[fila][columnaActual] = 0;
 * movimientoValido = true; sumarPuntos(tablero[fila][columnaActual - 1]);
 * break; } } return movimientoValido; }
 * 
 * private boolean moverDerecha() { boolean movimientoValido = false; for (int
 * fila = 0; fila < TAMANO; fila++) { movimientoValido |=
 * moverFilaDerecha(fila); } return movimientoValido; }
 * 
 * private boolean moverFilaDerecha(int fila) { boolean movimientoValido =
 * false; for (int columna = TAMANO - 2; columna >= 0; columna--) { if
 * (tablero[fila][columna] != 0) { movimientoValido |=
 * moverFichaHaciaDerecha(fila, columna); } } return movimientoValido; }
 * 
 * private boolean moverFichaHaciaDerecha(int fila, int columna) { boolean
 * movimientoValido = false; int columnaActual = columna; while (columnaActual <
 * TAMANO - 1 && (tablero[fila][columnaActual + 1] == 0 ||
 * tablero[fila][columnaActual + 1] == tablero[fila][columnaActual])) { if
 * (tablero[fila][columnaActual + 1] == 0) { tablero[fila][columnaActual + 1] =
 * tablero[fila][columnaActual]; tablero[fila][columnaActual] = 0;
 * columnaActual++; movimientoValido = true; } else if
 * (tablero[fila][columnaActual + 1] == tablero[fila][columnaActual]) {
 * tablero[fila][columnaActual + 1] *= 2; tablero[fila][columnaActual] = 0;
 * movimientoValido = true; sumarPuntos(tablero[fila][columnaActual + 1]);
 * break; } } return movimientoValido; }
 * 
 * public boolean moverAbajo() { boolean movimientoValido = false; for (int
 * columna = 0; columna < TAMANO; columna++) { movimientoValido |=
 * moverColumnaAbajo(columna); } return movimientoValido; }
 * 
 * private boolean moverColumnaAbajo(int columna) { boolean movimientoValido =
 * false; for (int fila = TAMANO - 2; fila >= 0; fila--) { if
 * (tablero[fila][columna] != 0) { movimientoValido |=
 * moverFichaHaciaAbajo(columna, fila); } } return movimientoValido; }
 * 
 * private boolean moverFichaHaciaAbajo(int columna, int fila) { boolean
 * movimientoValido = false; int filaActual = fila; while (filaActual < TAMANO -
 * 1 && (tablero[filaActual + 1][columna] == 0 || tablero[filaActual +
 * 1][columna] == tablero[filaActual][columna])) { if (tablero[filaActual +
 * 1][columna] == 0) { tablero[filaActual + 1][columna] =
 * tablero[filaActual][columna]; tablero[filaActual][columna] = 0; filaActual++;
 * movimientoValido = true; } else if (tablero[filaActual + 1][columna] ==
 * tablero[filaActual][columna]) { tablero[filaActual + 1][columna] *= 2;
 * tablero[filaActual][columna] = 0; movimientoValido = true;
 * sumarPuntos(tablero[filaActual + 1][columna]); // Corregido: usar el valor de
 * la ficha actual break; } } return movimientoValido; }
 **/