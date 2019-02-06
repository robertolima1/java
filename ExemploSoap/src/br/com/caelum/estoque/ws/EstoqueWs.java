package br.com.caelum.estoque.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoExeption;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;
import br.com.caelum.estoque.modelo.usuario.Usuario;


@WebService
public class EstoqueWs {

	private ItemDao dao =  new ItemDao();
	
	
	@WebMethod(operationName="TodosOsItens")
	@WebResult(name="item")
	@ResponseWrapper(localName="itens") // Ele embrulhará o retorno  itens -> item
	@RequestWrapper(localName="listaItens") //Embrulhará a requisição	
	public List<Item> getItens( @WebParam(name="filtros") Filtros filtros){
		//Retirar o ws do parametro quando o SOAP UI gerar o wsdl
		//Adicionando o WebParam, eu consigo deixar meu wsdl mais expressivo, dizendo exatamente os parametros que eu desejo
		if(filtros==null) {
			return new ArrayList<Item>();
		}
		List<Filtro> listaFiltros = new ArrayList<Filtro>();		
		listaFiltros = filtros.getLista();
		
		
		
		System.out.println("Listando todos os itens!");
		return dao.todosItens(listaFiltros);
		
	}
	
	
	
	@WebMethod(operationName="CadastrarItem")
	@WebResult(name="item")
	//Aqui eu adicionando um novo parametro na notação "HEARDER" onde esse parametro ficará no Header no wsdl quando for feito a requisição
	public void cadastrarItem(
			@WebParam(name="tokenUsuario", header=true) TokenUsuario tokenUsuario,
			@WebParam(name="item") Item item)
					throws AutorizacaoExeption {
		
		System.out.println("cadastrado item" + item);		
		boolean valid = new TokenDao().ehValido(tokenUsuario);
		
		if(valid) {
			this.dao.cadastrar(item);
		}
		else {
			throw new AutorizacaoExeption("Token Inválido");
		}
	}
}



