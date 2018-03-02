package br.com.klund.kpi.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

//import javax.enterprise.context.ApplicationScoped;
//import javax.faces.view.ViewScoped;
//import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.klund.kpi.modelo.dao.UsuarioDao;
import br.com.klund.kpi.modelo.negocio.Usuario;
import br.com.klund.kpi.tx.Transacional;

@Named
@ViewScoped
public class CadastroBean implements Serializable {
	/**
	 * criado por Ezequiel
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario = new Usuario();

	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/novousuario.xhtml?faces-redirect=true";
	}

	@Transacional
	public String voltar() {
		return "/view/logado.xhtml?faces-redirect=true";
	}

	@Transacional
	public String incluir() {
		usuario.setDataDoCadastro(LocalDateTime.now());
		if (usuario.getLogin().isEmpty() || usuario.getSenha().isEmpty()) {
			 mensagemErro("Todos os campos devem ser preenchidos");
			return null;
		}
		boolean existe = usuarioDao.existeLogin(usuario.getLogin());
		if (existe == false) {
			usuarioDao.adiciona(usuario);
			System.out.println("init.Cadastro()");
			usuario = new Usuario();
		    mensagemSucesso("Cadastrado com sucesso");
		return null;
		}
		mensagemErro("Não foi possivel realizar o cadastro usuário já existe");
		return null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Transacional
	public List<Usuario> usuariosCadastrados() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioDao.listaTodosPaginada(0, 100);
		return usuarios;
	}
	
	@Transacional
	public Usuario Responsavel(String login) {
		UsuarioDao daoUser = new UsuarioDao();
		Usuario encontrado = daoUser.BuscaLogin(login);
		return encontrado;
	}

	@Transacional
	public void atualizaUsuario() {
		try {
			usuarioDao.atualiza(usuario);
			mensagemSucesso( "Alterado com sucesso");

		} catch (Exception e) {
		    mensagemErro("Erro não foi possivel atualizar");
		}
	}

	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}
	
}
