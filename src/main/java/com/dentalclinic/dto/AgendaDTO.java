package com.dentalclinic.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.dentalclinic.model.Agenda;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AgendaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime start;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime end;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate allDay;
	private Double valor;
	private Boolean status;
	private Long paciente_id;
	private String login_usuario;
	private String pagamento;
	private String detalhes;

	public AgendaDTO() {

	}

	public AgendaDTO(Agenda obj) {
		this.id = obj.getId();
		this.title = obj.getTitle();
		this.start = obj.getStart();
		this.end = obj.getEnd();
		this.allDay = obj.getAllDay();
		this.valor = obj.getValor();
		this.status = obj.getStatus();
		this.login_usuario = obj.getUsuario().getLogin();
		this.paciente_id = obj.getPaciente().getId();
		this.setPagamento(obj.getPagamento());
		this.setDetalhes(obj.getDetalhes());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public LocalDate getAllDay() {
		return allDay;
	}

	public void setAllDay(LocalDate allDay) {
		this.allDay = allDay;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getPaciente_id() {
		return paciente_id;
	}

	public void setPaciente_id(Long paciente_id) {
		this.paciente_id = paciente_id;
	}

	public String getLogin_usuario() {
		return login_usuario;
	}

	public void setLogin_usuario(String login_usuario) {
		this.login_usuario = login_usuario;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

}