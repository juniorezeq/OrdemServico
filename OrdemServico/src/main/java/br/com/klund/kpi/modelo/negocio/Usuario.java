package br.com.klund.kpi.modelo.negocio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "login", length = 50, nullable = false, unique = true)
	private String login;
	@Column(nullable = false, insertable = true, updatable = false)
	private LocalDateTime dataDoCadastro;
	@Column(name = "senha", length = 50, nullable = false)
	private String senha;
	private LocalDateTime dataDoUltimoAcesso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getDataDoCadastro() {
		return dataDoCadastro;
	}

	public void setDataDoCadastro(LocalDateTime dataDoCadastro) {
		this.dataDoCadastro = dataDoCadastro;
	}

	public LocalDateTime getDataDoUltimoAcesso() {
		return dataDoUltimoAcesso;
	}

	public void setDataDoUltimoAcesso(LocalDateTime dataDoUltimoAcesso) {
		this.dataDoUltimoAcesso = dataDoUltimoAcesso;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(login);
	}

	public void geraNovaSenha() {
		UUID uuid = UUID.randomUUID();
		String senha = uuid.toString().substring(0, 5);
		this.setSenha(senha);
	}

}
