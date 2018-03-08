package br.com.klund.kpi.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.klund.kpi.modelo.dao.OrdemServicoDao;
import br.com.klund.kpi.modelo.dao.UsuarioDao;
import br.com.klund.kpi.modelo.negocio.OrdemServico;
import br.com.klund.kpi.modelo.negocio.Usuario;
import br.com.klund.kpi.tx.Transacional;

@Named
@ViewScoped
public class OrdemServicoBean implements Serializable {
	/**
	 * criado por Ezequiel
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrdemServicoDao ordemServicoDao;
	private UsuarioDao userDao;
	private OrdemServico ordemservico = new OrdemServico();
	private List<OrdemServico> osBanco;
	private String dataRecebimento;
	private String dataProposta;
	private String dataPO;
	private String dataPartsOrdered;
	private String dataPartsReceveid;
	private String dataServiceFinish;
	private String dataJobInvoiced;
	private String dataMoneyBank;

	
	
	 
	@PostConstruct
	public void init() {
		osBanco = new ArrayList<OrdemServico>();
			
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastroordemservico.xhtml?faces-redirect=true";
	}
	
	@Transacional
	public String editarOS() {
		return "/view/editaros.xhtml?faces-redirect=true";
	}

	@Transacional
	public String voltar() {
		return "/view/logado.xhtml?faces-redirect=true";
	}
	
	
	@Transacional
	public String incluir() {
			if (ordemservico.getCliente().isEmpty() || ordemservico.getNumeroos().isEmpty()) {
			 mensagemErro("Todos os campos devem ser preenchidos");
			return null;
		}else {
			ordemServicoDao.adiciona(ordemservico);
			System.out.println("init.Cadastro()");
			 ordemservico = new OrdemServico();
		    mensagemSucesso("Cadastrado com sucesso");
		return null;
		}
	}



	@Transacional
	public void atualizaOs() {
		try {
			ordemServicoDao.atualiza(ordemservico);
			} catch (Exception e) {
		    mensagemErro("Erro não foi possivel atualizar");
		}
	}
	

	
	@Transacional
	public List<OrdemServico> osCadastrados() {
		osBanco = new ArrayList<OrdemServico>();
		osBanco = ordemServicoDao.listarTodos();
		return osBanco;
	}
	
	@Transacional
	public List<Usuario> usuariosCadastrados() {
		List<Usuario> usuariosCadastrados = new ArrayList<Usuario>();
		usuariosCadastrados = userDao.listarTodos();
		return usuariosCadastrados;
	}
	

	@Transacional
	public void responsavel(String login) {
	Usuario resp = userDao.BuscaLogin(login);
	ordemservico.setResponsavel(resp);
	}
	
	
	@Transacional
	public void atualizar(OrdemServico os) {
		try {
			ordemServicoDao.atualiza(os);
			} catch (Exception e) {
		    mensagemErro("Erro não foi possivel atualizar");
		}
	}
	
	@Transacional
	public void removeOs(OrdemServico os) {
		try {
			ordemServicoDao.remove(os);
			} catch (Exception e) {
		    mensagemErro("Erro não foi possivel remover");
		}
	}

	@Transacional
	public String getDataRecebimento() {
		return dataRecebimento;
	}

	@Transacional
	public void setDataRecebimento(String dataRecebimentoString) {
		this.dataRecebimento = dataRecebimentoString;
		ordemservico.setDataRecebimento(converteData(dataRecebimentoString));
	}

	
	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}

	@Transacional
	public List<OrdemServico> getOsAtivas() {
		List <OrdemServico> osCadastradas;
		List <OrdemServico> osAtivas = new ArrayList<OrdemServico>();
		osCadastradas = ordemServicoDao.listarTodos();
		for (int a = 0; a < osCadastradas.size(); a++) {
			if ((osCadastradas.get(a).getStatus().equals(OrdemServico.status.Ativo))) {
				osAtivas.add(osCadastradas.get(a));
			}
		}
		return osAtivas;
	}

	@Transacional
	public List<OrdemServico> getOsFinalizadas() {
		List <OrdemServico> osCadastradas;
		List <OrdemServico> osFinalizadas = new ArrayList<OrdemServico>();
		osCadastradas = ordemServicoDao.listarTodos();
		for (int a = 0; a < osCadastradas.size(); a++) {
			if ((osCadastradas.get(a).getStatus().equals(OrdemServico.status.Finalizado))) {
				OrdemServico os = osCadastradas.get(a);
				os.calcularKpis();
				osFinalizadas.add(os);
				
			}
		}
		return osFinalizadas;
	}
	
	
	
	public void setDataProposta(String dataPropostaString) {
		this.dataProposta = dataPropostaString;
		ordemservico.setDataProposta(converteData(dataPropostaString));
				}

	public void setDataPO(String stringdataPO) {
		this.dataPO = stringdataPO;
		ordemservico.setDataPo(converteData(stringdataPO));
	}

	public void setDataPartsOrdered(String dataPartsOrdered) {
		this.dataPartsOrdered = dataPartsOrdered;
		ordemservico.setDataPartsOrdered(converteData(dataPartsOrdered));
	}

	public void setDataPartsReceveid(String dataPartsReceveid) {
		this.dataPartsReceveid = dataPartsReceveid;
		ordemservico.setDataPartsReceveid(converteData(dataPartsReceveid));
	}

	public void setDataServiceFinish(String dataServiceFinish) {
		this.dataServiceFinish = dataServiceFinish;
		ordemservico.setServiceFinished(converteData(dataServiceFinish));
	}

	public void setDataJobInvoiced(String dataJobInvoiced) {
		this.dataJobInvoiced = dataJobInvoiced;
		ordemservico.setJobInvoiced(converteData(dataJobInvoiced));
	}

	public void setDataMoneyBank(String dataMoneyBank) {
		this.dataMoneyBank = dataMoneyBank;
		ordemservico.setMoneyInBank(dataMoneyBank);
	}

	public void setOrdemServicoDao(OrdemServicoDao ordemServicoDao) {
		this.ordemServicoDao = ordemServicoDao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDataProposta() {
		return dataProposta;
	}

	public String getDataPO() {
		return dataPO;
	}

	public String getDataPartsOrdered() {
		return dataPartsOrdered;
	}

	public String getDataPartsReceveid() {
		return dataPartsReceveid;
	}

	public String getDataServiceFinish() {
		return dataServiceFinish;
	}

	public String getDataJobInvoiced() {
		return dataJobInvoiced;
	}

	public String getDataMoneyBank() {
		return dataMoneyBank;
	}

	public OrdemServicoDao getOrdemServicoDao() {
		return ordemServicoDao;
	}
	
	
	@Transacional
	    public LocalDate converteData(String dataString) {
			LocalDate data;
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/MM/yyyy");
			data = (LocalDate.parse(dataString,formato));
			return data;
		}
		

	    	
	    @Transacional
	    	public OrdemServico getOrdemservico() {
	    		return ordemservico;
	    	}

	    @Transacional
	    	public void setOrdemservico(OrdemServico ordemservico) {
	    		this.ordemservico = ordemservico;
	    	}

	    
			@Transacional
	    	public void atualizaUsuario() {
	    		try {
	    			ordemServicoDao.atualiza(ordemservico);
	    			mensagemSucesso( "Alterado com sucesso");

	    		} catch (Exception e) {
	    		    mensagemErro("Erro não foi possivel atualizar");
	    		}
	    	}
			
			
	    	public String onRowEdit(RowEditEvent event) {
	    		FacesMessage msg = new FacesMessage("ordem de serviço atualizada com sucesso", ((OrdemServico) event.getObject()).getNumeroos());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	            return null;
	        }
	         
	        public String onRowCancel(RowEditEvent event) {
	        	
	            FacesMessage msg = new FacesMessage("Edit Cancelled", ((OrdemServico) event.getObject()).getId().toString());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	            return null;
	        }
	         
	        public String onCellEdit(CellEditEvent event) {
	            Object oldValue = event.getOldValue();
	            Object newValue = event.getNewValue();
	             
	            if(newValue != null && !newValue.equals(oldValue)) {
	              mensagemSucesso("Celula Editada com sucesso!");
	            }
	            osCadastrados();
	            return "/view/logado.xhtml?faces-redirect=true";
	        }

	        public void onRowSelect(SelectEvent event) {
	        	ordemservico = (OrdemServico) event.getObject();
	            FacesMessage msg = new FacesMessage("Ordem Selecionada", ((OrdemServico) event.getObject()).getId().toString());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	     
	        public void onRowUnselect(UnselectEvent event) {
	        	FacesMessage msg = new FacesMessage("Ordem deselecionada", ((OrdemServico) event.getObject()).getId().toString());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }	
		    	 
	 
}
