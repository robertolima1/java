package br.com.caelum.estoque.ws;

import javax.xml.ws.Endpoint;

public class PublishWs {

	public static void main(String[] args) {

		
		EstoqueWs implementationWs = new EstoqueWs();		
		
        String URL = "http://localhost:8080/estoquews";

        System.out.println("EstoqueWS rodando: " + URL);

        //associando URL com implementacao
        Endpoint.publish(URL, implementationWs);

	}

}
