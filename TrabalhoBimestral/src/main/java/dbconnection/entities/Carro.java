package dbconnection.entities;

import java.math.BigDecimal;

import dbconnection.Coluna;
import dbconnection.Tabela;

@Tabela(value="tabelaCarro")
public class Carro {
	
	@Coluna(nome="idCarro", pk=true, restricao="NOT NULL")
	private int id;
	
	@Coluna(nome="modeloCarro", restricao="NOT NULL")
	private String modelo;
	
	@Coluna(nome="marcaCarro")
	private String marca;
	
	@Coluna(nome="anoCarro", restricao="NOT NULL")
	private int ano;
	
	@Coluna(nome="pesoToneladasCarro")
	private BigDecimal pesoToneladas;

	@Coluna(nome="pesoCarcacaCarro")
	private BigDecimal pesoCarcaca;
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public BigDecimal getPesoToneladas() {
		return pesoToneladas;
	}
	public void setPesoToneladas(BigDecimal pesoToneladas) {
		this.pesoToneladas = pesoToneladas;
	}
	public BigDecimal getPesoCarcaca() {
		return pesoCarcaca;
	}
	public void setPesoCarcaca(BigDecimal pesoCarcaca) {
		this.pesoCarcaca = pesoCarcaca;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((pesoCarcaca == null) ? 0 : pesoCarcaca.hashCode());
		result = prime * result + ((pesoToneladas == null) ? 0 : pesoToneladas.hashCode());
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
		Carro other = (Carro) obj;
		if (ano != other.ano)
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (pesoCarcaca == null) {
			if (other.pesoCarcaca != null)
				return false;
		} else if (!pesoCarcaca.equals(other.pesoCarcaca))
			return false;
		if (pesoToneladas == null) {
			if (other.pesoToneladas != null)
				return false;
		} else if (!pesoToneladas.equals(other.pesoToneladas))
			return false;
		return true;
	}
}
