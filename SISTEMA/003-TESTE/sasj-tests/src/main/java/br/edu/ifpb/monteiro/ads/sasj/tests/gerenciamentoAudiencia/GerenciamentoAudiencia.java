package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoAudiencia;

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
import junit.framework.Test;
import junit.framework.TestSuite;

public class GerenciamentoAudiencia {

    public static Test suite() {
        TestSuite suite = new TestSuite();
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
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
