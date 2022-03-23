package Analizador;

import java.util.Stack;
import java.util.Vector;

import EDI.Datos;

public class Sintactico
{
	Datos obd = new Datos();
	String comp, tran="Bloque", ord, a[], ante;
	int ren, col, x, y, ind, conta=0, f=-1;
	Vector <String> archivodatos=new Vector<>();
	//Stack<String> error = new Stack<String>();
	Stack<String> pila = new Stack<String>();
	//Stack<String> err2 = new Stack<String>();
	String tab[][] = {
			{ "", "inicio", "fin", "[", "{", ";", "if", "do", "while", "boolean", "char", "String", "double", "int",
					",", "(", ")", "id", "num", "literal", "car", "true", "false", "*", "/", "+", "-", "<", ">", "@",
					"#", "?", "¿", "and", "or", "not", "else","]","}"},
			{ "Dec", null, null, "[", null, null, null, null, null, "Declaraciones ]", "Declaraciones ]",
					"Declaraciones ]", "Declaraciones ]", "Declaraciones ]", null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null },
			{ "Bloque", "inicio Dec Bloque fin", null, null, null, null, "sents }", "sents }", "sents }", null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null },
			{ "Declaraciones", null, null, null, null, null, null, null, null, "Tipo asig sigid ; Declaraciones",
					"Tipo asig sigid ; Declaraciones", "Tipo asig sigid ; Declaraciones", "Tipo asig sigid ; Declaraciones",
					"Tipo asig sigid ; Declaraciones", null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null, null,"3" },
			{ "Dec1", null, null, null, null, null, null, null, null, "Tipo asig sigid ; Dec1",
					"Tipo asig sigid ; Dec1", "Tipo asig sigid ; Dec1", "Tipo asig sigid ; Dec1",
					"Tipo asig sigid ; Dec1", null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null },
			{ "Tipo", null, null, null, null, null, null, null, null, "boolean", "char", "String", "double", "int",
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null },
			{ "sigid", null, null, null, null, "3", null, null, null, null, null, null, null, null, ", id sigid", null,
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null },
			{ "sigid1", null, null, null, null, "3", null, null, null, null, null, null, null, null, ", asig sigid",
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null },
			{ "sigif", null, null, null, null, "3", null, null, "3", null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null },
			{ "asig", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					"id = EL", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null },
			{ "sents", null, null, null, null, null, "sent sents", "sent sents", "sent sents", null, null, null, null,
					null, null, null, null, "sent sents", null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null,null,"3" },
			{ "sent", null, null, null, null, null, "if ( EL ) { sent sents } sigif",
					"do { sent sents } while ( EL ) ;", "while ( EL ) { sent sents }", null, null, null, null, null,
					null, null, null, "id = EL ;", null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null },
			{ "EL", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					"ER EL1", "ER EL1", "ER EL1", "ER EL1", "ER EL1", "ER EL1", null, null, null, null, null, null,
					null, null, null, null, "not EL", null },
			{ "EL1", null, null, null, null, "3", null, null, null, null, null, null, null, null, null, null, "3",
					null, null, null, null, null, null, "3", "3", "3", "3", "3", "3", "3", "3", "3", "3", "and E",
					"or E", "3", null },
			{ "ER", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					"E ER1", "E ER1", "E ER1", "E ER1", "E ER1", "E ER1", null, null, null, null, null, null, null,
					null, null, null, "sinc", "sinc", null },
			{ "ER1", null, null, null, null, "3", null, null, null, null, null, null, null, null, null, null, "3",
					null, null, null, null, null, null, "3", "3", "3", "3", "< E", "> E", "@ E", "# E", "? E", "¿ E",
					"3", "3", "3", null },
			{ "E", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					"T E1", "T E1", "T E1", "T E1", "T E1", "T E1", null, null, null, null, null, null, null, null,
					null, null, null, null, null, null },
			{ "E1", null, null, null, null, "3", null, null, null, null, null, null, null, null, null, null, "3",
					"sinc", "sinc", "sinc", "sinc", "sinc", "sinc", "3", "3", "+ T E1", "- T E1", "3", "3", "3", "3",
					"3", "3", "3", "3", "3" },
			{ "T", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					"F T1", "F T1", "F T1", "F T1", "F T1", "F T1", null, null, null, null, null, null, null, null,
					null, null, null, null, null, null },
			{ "T1", null, null, null, null, "3", null, null, null, null, null, null, null, null, null, null, "3",
					"sinc", "sinc", "sinc", "sinc", "sinc", "sinc", "* F T1", "/ F T1", "3", "3", "3", "3", "3", "3",
					"3", "3", "3", "3", "3", "3" },
			{ "F", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "id",
					"num", "literal", "car", "true", "false", "sinc", "sinc", null, null, null, null, null, null, null,
					null, null, null, null } };

	@SuppressWarnings("unchecked")
	public Sintactico(@SuppressWarnings("rawtypes") Vector archdat)
	{
		/*this.comp = comp;
		pila.push(this.comp);
		this.Buscar();*/
		archivodatos=archdat;
		pila.push("$");
		pila.push("inicio");
		this.Inicio();
	}
	
	public boolean Deli(String com)
	{
		if(com.equals("[") || com.equals("(") || com.equals("{"))
			return true;
		return false;
	}
	
	public void Inicio()
	{
		@SuppressWarnings("unused")
		String trash="";
		if(f < archivodatos.size())
		{
			f++;
			comp=archivodatos.get(f);
			if(f==69)
				trash="";
			if(ante!=null && ante.equals("while"))
				if(archivodatos.get(f).equals("("))
				{
					pila.pop();
					trash=archivodatos.get(f);
					if(!Determinista(pila.peek()))
						tran=pila.peek();
					System.out.println();
					for (int t = 0; t < pila.size(); t++)
						System.out.print(pila.get(t)+" ");
					this.Inicio();
				}
			if(archivodatos.get(f).equals("Delimitador"))
			{
				trash=archivodatos.get(f);
				this.Inicio();
			}
			if(this.Deli(archivodatos.get(f)) || archivodatos.get(f).equals(ante))
			{
				trash=archivodatos.get(f);
				if(tran.equals("$"))
				{
					System.out.println();
					this.Terminar();
				}
				else
					this.Inicio();
			}
			else
				this.Buscar();
		}

	}

	public void Buscar()
	{
		// componentes
		for (col = 0; col < tab[0].length; col++)
			if (archivodatos.get(f).equals(tab[0][col]))
				y = col;

		// transiciones
		for (ren = 0; ren < tab.length; ren++)
			if (tran.equals(tab[ren][0]))
				x = ren;
		ord = tab[x][y];
		this.Apilar();
		this.Concordancia();
	}
	
	private void Apilar()
	{
		pila.pop();
		a = ord.split(" ");
		for (ind = a.length; ind-1 != -1; ind--)
			pila.push(a[ind-1]);
		if(pila.peek().equals("3"))
			pila.pop();
		if(!Determinista(pila.peek()))
			tran=pila.peek();
		System.out.println();
		for (int t = 0; t < pila.size(); t++)
			System.out.print(pila.get(t)+" ");
	}
	
	private boolean Determinista(String com)
	{
		String det[] = {",", "id", "String", "int", "char", "double", "boolean", "inicio", "if", "do", "while", "else", ";", "=", "num","[","]",
				"(", ")","{","}", "<", ">", "&", "#", "@", "¿", "?", "|", "!", "fin", "*", "+", "-", "3", "literal", "car",
				"true", "false", "/", "sinc" };
		for (int i = 0; i < det.length; i++)
		{
			if (det[i].equals(com))
				return true;
			
		}
		return false;
	}
	
	public void Concordancia()
	{
		if (Determinista(pila.peek()))
		{
			if (pila.peek().equals(archivodatos.get(f)))
			{
				pila.pop();
				if(!Determinista(pila.peek()))
				{
					tran=pila.peek();
					System.out.println();
					for (int t = 0; t < pila.size(); t++)
						System.out.print(pila.get(t)+" ");
				}
				if(Determinista(pila.peek()))
				{
					ante=pila.pop();
					if(!Determinista(pila.peek()))
					{
						tran=pila.peek();
						System.out.println();
						for (int t = 0; t < pila.size(); t++)
							System.out.print(pila.get(t)+" ");
					}
				}
				this.Inicio();
			}
		}
		else
			if(!Determinista(pila.peek()) && Determinista(comp))
				this.Buscar();
			else
				this.Inicio();
	}
	
	public void Terminar()
	{
		System.out.println("Automata Finalizado");
	}
}
