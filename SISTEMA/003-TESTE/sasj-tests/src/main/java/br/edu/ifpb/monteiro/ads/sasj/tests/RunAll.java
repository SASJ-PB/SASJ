package br.edu.ifpb.monteiro.ads.sasj.tests;

import br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste.CadastroAudienciaComChoqueDeHorario;
import br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste.CadastroConciliacaoComChoqueDeHorario;
import br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste.CadastroConciliacaoComHorarioIgualAudienciaSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AcessoNaoPermitido;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AcessoPaginaComSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AcessoSemAutenticacao;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AutenticacaoMatriculaIncorreta;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AutenticacaoSemMatricula;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AutenticacaoSemSenha;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AutenticacaoSenhaIncorreta;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.AutenticacaoSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.LogoutSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.RecuperacaoSenhaEmailInvalido;
import br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao.casosDeTeste.RecuperacaoSenhaSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.AtualizacaoAudienciaSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoDataVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoDuracaoVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoHoraVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoNomeDaParteVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoNumeroProcessoExistente;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoNumeroProcessoVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoOitivasVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaCampoTipoAudienciaVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.CadastroAudienciaSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia.casosDeTeste.RemocaoAudienciaSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.AtualizacaoConciliacaoSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoDataVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoDuracaoVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoHoraVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoNomeConciliadorVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoNomeDaParteVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoNumeroProcessoExistente;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoNumeroProcessoVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoCampoOitivasVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.CadastroConciliacaoSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao.casosDeTeste.RemocaoConciliacaoSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioCampoCargoVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioCampoEmailVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioCampoMatriculaInvalido;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioCampoMatriculaVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioCampoNomeInvalido;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioCampoNomeVazio;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioEmailDuplicado;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioMatriculaDuplicada;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.CadastroUsuarioSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario.casosDeTeste.DesativacaoContaSucesso;
import br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada.casosDeTeste.BuscaAudienciasPorData;
import br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada.casosDeTeste.BuscaConciliacaoCampoMarcadoInvalido;
import junit.framework.Test;
import junit.framework.TestSuite;

public class RunAll {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite();
		
		//Autenticação e permissão
        suite.addTestSuite(AutenticacaoSemMatricula.class);
        suite.addTestSuite(AutenticacaoSemSenha.class);
        suite.addTestSuite(AutenticacaoMatriculaIncorreta.class);
        suite.addTestSuite(AutenticacaoSenhaIncorreta.class);
        suite.addTestSuite(AutenticacaoSucesso.class);
        suite.addTestSuite(LogoutSucesso.class);
        suite.addTestSuite(AcessoNaoPermitido.class);
        suite.addTestSuite(AcessoSemAutenticacao.class);
        suite.addTestSuite(AcessoPaginaComSucesso.class);
        suite.addTestSuite(RecuperacaoSenhaEmailInvalido.class);
        suite.addTestSuite(RecuperacaoSenhaSucesso.class);	
	
        // Gerenciamento de audiência
        suite.addTestSuite(CadastroAudienciaSucesso.class);
        suite.addTestSuite(CadastroAudienciaCampoDataVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoHoraVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoNumeroProcessoVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoNomeDaParteVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoTipoAudienciaVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoOitivasVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoDuracaoVazio.class);
        suite.addTestSuite(CadastroAudienciaCampoNumeroProcessoExistente.class);
        suite.addTestSuite(AtualizacaoAudienciaSucesso.class);
        suite.addTestSuite(RemocaoAudienciaSucesso.class);
        
        // Gerenciamento de conciliação
        suite.addTestSuite(CadastroConciliacaoSucesso.class);
        suite.addTestSuite(CadastroConciliacaoCampoDataVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoHoraVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoNumeroProcessoVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoNomeDaParteVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoNomeConciliadorVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoOitivasVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoDuracaoVazio.class);
        suite.addTestSuite(CadastroConciliacaoCampoNumeroProcessoExistente.class);
        suite.addTestSuite(AtualizacaoConciliacaoSucesso.class);
        suite.addTestSuite(RemocaoConciliacaoSucesso.class);
        
        // Agendamento
        suite.addTestSuite(CadastroAudienciaComChoqueDeHorario.class);
        suite.addTestSuite(CadastroConciliacaoComChoqueDeHorario.class);
        suite.addTestSuite(CadastroConciliacaoComHorarioIgualAudienciaSucesso.class);
        
        // Gerenciamento de usuario
        suite.addTestSuite(CadastroUsuarioSucesso.class);
        suite.addTestSuite(CadastroUsuarioCampoCargoVazio.class);
        suite.addTestSuite(CadastroUsuarioCampoEmailVazio.class);
        suite.addTestSuite(CadastroUsuarioCampoMatriculaInvalido.class);
        suite.addTestSuite(CadastroUsuarioCampoMatriculaVazio.class);
        suite.addTestSuite(CadastroUsuarioCampoNomeInvalido.class);
        suite.addTestSuite(CadastroUsuarioCampoNomeVazio.class);
        suite.addTestSuite(CadastroUsuarioEmailDuplicado.class);
        suite.addTestSuite(CadastroUsuarioMatriculaDuplicada.class);
        suite.addTestSuite(DesativacaoContaSucesso.class);
        
        // Pequisa avançada
        suite.addTestSuite(BuscaAudienciasPorData.class);
        suite.addTestSuite(BuscaConciliacaoCampoMarcadoInvalido.class);
        
        return suite;
	}

	public static void main(String[] args) {
		
        junit.textui.TestRunner.run(suite());
	}
}
