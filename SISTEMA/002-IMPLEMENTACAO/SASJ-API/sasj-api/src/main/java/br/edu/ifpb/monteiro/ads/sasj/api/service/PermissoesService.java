package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoUsuario;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Permissao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;

@Service
public class PermissoesService {

	public void adicionarPermissoes(Usuario usuario) {
		List<Permissao> permissoesUsuario = new ArrayList<>();

		if (usuario.getTipoUsuario() == TipoUsuario.ADMIN) {
			Permissao cadastrarUsuario = new Permissao();
			cadastrarUsuario.setCodigo(1L);
			cadastrarUsuario.setDescricao("ROLE_CADASTRAR_USUARIO");
			permissoesUsuario.add(cadastrarUsuario);

			Permissao removerUsuario = new Permissao();
			removerUsuario.setCodigo(4L);
			removerUsuario.setDescricao("ROLE_REMOVER_USUARIO");
			permissoesUsuario.add(removerUsuario);
		}

		Permissao pesquisarUsuario = new Permissao();
		pesquisarUsuario.setCodigo(2L);
		pesquisarUsuario.setDescricao("ROLE_PESQUISAR_USUARIO");
		permissoesUsuario.add(pesquisarUsuario);

		Permissao atualizarUsuario = new Permissao();
		atualizarUsuario.setCodigo(3L);
		atualizarUsuario.setDescricao("ROLE_ATUALIZAR_USUARIO");
		permissoesUsuario.add(atualizarUsuario);

		Permissao cadastrarAudiencia = new Permissao();
		cadastrarAudiencia.setCodigo(5L);
		cadastrarAudiencia.setDescricao("ROLE_CADASTRAR_AUDIENCIA");
		permissoesUsuario.add(cadastrarAudiencia);

		Permissao pesquisarAudiencia = new Permissao();
		pesquisarAudiencia.setCodigo(6L);
		pesquisarAudiencia.setDescricao("ROLE_PESQUISAR_AUDIENCIA");
		permissoesUsuario.add(pesquisarAudiencia);

		Permissao atualizarAudiencia = new Permissao();
		atualizarAudiencia.setCodigo(7L);
		atualizarAudiencia.setDescricao("ROLE_ATUALIZAR_AUDIENCIA");
		permissoesUsuario.add(atualizarAudiencia);

		Permissao removerAudiencia = new Permissao();
		removerAudiencia.setCodigo(8L);
		removerAudiencia.setDescricao("ROLE_REMOVER_AUDIENCIA");
		permissoesUsuario.add(removerAudiencia);

		Permissao cadastrarConciliacao = new Permissao();
		cadastrarConciliacao.setCodigo(9L);
		cadastrarConciliacao.setDescricao("ROLE_CADASTRAR_CONCILIACAO");
		permissoesUsuario.add(cadastrarConciliacao);

		Permissao pesquisarConciliacao = new Permissao();
		pesquisarConciliacao.setCodigo(10L);
		pesquisarConciliacao.setDescricao("ROLE_PESQUISAR_CONCILIACAO");
		permissoesUsuario.add(pesquisarConciliacao);

		Permissao atualizarConciliacao = new Permissao();
		atualizarConciliacao.setCodigo(11L);
		atualizarConciliacao.setDescricao("ROLE_ATUALIZAR_CONCILIACAO");
		permissoesUsuario.add(atualizarConciliacao);

		Permissao removerConciliacao = new Permissao();
		removerConciliacao.setCodigo(12L);
		removerConciliacao.setDescricao("ROLE_REMOVER_CONCILIACAO");
		permissoesUsuario.add(removerConciliacao);
		
		Permissao cadastrarProcesso = new Permissao();
		cadastrarProcesso.setCodigo(13L);
		cadastrarProcesso.setDescricao("ROLE_CADASTRAR_PROCESSO");
		permissoesUsuario.add(cadastrarProcesso);

		Permissao pesquisarProcesso = new Permissao();
		pesquisarProcesso.setCodigo(14L);
		pesquisarProcesso.setDescricao("ROLE_PESQUISAR_PROCESSO");
		permissoesUsuario.add(pesquisarProcesso);

		Permissao atualizarProcesso = new Permissao();
		atualizarProcesso.setCodigo(15L);
		atualizarProcesso.setDescricao("ROLE_ATUALIZAR_PROCESSO");
		permissoesUsuario.add(atualizarProcesso);

		Permissao removerProcesso = new Permissao();
		removerProcesso.setCodigo(16L);
		removerProcesso.setDescricao("ROLE_REMOVER_PROCESSO");
		permissoesUsuario.add(removerProcesso);

		usuario.setPermissoes(permissoesUsuario);
	}

}