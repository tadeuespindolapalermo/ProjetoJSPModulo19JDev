package br.com.projetojsp.bean;

public class ProdutoBean {

	private Long id;
	private String nome;
	private double quantidade;
	private double valor;
	private Long categoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public String getValorTexto() {
		return Double.toString(valor).replace(".", ",");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProdutoBean other = (ProdutoBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else {
			if (!id.equals(other.id))
				return false;
		}			
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoBean [id=" + id + ", nome=" + nome + ", quantidade=" + quantidade + ", valor=" + valor
				+ ", categoria=" + categoria + "]";
	}

}
