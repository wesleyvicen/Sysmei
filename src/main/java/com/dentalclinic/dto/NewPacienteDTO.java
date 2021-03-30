package com.dentalclinic.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.dentalclinic.model.Clinica;
import com.dentalclinic.model.Paciente;

import enums.TipoCivil;
import enums.TipoPlano;
import enums.TipoSexo;


public class NewPacienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Clinica clinica;
	private String email;

	private String telefone1;

	private String telefone2;

	private String telefone3;

	private LocalDate nascimento;
	// Pessoa Responsavel, Apenas nome.
	private String responsavel;
	private TipoSexo sexo;
	private TipoCivil estadoCivil;
	private String indicacao;

	private TipoPlano planoSaude;

	private String convenio;
	private String rg;
	private String cpf;
	private String ocupacao;
	private String endereco;
	private String enderecoNum;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

	public NewPacienteDTO() {
	}
	
	public NewPacienteDTO(Paciente entity) {
		
		this.nome = entity.getNome();
		this.clinica = entity.getClinica();
		this.email = entity.getEmail();
		this.nascimento = entity.getNascimento();
		this.responsavel = entity.getResponsavel();
		this.sexo = entity.getSexo();
		this.estadoCivil = entity.getEstadoCivil();
		this.indicacao = entity.getIndicacao();
		this.planoSaude = entity.getPlanoSaude();
		this.convenio = entity.getConvenio();
		this.rg = entity.getRg();
		this.cpf = entity.getCpf();
		this.ocupacao = entity.getOcupacao();
		this.endereco = entity.getEndereco();
		this.enderecoNum = entity.getEnderecoNum();
		this.bairro = entity.getBairro();
		this.cidade = entity.getCidade();
		this.estado = entity.getEstado();
		this.cep = entity.getCep();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public TipoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(TipoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}

	public TipoPlano getPlanoSaude() {
		return planoSaude;
	}

	public void setPlanoSaude(TipoPlano planoSaude) {
		this.planoSaude = planoSaude;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEnderecoNum() {
		return enderecoNum;
	}

	public void setEnderecoNum(String enderecoNum) {
		this.enderecoNum = enderecoNum;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}