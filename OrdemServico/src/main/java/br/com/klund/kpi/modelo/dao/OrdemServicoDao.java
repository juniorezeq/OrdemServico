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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import br.com.klund.kpi.modelo.negocio.OrdemServico;

@Named
@RequestScoped
public class OrdemServicoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<OrdemServico> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<OrdemServico>(this.em, OrdemServico.class);
	}

	@Inject
	private EntityManager em;
	
	
	public OrdemServico buscaNumeroOS(OrdemServico os) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" select u from tb_ordem_servico u ");
		jpql.append(" where ");
		jpql.append("       u.numeroos = :pNumeroOS ");
	
		TypedQuery<OrdemServico> query = em.createQuery(jpql.toString() , OrdemServico.class);
		
		query.setParameter("pNumeroOS", os.getNumeroos());
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	
	public List<OrdemServico> listarTodos() {
		CriteriaQuery<OrdemServico> query = em.getCriteriaBuilder().createQuery(OrdemServico.class);
		query.select(query.from(OrdemServico.class));

		List<OrdemServico> lista = em.createQuery(query).getResultList();
		return lista;
	}
	
	
	public void adiciona(OrdemServico usuario) {
		dao.adiciona(usuario);
	}

	public void atualiza(OrdemServico os){
		em.merge(os);
	}

	public void remove(OrdemServico usuario) {
		dao.remove(usuario);
	}

	public OrdemServico buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<OrdemServico> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}



}
