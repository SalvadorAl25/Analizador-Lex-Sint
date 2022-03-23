package Analizador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import Analizador.Expreciones;
import EDI.Datos;

public class Lexico
{
	FileInputStream entrada;
	FileOutputStream salida;
	int elex;
	String conte;
	
	Datos obd = new Datos();
	Expreciones expre = new Expreciones();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Vector<String> archivooriginal = new Vector(), archivodatos = new Vector(), errores = new Vector(),
			erresr = new Vector(); // vector de componente, dato para
									// sintactico, error y que error es

	public String Abrir()
	{
		String contenido = "";
		int num;
		try
		{
			entrada = new FileInputStream("Lexico.txt");
			while ((num = entrada.read()) != -1)
			{
				char caracter = (char) num;
				contenido += caracter;
			}
			entrada.close();
		}
		catch (IOException e)
		{
			System.out.println("Error de lectura");
		}
		return contenido;
	}

	public void GuardarArchivo()
	{
		try
		{
			salida = new FileOutputStream("Lexico.txt", true);
			conte = obd.Cadena("");
			byte[] b = conte.getBytes();
			salida.write(b);
			salida.close();
		}
		catch (IOException e)
		{
			System.out.println("Error al guardar");
		}

	}

	@SuppressWarnings({ "unused", "resource" })
	public void Proceso()
	{
		String cad = "", lexema = "", elementoanterior = "", caracter = "";
		int col, con = 0, ren = 0, elementos = 0;
		boolean simbolo = false, mensaje = false, tipodato = false;
		try
		{
			FileReader lec = new FileReader("Lexico.txt");
			BufferedReader Archivo = new BufferedReader(lec);
			do
			{
				cad = Archivo.readLine(); // Lee la línea y hace un salto de
											// línea
				if (cad != null)
				{
					cad = cad.trim();
					// El renglón tiene elementos
					for (int i = 0; i < cad.length(); i++)
					{
						caracter = cad.charAt(i) + "";
						// Recorrer cada elemento del renglón
						if (mensaje)
						{ // Todo lo que escriba será mensaje, hasta que escriba
							// un paréntesis
							if (caracter.equals("'"))
							{
								archivooriginal.add(lexema);
								archivodatos.add("car");
								mensaje = false;
								lexema = "";
								col = i + 1;
							}
							else
							{
								if (caracter.equals("\\\""))
								{
									archivooriginal.add(lexema);
									archivodatos.add("literal");
									mensaje = false;
									lexema = "";
									col = i + 1;
								}
								else
									lexema += caracter;
							}
						}
						else
						{
							if (!caracter.equals("="))
							{
								if (elementoanterior.equals("<"))
								{
									archivooriginal.add(elementoanterior);
									archivodatos.add("<");
									con++;
								}
								if (elementoanterior.equals(">"))
								{
									archivooriginal.add(elementoanterior);
									archivodatos.add(">");
									con++;
								}
								if (elementoanterior.equals("="))
								{
									archivooriginal.add(elementoanterior);
									archivodatos.add("=");
								}
								elementoanterior = "";
							}
							if (expre.Espacio(caracter) || i == cad.length() - 1 || expre.CaracterPuntuacion(caracter)
									|| expre.OperadorMatematico(caracter) || expre.OperadorLogico(caracter)
									|| expre.Delimitador(caracter) || expre.Separador(caracter)
									|| expre.Punto(caracter))
							{
								if (expre.Entero(lexema) && expre.Punto(caracter))
								{
									// En caso de que el punto que tengamos sea
									// doble
									lexema += caracter;
								}
								else
								{
									if (i == cad.length() - 1)
									{
										if (expre.Caracter(caracter) || expre.Digito(caracter))
											lexema += caracter;
										else
											simbolo = true;
									}
									// Encontró un separador
									if (expre.PalabraReservada(lexema))
									{
										if (lexema.equals("int"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("int");
											tipodato = true;
										}
										if (lexema.equals("double"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("double");
											tipodato = true;
										}
										if (lexema.equals("char"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("char");
											tipodato = true;
										}
										if (lexema.equals("String"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("String");
											tipodato = true;
										}
										if (lexema.equals("if"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("if");
										}
										if (lexema.equals("else"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("else");
										}
										if (lexema.equals("inicio"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("inicio");
										}
										if (lexema.equals("fin"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("fin");
										}
										if (lexema.equals("do"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("do");
										}
										if (lexema.equals("while"))
										{
											archivooriginal.add(lexema);
											archivodatos.add("while");
										}
										
									}
									else
									{
										if (expre.Identificador(lexema))
										{
											archivooriginal.add(lexema);
											archivodatos.add("id");
											if (tipodato)
												elementos++;
										}
										else
										{
											if (expre.IdentificadorNoValido(lexema))
											{
												errores.add(lexema);
												erresr.add("IDNoVálido");
											}
										}
									}
									if (expre.Entero(lexema))
									{
										archivooriginal.add(lexema);
										archivodatos.add("num");
									}

									if (expre.Decimal(lexema))
									{
										archivooriginal.add(lexema);
										archivodatos.add("num");
									}

									/*
									 * if(expre.Float(lexema)) {
									 * archivooriginal.add(lexema);
									 * archivodatos.add("num"); }
									 */
									if (expre.Cadena(lexema))
									{
										archivooriginal.add(lexema);
										archivodatos.add("num");
									}
									while (simbolo || i < cad.length() - 1)
									{
										if (expre.Punto(caracter))
										{
											archivooriginal.add(caracter);
											archivodatos.add(".");
										}
										if (expre.Separador(caracter))
										{
											if (caracter.equals(";"))
											{
												archivooriginal.add(caracter);
												archivodatos.add(";");
												tipodato = false;
											}
											if (caracter.equals("("))
											{
												archivooriginal.add(caracter);
												archivodatos.add("(");
											}
											if (caracter.equals(")"))
											{
												archivooriginal.add(caracter);
												archivodatos.add(")");
											}
											if (caracter.equals("{"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("{");
											}
											if (caracter.equals("}"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("}");
											}
											if (caracter.equals("["))
											{
												archivooriginal.add(caracter);
												archivodatos.add("[");
											}
											if (caracter.equals("]"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("]");
											}
											break;
										}
										if (expre.CaracterPuntuacion(caracter))
										{
											if (caracter.equals("="))
											{
												if (elementoanterior.length() != 0)
												{
													if (elementoanterior.equals("<"))
													{
														archivooriginal.add("<=");
														archivodatos.add("#");
														con++;
													}
													if (elementoanterior.equals(">"))
													{
														archivooriginal.add(">=");
														archivodatos.add("@");
														con++;
													}
													if (elementoanterior.equals("="))
													{
														archivooriginal.add("==");
														archivodatos.add("?");
														con++;
													}
													if (elementoanterior.equals("!"))
													{
														archivooriginal.add("!=");
														archivodatos.add("¿");
														con++;
													}
													elementoanterior = "";
												}
												else
												{
													elementoanterior = caracter;
												}
											}
											else
											{
												if (caracter.equals("'") || caracter.equals("\\\""))
												{
													mensaje = true;
												}
												else
												{
													archivooriginal.add(caracter);
													archivodatos.add("CaracterPuntuacion");
												}
											}
											break;
										}
										if (expre.OperadorLogico(caracter))
										{
											archivooriginal.add(lexema);
											archivodatos.add("OperadorLogico");
											break;
										}
										if (expre.OperadorMatematico(caracter))
										{
											if (caracter.equals("+"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("+");
											}
											if (caracter.equals("-"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("-");
											}
											if (caracter.equals("*"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("*");
											}
											if (caracter.equals("/"))
											{
												archivooriginal.add(caracter);
												archivodatos.add("/");
											}
											break;
										}
										if (expre.Delimitador(caracter))
										{
											if (caracter.equals("<") || caracter.equals(">") || caracter.equals(","))
											{
												if (caracter.equals("<"))
												{
													elementoanterior = "<";
												}
												if (caracter.equals(">"))
												{
													elementoanterior = ">";
												}
												if (caracter.equals(","))
												{
													archivooriginal.add(caracter);
													archivodatos.add(",");
												}
											}
											else
											{
												archivooriginal.add(caracter);
												archivodatos.add("Delimitador");
											}
											break;
										}
										simbolo = false;
									}
									lexema = "";
									col = i + 1;

								}
							}
							else
							{
								// No contiene ; ni espacio, {, ( [
								lexema += caracter;
							}
						}
					}

				}
				ren++;
				col = 0;
				for (int ind = 0; ind < archivodatos.size(); ind++)
				{
					if(archivodatos.get(ind).equals("id"))
						if(archivodatos.get(ind-1).equals("Delimitador"))
							if(archivodatos.get(ind+1).equals("Delimitador"))
								archivodatos.set(ind, "literal");
				}
			}
			while (cad != null);
		}
		catch (IOException e)
		{

		}
		System.out.println();
		for (int j = 0; j < archivooriginal.size(); j++)
		{
			System.out.println("Elemento: " + j);
			System.out.println("Original: " + archivooriginal.get(j));
			if(!archivodatos.get(j).equals("Delimitador"))
				System.out.println("Datos: " + archivodatos.get(j));
			System.out.println();
		}
		if (!errores.isEmpty())
		{
			for (int j = 0; j < errores.size(); j++)
			{
				System.out.println();
				System.out.println("Original: " + errores.get(j));
				System.out.println("Datos: " + erresr.get(j));
				System.out.println();
			}
		}
		new Sintactico(archivodatos);
	}
}
