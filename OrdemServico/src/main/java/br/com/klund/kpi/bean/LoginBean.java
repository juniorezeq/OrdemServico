package br.com.klund.kpi.bean;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import javax.servlet.http.HttpSession;

import br.com.klund.kpi.modelo.dao.UsuarioDao;
import br.com.klund.kpi.modelo.negocio.Usuario;
import br.com.klund.kpi.tx.Transacional;

@Named
@ViewScoped
public class LoginBean implements Serializable {
	
	private static final String USUARIO_LOGADO = "usuarioLogado";

	private static final long serialVersionUID = 1L;

	@Inject
	private HttpSession session;
	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init() {
		System.out.println("LoginBean.init();");
		usuario = (Usuario) session.getAttribute(USUARIO_LOGADO);
		if (usuario == null) {
			usuario = new Usuario();
		}
	}
	
	@Transacional
	public String perfil(){
		return "/view/perfil/perfil.xhtml?faces-redirect=true";
	}
	

	@Transacional
	public String loga() {
		Usuario usuarioAutenticado  = usuarioDao.buscaUsuarioPelaAutenticacao(this.usuario);
		if (usuarioAutenticado != null) {
			usuarioAutenticado.setDataDoUltimoAcesso(LocalDateTime.now());
			usuarioDao.atualiza(usuarioAutenticado);
			session.setAttribute(USUARIO_LOGADO, usuarioAutenticado);
			usuario = usuarioAutenticado;
			return "/view/logado.xhtml?faces-redirect=true";
		}
		String mensagem = "Usuário ou senha inválido!";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensagem));
		return null;
	}
	
	
	public Date ultimoAcesso(){
		Date date = asDate(usuario.getDataDoUltimoAcesso());
		return date;
	}
	
	 public static Date asDate(LocalDateTime localDateTime) {
		    return (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		  }
	 
	@Transacional
	public String desloga() {
		session.removeAttribute(USUARIO_LOGADO);
		session.invalidate();
		usuario = new Usuario();
		return "/view/login/login.xhtml?faces-redirect=true";
	}
	
	
	@Transacional
	public List<Usuario> usuarios() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioDao.listarTodos();		
			return usuarios;
		}
	
	@Transacional
	public String novoUsuario() {
		return "/view/novousuario.xhtml?faces-redirect=true";
		}

	
	@Transacional
	public void atualizaUsuario() {
		try {
			usuarioDao.atualiza(usuario);
			String mensagem = "Alterado com sucesso";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));

		} catch (Exception e) {
			String mensagem = "Erro não foi possivel atualizar";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

		}
	}
	
}
