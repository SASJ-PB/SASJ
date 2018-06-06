package br.edu.ifpb.monteiro.ads.sasj.tests.autenticacaoEPermissao;

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
import junit.framework.Test;
import junit.framework.TestSuite;

public class AutenticacaoEPermissao {

    public static Test suite() {
        TestSuite suite = new TestSuite();
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
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
