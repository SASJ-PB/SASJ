package br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada;

import br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada.casosDeTeste.BuscaAudienciasPorData;
import br.edu.ifpb.monteiro.ads.sasj.tests.pesquisaAvancada.casosDeTeste.BuscaConciliacaoCampoMarcadoInvalido;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PesquisaAvancada {

	public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(BuscaAudienciasPorData.class);
        suite.addTestSuite(BuscaConciliacaoCampoMarcadoInvalido.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
