package Analizador;

import java.util.regex.*;

public class Expreciones
{
	private String pr[] = { "int", "double", "char", "String", "boolean", "if", "else", "while", "do", "inicio", "fin",
			"true", "false" };

	// Clase de números
	public boolean Digito(String cad)
	{
		return Pattern.matches("[0-9]", cad);
	}

	public boolean Entero(String cad)
	{
		return Pattern.matches("[0-9]+", cad);
	}

	public boolean Decimal(String cad)
	{
		return Pattern.matches("[0-9]*[\\.][0-9]+", cad);
	}

	// Clase de Letras
	public boolean Caracter(String cad)
	{
		return Pattern.matches("[a-zA-Z]", cad);
	}

	public boolean Palabra(String cad)
	{
		return Pattern.matches("[a-zA-Z]+", cad);
	}

	public boolean Cadena(String cad)
	{
		return Pattern.matches("'.*'", cad);
	}

	// Clase de Letras y Números

	public boolean Identificador(String cad)
	{
		return Pattern.matches("[a-zA-Z_]+[a-zA-Z0-9]*", cad);
	}

	public boolean IdentificadorNoValido(String cad)
	{
		return Pattern.matches("[0-9]+[a-zA-Z][a-zA-Z0-9]*", cad);
	}

	/*public boolean Float(String cad)
	{
		if (cad.length() <= 1)
		{
			System.out.println("Solo 1 ");
			return false;
		}
		String numeros = cad.substring(0, cad.length() - 1);
		System.out.println(numeros);
		if (cad.endsWith("f"))
			return Pattern.matches("[0-9]*[\\.][0-9]+", numeros);
		else
			return false;
	}*/

	public boolean PalabraReservada(String cad)
	{
		for (int i = 0; i < pr.length; i++)
		{
			if (cad.equalsIgnoreCase(pr[i]))
			{
				return true;
			}
		}
		return false;
	}

	// Clase de Signos de puntuación y Delimitadores

	public boolean Delimitador(String cad)
	{
		return Pattern.matches("[^0-9A-Za-z|~\\\\~|~\\.~|~\\s~]+", cad);
	}

	public boolean Espacio(String cad)
	{
		return Pattern.matches("\\s", cad);
	}

	public boolean Punto(String cad)
	{
		return Pattern.matches("\\.", cad);
	}

	public boolean Separador(String cad)
	{
		return Pattern.matches("[\\[\\{\\(\\s\\;\\)\\]\\}]", cad);
	}

	public boolean CaracterPuntuacion(String cad)
	{
		return Pattern.matches("[:=\\']", cad);
	}

	public boolean Igual(String cad)
	{
		return Pattern.matches("=", cad);
	}

	public boolean Coma(String cad)
	{
		return Pattern.matches(",", cad);
	}

	public boolean PuntoComa(String cad)
	{
		return Pattern.matches(";", cad);
	}

	public boolean Menor(String cad)
	{
		return Pattern.matches("<", cad);
	}

	public boolean Mayor(String cad)
	{
		return Pattern.matches(">", cad);
	}

	public boolean OperadorLogico(String cad)
	{
		return Pattern.matches("[&|!]", cad);
	}

	public boolean OperadorMatematico(String cad)
	{
		return Pattern.matches("[\\+\\-\\*\\/]", cad);
	}
}