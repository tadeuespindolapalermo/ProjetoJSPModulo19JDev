package br.com.projetojsp.bean;

public class TelefoneBean {

	private Long id;
	private String numero;
	private String tipo;
	private Long usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
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
		TelefoneBean other = (TelefoneBean) obj;
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
		return "TelefoneBean [id=" + id + ", numero=" + numero + ", tipo=" + tipo + ", usuario=" + usuario + "]";
	}

}
