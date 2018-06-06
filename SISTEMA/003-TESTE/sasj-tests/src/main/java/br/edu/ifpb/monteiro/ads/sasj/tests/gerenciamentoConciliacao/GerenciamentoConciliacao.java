package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoConciliacao;

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
import junit.framework.Test;
import junit.framework.TestSuite;

public class GerenciamentoConciliacao {

    public static Test suite() {
        TestSuite suite = new TestSuite();
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
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
