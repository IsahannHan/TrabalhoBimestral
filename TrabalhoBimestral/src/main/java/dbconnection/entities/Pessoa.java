package dbconnection.entities;

import java.math.BigDecimal;

import dbconnection.Coluna;
import dbconnection.Tabela;

@Tabela(value="t_pessoa")
public class Pessoa {
	
	@Coluna(nome="idPessoa", pk=true, restricao="NOT NULL")
	private int id;
	
	@Coluna(nome="nomePessoa", restricao="NOT NULL")
	private String pessoa;
	
	@Coluna(nome="sobrenomePessoa", restricao="NOT NULL")
	private String sobrenome;
	
	@Coluna(nome="pesoPessoa", restricao="NOT NULL")
	private BigDecimal peso;
	
	@Coluna(nome="alturaPessoa", restricao="NOT NULL")
	private BigDecimal altura;

	@Coluna(nome="idadePessoa", restricao="NOT NULL")
	private int idade;
	
	public Pessoa(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	public BigDecimal getAltura() {
		return altura;
	}
	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altura == null) ? 0 : altura.hashCode());
		result = prime * result + id;
		result = prime * result + idade;
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((sobrenome == null) ? 0 : sobrenome.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (altura == null) {
			if (other.altura != null)
				return false;
		} else if (!altura.equals(other.altura))
			return false;
		if (id != other.id)
			return false;
		if (idade != other.idade)
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (sobrenome == null) {
			if (other.sobrenome != null)
				return false;
		} else if (!sobrenome.equals(other.sobrenome))
			return false;
		return true;
	}
}
