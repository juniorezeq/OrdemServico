package br.com.klund.kpi.modelo.negocio;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tb_ordem_servico")
public class OrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "numeroos", length = 30, unique = true)
	private String numeroos;
	@Column(name = "inquire", length = 500)
	private String inquire;
	@Column(name = "cliente", length = 200)
	private String cliente;
	@Column(name = "equipament", length = 200)
	private String equipament;
	@Column(name = "department", length = 40)
	private departamentos department;
	@Column(name = "status", length = 10)
	private status status;
	@Column(insertable = true, updatable = true)
	private LocalDate dataRecebimento;
	@Column(insertable = true, updatable = true)
	private LocalDate dataProposta;
	@Column(insertable = true, updatable = true)
	private LocalDate dataPo;
	@Column(insertable = true, updatable = true)
	private LocalDate dataPartsOrdered;
	@Column(insertable = true, updatable = true)
	private LocalDate dataPartsReceveid;
	@Column(insertable = true, updatable = true)
	private LocalDate serviceFinished;
	@Column(insertable = true, updatable = true)
	private LocalDate jobInvoiced;
	@Column(insertable = true, updatable = true)
	private LocalDate moneyInBank;
	@Column(name = "kpiProposal")
	private int kpiProposal;
	@Column(name = "kpiFollowUp")
	private int kpiFollowUp;
	@Column(name = "kpiPartsOrdered")
	private int kpiPartsOrdered;
	@Column(name = "kpiDeliveryTime")
	private int kpiDeliveryTime;
	@Column(name = "kpiproducao")
	private int kpiproducao;
	@Column(name = "kpiInvoiced")
	private int kpiInvoiced;	
	@Column(name = "kpiReceveid")
	private int kpiReceveid;
	@Column(name = "goal")
	private int goal;
	@Column(name = "done")
	private int done;
	@Column(name = "difference")
	private int difference;
	@Column(name = "comentario", length = 300)
	private String comentario;
	@Column(name = "alteradorpor", length = 80)
	private String alteradopor;
	@ManyToOne
	private Usuario responsavel;
		
	enum departamentos {
		Servico,
		Locacao,
		Projeto;
	}
	

	 public enum status {
		Ativo,
		Finalizado,
		Perdido,
		Cancelado;
	}

	 

public List<String> departamentosListados(){
	List<String> dptos = Arrays.asList("Servico","Locacao","Projeto");
	return dptos;
}
	 
public List<String> statusListados(){
	List<String> status = Arrays.asList("Ativo","Finalizado","Perdido","Cancelado");
	return status;
}	 


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public status getStatus() {
		return status;
	}

	public void setStatus(status status) {
		this.status = status;
	}

	public String getNumeroos() {
		return numeroos;
	}

	public String getAlteradopor() {
		return alteradopor;
	}

	public void setAlteradopor(String alteradopor) {
		this.alteradopor = alteradopor;
	}

	public void setNumeroos(String numeroos) {
		this.numeroos = numeroos;
	}

	public String getInquire() {
		return inquire;
	}

	public void setInquire(String inquire) {
		this.inquire = inquire;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEquipament() {
		return equipament;
	}

	public void setEquipament(String equipament) {
		this.equipament = equipament;
	}

	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public departamentos getDepartment() {
		return department;
	}

	public void setDepartment(departamentos department) {
		this.department = department;
	}

	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}

		
	public void setDataRecebimento(String dataRecebida) {
		this.dataRecebimento = converteData(dataRecebida);
	}

	public LocalDate getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(String dataPropostaString) {
		this.dataProposta = converteData(dataPropostaString);
	}

	public LocalDate getDataPo() {
		return dataPo;
	}

	public void setDataPo(LocalDate dataPo) {
		this.dataPo = (dataPo);
	}

	public LocalDate getDataPartsOrdered() {
		return dataPartsOrdered;
	}

	public void setDataPartsOrdered(String dataPartsOrdered) {
		this.dataPartsOrdered = converteData(dataPartsOrdered);
	}

	public LocalDate getDataPartsReceveid() {
		return dataPartsReceveid;
	}

	public void setDataPartsReceveid(String dataPartsReceveid) {
		this.dataPartsReceveid = converteData(dataPartsReceveid);
	}

	public LocalDate getServiceFinished() {
		return serviceFinished;
	}

	public void setServiceFinished(String serviceFinished) {
		this.serviceFinished = converteData(serviceFinished);
	}

	public LocalDate getJobInvoiced() {
		return jobInvoiced;
	}

	public void setJobInvoiced(String jobInvoiced) {
		this.jobInvoiced = converteData(jobInvoiced);
	}

	public LocalDate getMoneyInBank() {
		return moneyInBank;
	}

	public void setMoneyInBank(String moneyInBank) {
		this.moneyInBank = converteData(moneyInBank);
	}

	public int getKpiProposal() {
		return kpiProposal;
	}

	public void setKpiProposal(int kpiProposal) {
		this.kpiProposal = kpiProposal;
	}

	public int getKpiFollowUp() {
		return kpiFollowUp;
	}

	public void setKpiFollowUp(int kpiFollowUp) {
		this.kpiFollowUp = kpiFollowUp;
	}

	public int getKpiPartsOrdered() {
		return kpiPartsOrdered;
	}

	public void setKpiPartsOrdered(int kpiPartsOrdered) {
		this.kpiPartsOrdered = kpiPartsOrdered;
	}

	public int getKpiDeliveryTime() {
		return kpiDeliveryTime;
	}

	public void setKpiDeliveryTime(int kpiDeliveryTime) {
		this.kpiDeliveryTime = kpiDeliveryTime;
	}

	public int getKpiproducao() {
		return kpiproducao;
	}

	public void setKpiproducao(int kpiprod) {
		this.kpiproducao = kpiprod;
	}

	public int getKpiInvoiced() {
		return kpiInvoiced;
	}

	public void setKpiInvoiced(int kpiInvoiced) {
		this.kpiInvoiced = kpiInvoiced;
	}

	public int getKpiReceveid() {
		return kpiReceveid;
	}

	public void setKpiReceveid(int kpiReceveid) {
		this.kpiReceveid = kpiReceveid;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getDone() {
		return done;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario colaborador) {
		this.responsavel = colaborador;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}
	
	public int calcularDiferenca () {
		difference = goal - done;
		return difference;
	}
	
	/**
	 * @return
	 */
	public int calcularDone() {
	Period period = Period.between(dataPo, moneyInBank);
	done = (period.getMonths()*30) + period.getDays();
	return done;
	}
	
	public void calcularKpis() {
		Period kpiProp = Period.between( dataRecebimento, dataProposta);
		kpiProposal = (kpiProp.getMonths()*30)+ kpiProp.getDays();
		
		Period kpiFol = Period.between( dataProposta, dataPo);
		kpiFollowUp = (kpiFol.getMonths()*30)+ kpiFol.getDays();
				
		Period kpiOrd = Period.between( dataPo, dataPartsOrdered);
		kpiPartsOrdered = (kpiOrd.getMonths()*30)+ kpiOrd.getDays();
	
		Period kpiDeliv = Period.between(dataPartsOrdered, dataPartsReceveid);
		kpiDeliveryTime= (kpiDeliv.getMonths()*30)+ kpiDeliv.getDays();
		
		Period kpiProd = Period.between (dataPartsReceveid, serviceFinished);
		kpiproducao= (kpiProd.getMonths()*30)+ kpiProd.getDays();
		
		Period kpiInv = Period.between ( serviceFinished, jobInvoiced);
		kpiInvoiced = (kpiInv.getMonths()*30)+ kpiInv.getDays();
		
		Period kpiMon = Period.between ( jobInvoiced, moneyInBank);
		kpiReceveid = (kpiMon.getMonths()*30)+ kpiMon.getDays();	
	}

	public LocalDate converteData(String dataString) {
		LocalDate data;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/MM/yyyy");
		data = (LocalDate.parse(dataString,formato));
		return data;
	}
	
	
	@Override
	public String toString() {
		return  numeroos;
	}

	
}
