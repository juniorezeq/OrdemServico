package br.com.klund.kpi.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.klund.kpi.modelo.negocio.Usuario;

@Named
@RequestScoped
public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Usuario> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Usuario>(this.em, Usuario.class);
	}

	@Inject
	private EntityManager em;
	
	public boolean existe(Usuario usuario) {
		
		TypedQuery<Usuario> query = em.createQuery(
				  " select u from Usuario u "
				+ " where u.login = :pLogin and u.senha = :pSenha", Usuario.class);
		
		query.setParameter("pLogin", usuario.getLogin());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			@SuppressWarnings("unused")
			Usuario resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}

	
public boolean existeLogin(String login) {
		TypedQuery<Usuario> query = em.createQuery(
				  " select u from Usuario u "
				+ " where u.login = :pLogin", Usuario.class);
		
		query.setParameter("pLogin", login);
		try {
			@SuppressWarnings("unused")
			Usuario resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}
	

public Usuario BuscaLogin(String login) {
	TypedQuery<Usuario> query = em.createQuery(
			  " select u from Usuario u "
			+ " where u.login = :pLogin", Usuario.class);
	
	query.setParameter("pLogin", login);
	try {
		@SuppressWarnings("unused")
		Usuario resultado = query.getSingleResult();
		return resultado;
	} catch (NoResultException ex) {
	return null;
	}
}

	
	
	
	public Usuario buscaUsuarioPelaAutenticacao(Usuario usuario) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" select u from Usuario u ");
		jpql.append(" where ");
		jpql.append("       u.login = :pLogin ");
		jpql.append("   and u.senha = :pSenha ");
		
		TypedQuery<Usuario> query = em.createQuery(jpql.toString() , Usuario.class);
		
		query.setParameter("pLogin", usuario.getLogin());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public List<Usuario> listarTodos() {
		CriteriaQuery<Usuario> query = em.getCriteriaBuilder().createQuery(Usuario.class);
		query.select(query.from(Usuario.class));

		List<Usuario> lista = em.createQuery(query).getResultList();

		return lista;
	}
	
	
	public void adiciona(Usuario usuario) {
		dao.adiciona(usuario);
	}

	public void atualiza(Usuario usuario){
		em.merge(usuario);
	}

	public void remove(Usuario usuario) {
		dao.remove(usuario);
	}

	public Usuario buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Usuario> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}



}
