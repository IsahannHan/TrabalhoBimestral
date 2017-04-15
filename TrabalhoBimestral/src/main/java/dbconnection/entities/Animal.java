package dbconnection.entities;

import java.math.BigDecimal;

import dbconnection.Coluna;
import dbconnection.Tabela;

@Tabela("ANIMAL_T")
public class Animal {
	
	//O primary key não necessita de SERIAL na restrição pois ele será adicionado automaticamente.
	@Coluna(nome="id_animal", pk=true, restricao="NOT NULL")
	private int id;
	
	@Coluna(nome="nome_animal", restricao="NOT NULL")
	private String nome;
	
	@Coluna(nome="idade_animal", restricao="NOT NULL")
	private int idade;
	
	@Coluna(nome="peso_animal")
	private BigDecimal peso;
	
	public Animal(){
		
	}
	
	public Animal(String nome, int idade, BigDecimal peso){
		super();
		this.nome = nome;
		this.idade = idade;
		this.peso = peso;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + idade;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (id != other.id)
			return false;
		if (idade != other.idade)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Animal [id_animal=" + id + ", nome=" + nome + ", idade= "+ idade +", peso="+ peso +"]";
	}
}
