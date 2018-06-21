package br.edu.ifpb.monteiro.ads.sasj.tests.agendamento;

import br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste.CadastroAudienciaComChoqueDeHorario;
import br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste.CadastroConciliacaoComChoqueDeHorario;
import br.edu.ifpb.monteiro.ads.sasj.tests.agendamento.casosDeTeste.CadastroConciliacaoComHorarioIgualAudienciaSucesso;
import junit.framework.Test;
import junit.framework.TestSuite;

public class Agendamento {

	public static Test suite() {
        TestSuite suite = new TestSuite();
        
        suite.addTestSuite(CadastroAudienciaComChoqueDeHorario.class);
        suite.addTestSuite(CadastroConciliacaoComChoqueDeHorario.class);
        suite.addTestSuite(CadastroConciliacaoComHorarioIgualAudienciaSucesso.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
