/*
Algoritimos em Grafos 2/2017
Nome:Otto Wilke Diniz Mariani Bittencourt
Matricula:504654
*/

/*
CORES:
0 - Branco
1 - Cinza
2 - Preto
 */

import java.io.*;
import java.*;
import java.nio.charset.*;
import java.util.Formatter;
import java.util.Scanner;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


class Grafos {
	private boolean tipo;
	private int numVertices;
	private int [][] matriz;
	private int [] cor;
	private int [] distancia;
	private int [] pai;
	Queue<Integer> fila;

	ArrayList<Integer> s;
	ArrayList<Integer> l;

	int tatata;


	/**
	 * Metodo de contrcao
	 * @param  otipo        tipo
	 * @param  onumVertices n de vertices
	 * @return
	 */
	public Grafos(boolean otipo, int onumVertices) {
		tipo = otipo;
		numVertices = onumVertices;
		matriz = new int [numVertices][numVertices];
		enchercomzero();
	}

	/**
	 * Metodo de construcao
	 * @param  otipo        tipo de grafo
	 * @param  onumVertices n de vertices
	 * @param  asRelacoes   relacoes dos vertices
	 * @return
	 */
	public Grafos(boolean otipo, int onumVertices, String[] asRelacoes) {
		tipo = otipo;
		numVertices = onumVertices;
		matriz = new int [numVertices][numVertices];
		cor = new int[numVertices];
		distancia = new int[numVertices];
		pai = new int[numVertices];

		enchercomzero();
		String[] spli;
		int origem, destino, peso;
		for (int i = 0; asRelacoes[i] != null; i++) {
			spli = asRelacoes[i].split(",");
			origem = Integer.parseInt(spli[0]);
			destino = Integer.parseInt(spli[1]);
			peso = Integer.parseInt(spli[2]);
			encherMatriz(origem, destino, peso);
		}

		for ( int i = 0; i < numVertices; i ++ ) {
			cor[i] = 0;
			distancia[i] = 9999;
			pai[i] = -1;
		}
		cor[0] = 1;
		distancia[0] = 0;
		fila = new ArrayDeque<>();


	}

	/**
	 * Metodo de encher com zeros
	 */
	public void enchercomzero() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				matriz[i][j] = 0;
			}
		}
	}

	/**
	 * Achar vertices sem entrada
	 */
	public void verticesSemEntrada() {

		int cont = 0;
		for (int coluna = 0; coluna < numVertices ; coluna++ ) {
			cont = 0;
			for (int linha = 0; linha < numVertices ; linha++ ) {
				if (linha != coluna && matriz[coluna][linha] == 1) {
					linha = matriz.length;
				} else if (linha != coluna && matriz[coluna][linha] == 0) {
					cont++;
				}
			}
			if (cont == numVertices - 1) {
				s.add(coluna);
				tatata++;
			}
		}
	}

	/**
	 * Metodo para achar vertices sem arco de entrada
	 * @param  dest vertice de destino
	 * @return
	 */
	public boolean temArcoDeEntrada(int dest) {
		boolean resp = false;
		int cont = 0;

		for (int ori = 0; ori < matriz.length ; ori++ ) {
			if (matriz[dest][ori] == 0) {
				cont++;
			}
		}
		if (cont == matriz.length) {
			resp = true;
		}
		return resp;
	}


	/**
	 * Metodo para ver se a matriz esta vazia
	 * @return
	 */
	public boolean vazio () {
		boolean resp = true;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++ ) {
				if (matriz[i][j] != 0) {
					resp = false;
					i = matriz.length;
					j = matriz.length;
				}
			}
		}
		return resp;
	}

	public void prints() {
		for (int i = 0; i < tatata; i++ ) {
			System.out.println(s.get(i));
		}
	}

	/**
	 * Metodo de ordenaçao topologica de khan
	 * @return [description]
	 */
	public ArrayList<Integer> khan() {

		this.l = new ArrayList<Integer>();
		this.s = new ArrayList<Integer>();
		verticesSemEntrada();

		//System.out.println(s.size());

		while (!s.isEmpty()) {

			int v = s.remove(0);
			l.add(v);

			for (int i = 0; i < numVertices ; i++ ) {
				if (matriz[i][v] == 1) {
					matriz[i][v] = 0;
					if (temArcoDeEntrada(i) == true) {
						s.add(i);
					}
				}
			}
		}
		if (vazio() == true) {
			return l;
		} else {
			return null;
		}
	}

	/**
	 * Metodo para encher matriz
	 * @param origem  origem
	 * @param destino destino
	 * @param peso    peso da aresta
	 */
	public void encherMatriz(int origem, int destino, int peso) {
		matriz[origem][destino] = peso;
		if (tipo == false) {
			matriz[destino][origem] = peso;
		}
	}

	/**
	 * Metodo para imprimir a matriz
	 */
	public void imprimir () {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j]);
			}
			System.out.println("");
		}
	}

	/**
	 * metodo para busca em largura
	 */
	public void buscaPorLargura ( ) {
		int u = 0;
		fila.add(0);
		while ( !fila.isEmpty() ) {
			u = fila.remove();
			for ( int v = 0; v < numVertices; v ++ ) {
				if ( matriz[u][v] != 0 && cor[v] == 0 ) {
					cor[v] = 1;
					distancia[v] = distancia[u] + 1;
					pai[v] = u;
					fila.add(v);
				}
			}
		}
		cor[u] = 2;
	}

	/**
	 * Metodo de impressao do vetor
	 */
	public void imprime ( ) {
		System.out.println("Imprimindo o vetor do pai: " );
		for ( int i = 0; i < pai.length; i ++ ) {
			System.out.print(pai[i] + "\t");
		}
		System.out.println();
		for ( int i = 0; i < pai.length; i ++ ) {
			System.out.print(i + "\t");
		}
	}

	/** Metdo para imorimir o caminho
	 */
	public void imprimirCaminho() {

		ArrayList<String> caminho = new ArrayList<>();

		for (int i = numVertices - 1; pai[i] != -1; i = pai[i]) {
			caminho.add(pai[i] + " --> " + i);
		}
		Collections.reverse(caminho);

		for (String pass : caminho) {
			System.out.println(pass);
		}
	}
}

class Tp3 {

	static BufferedReader read = null;

	static String [] vetor = new String[100];

	static String [] relacoes = new String[100 * 100];

	static String [] materias = new String[100];

	static int numVertices = 0;

	private static int cont;

	/**
	 * Metodo para ler e converter a entrada
	 * @throws Exception erro
	 */
	public static void lerEConverter() throws Exception {

		read = new BufferedReader(new InputStreamReader(System.in));

		String aux;
		String[] spli;
		int cont = 0;

		while ((aux = read.readLine()) != null) {

			vetor[cont] = aux;
			spli = vetor[cont].split(";");
			materias[cont] = spli[0];
			cont++;
			numVertices++;

		}

		int reasasasasas = 0;

		while (materias[reasasasasas] != null) {

			//System.out.println(materias[reasasasasas]);
			reasasasasas++;
		}
	}

	/**
	 * Metodo para atribuir as dependencias
	 */
	public static void atribuirDependencias() {

		String[] spli;
		String[] spli2;
		int cont = 0;
		String aux;
		String aux2;
		int contOrigem = 0;
		int destino = 0;
		String dep;

		while (materias[contOrigem] != null) {

			spli = vetor[contOrigem].split(";");

			aux = spli[0];

			if (spli.length > 1) {
				dep = spli[1];
				spli2 = dep.split(",");
				int size = spli2.length;

				for (int cont2 = 0; cont2 < size ; cont2++ ) {

					aux2 = spli2[cont2];
					//System.out.println(aux2);

					for (int contDestino = 0; contDestino < materias.length ; contDestino++ ) {

						if (materias[contDestino].equals(aux2)) {
							destino = contDestino;
							relacoes[cont] = contOrigem + "," + destino + ",1";
							cont++;
							contDestino = materias.length;
							//System.out.println(destino);

						}

					}
				}
			}
			contOrigem++;
		}

		int reasasasasas = 0;

		while (relacoes[reasasasasas] != null) {

			//System.out.println(relacoes[reasasasasas]);
			reasasasasas++;
		}
	}

	/**
	 * Metodo para montar o grafo
	 * @return retorno
	 * @throws Exception     erro
	 */
	public static Grafos montarGrafo() throws Exception {

		read = new BufferedReader(new InputStreamReader(System.in));
		Grafos resp = null;
		boolean tipo = true; //se 0(false) == grafo. se 1(true) digrafo
		String[] relacoes2;
		int i = 0;
		cont = 0;

		relacoes2 = new String[(numVertices * numVertices)];

		while (relacoes[cont] != null) {
			relacoes2[cont] = relacoes[cont];
			cont++;
		}

		resp = new Grafos(tipo, numVertices, relacoes2);

		return resp;
	}

	public static void main(String[] args)throws Exception {
		int verticeAverificar;
		String arestaAverificar;
		read = new BufferedReader(new InputStreamReader(System.in));



		lerEConverter();
		atribuirDependencias();
		Grafos grafo =  montarGrafo();

		//grafo.imprimir();

		//grafo.khan();


		ArrayList<Integer> lista = grafo.khan();

		int aux;

		for (int i = 0; i < lista.size() ; i++ ) {
			aux = lista.get(i);

			if (i < lista.size() - 1) {
				System.out.print(materias[aux] + " - ");
			} 
			else {
				System.out.print(materias[aux]);
			}
		}



	}
}