package br.com.klund.kpi.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorDeTransacao  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception  {
		Object resultado = null;
		try {
			manager.getTransaction().begin();
			resultado = contexto.proceed();
			manager.getTransaction().commit();
			
		} catch(Exception ex) {
			manager.getTransaction().rollback();
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
		}
		
		return resultado;
	}
}

