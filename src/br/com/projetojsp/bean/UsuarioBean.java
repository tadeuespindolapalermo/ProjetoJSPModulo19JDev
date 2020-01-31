package br.com.projetojsp.bean;

import java.io.Serializable;

public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = -2072984964113354533L;
	
	private Long id;
	private String login;
	private String senha;
	private String nome;	
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String ibge;
	private boolean ativo;
	private String sexo;
	private String perfil;

	private String fotoBase64;
	private String fotoBase64Miniatura;
	private String contentType;
	private String tempFotoUser;

	private String curriculoBase64;
	private String contentTypeCurriculo;

	private boolean atualizarImagem;
	private boolean atualizarCurriculo;

	public UsuarioBean() {
		this.atualizarImagem = true;
		this.atualizarCurriculo = true;
	}

	public String getTempFotoUser() {
		setTempFotoUser();
		return tempFotoUser;
	}
	
	public void setTempFotoUser() {
		tempFotoUser = "data:" + contentType + ";base64," + fotoBase64;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCurriculoBase64() {
		return curriculoBase64;
	}

	public void setCurriculoBase64(String curriculoBase64) {
		this.curriculoBase64 = curriculoBase64;
	}

	public String getContentTypeCurriculo() {
		return contentTypeCurriculo;
	}

	public void setContentTypeCurriculo(String contentTypeCurriculo) {
		this.contentTypeCurriculo = contentTypeCurriculo;
	}

	public String getFotoBase64Miniatura() {
		return fotoBase64Miniatura;
	}

	public void setFotoBase64Miniatura(String fotoBase64Miniatura) {
		this.fotoBase64Miniatura = fotoBase64Miniatura;
	}

	public boolean isAtualizarImagem() {
		return atualizarImagem;
	}

	public void setAtualizarImagem(boolean atualizarImagem) {
		this.atualizarImagem = atualizarImagem;
	}

	public boolean isAtualizarCurriculo() {
		return atualizarCurriculo;
	}

	public void setAtualizarCurriculo(boolean atualizarCurriculo) {
		this.atualizarCurriculo = atualizarCurriculo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
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
		UsuarioBean other = (UsuarioBean) obj;
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
		return "UsuarioBean [id=" + id + ", login=" + login + ", senha=" + senha + ", nome=" + nome
				+ ", cep=" + cep + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado
				+ ", ibge=" + ibge + ", ativo=" + ativo + ", sexo=" + sexo + ", perfil=" + perfil + "]";
	}

}
