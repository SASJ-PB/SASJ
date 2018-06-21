package br.edu.ifpb.monteiro.ads.sasj.tests.gerenciamentoUsuario;

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
import junit.framework.Test;
import junit.framework.TestSuite;

public class GerenciamentoUsuario {

	public static Test suite() {
        TestSuite suite = new TestSuite();
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
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
