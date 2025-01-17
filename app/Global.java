import java.util.ArrayList;
import java.util.List;

import models.*;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	private List<User> usuarios = new ArrayList<>();
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(User.class.getName()).size() == 0){
					criaUsuarios();
				}
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinasTemasDicas();
				}

				//adicionaVotos();
			}
		});
	}
	
	@Override
	public void onStop(Application app){
	    JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				Logger.info("Aplicação finalizando...");
				disciplinas = dao.findAllByClassName("Disciplina");
				usuarios = dao.findAllByClassName("User");

				for (Disciplina disciplina : disciplinas) {
					dao.removeById(Disciplina.class, disciplina.getId());
				}
				for(User usuario : usuarios){
					dao.removeById(User.class,usuario.getId());
				}
			}
		});
	}
	
	private void criaDisciplinasTemasDicas(){
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		Dica dicaJavaScript = new DicaMaterial("http://www.w3schools.com/js/"); //DicaMaterial sobre javascript
		dicaJavaScript.setUser("Mauro Silva Diniz");
		Tema temaJavaScript = new Tema("JavaScript");
		dicaJavaScript.setTema(temaJavaScript);
		temaJavaScript.addDica(dicaJavaScript);
		si1.addTema(temaJavaScript); //Tema adicionado
		Dica dicaHtml = new DicaMaterial("https://www.codecademy.com/pt-BR/learn"); //DicaMaterial do codecademy
		dicaHtml.setUser("Gabriela Sousa");
		Tema temaHtmlCssBootstrap = new Tema("HTML / CSS / Bootstrap");
		dicaHtml.setTema(temaHtmlCssBootstrap);
		temaHtmlCssBootstrap.addDica(dicaHtml);
		si1.addTema(temaHtmlCssBootstrap);//Tema adicionado
		si1.addTema(new Tema("Heroku"));
		Dica dicaLabs = new DicaConselho("Aconselho vocês a fazerem todos os Labs."); // DicaConselho sobre os Labs
		dicaLabs.setUser("Tárcito Luã");
		Tema temaLabs = new Tema("Labs");
		dicaLabs.setTema(temaLabs);
		temaLabs.addDica(dicaLabs);
		si1.addTema(temaLabs); //Tema adicionado
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));

		dao.persist(si1);

		Disciplina es1 = new Disciplina("Engenharia de Software 1");
		es1.addTema(new Tema("Introdução à Engenharia de Software"));
		es1.addTema(new Tema("Métodos Formais"));
		es1.addTema(new Tema("Verificação e Validação"));
		es1.addTema(new Tema("Evolução de Software"));
		dao.persist(es1);

		Disciplina tc = new Disciplina("Teoria da Computação");
		tc.addTema(new Tema("Linguagens Regulares e Autômatos Finitos"));
		tc.addTema(new Tema("Linguagens Livre-de-Contexto e Autômatos de Pilha"));
		Dica dicaMaqTuring = new DicaMaterial("https://pt.wikipedia.org/wiki/M%C3%A1quina_de_Turing"); //DicaMaterial sobre maqina de turing
		dicaMaqTuring.setUser("Ricardo Schnetzer");
		Tema temaMaqTuring = new Tema("Máquina de Turing");
		dicaMaqTuring.setTema(temaMaqTuring);
		temaMaqTuring.addDica(dicaMaqTuring);
		tc.addTema(temaMaqTuring); //Tema adicionado
		tc.addTema(new Tema("Decidibilidade"));
		tc.addTema(new Tema("Redução"));
		dao.persist(tc);

		Disciplina eda = new Disciplina("Estrutura de Dados e Algoritmos");
		eda.addTema(new Tema("Análize de Algoritmos Iterativos"));
		Dica dicaAnalizeDeAlgRecursivos = new DicaConselho("Estude bastante desde o inicio, pois é o assunto que mais reprova."); //Dica conselho sobre
		dicaAnalizeDeAlgRecursivos.setUser("Tárcito Luã");
		Tema temaAnaAlgRecursivo = new Tema("Análize de Algoritmos Recursivos");									//analize de algoritmos recursivos
		dicaAnalizeDeAlgRecursivos.setTema(temaAnaAlgRecursivo);
		temaAnaAlgRecursivo.addDica(dicaAnalizeDeAlgRecursivos);
		eda.addTema(temaAnaAlgRecursivo);//Tema adicionado
		eda.addTema(new Tema("Ordenação Por Comparação - Iterativos"));
		eda.addTema(new Tema("Ordenação Por Comparação - Recursivos"));
		eda.addTema(new Tema("Ordenação em Tempo Linear"));
		eda.addTema(new Tema("TAD - Vetor, Pilha e Fila"));
		eda.addTema(new Tema("Listas Encadeadas - Iterativas"));
		eda.addTema(new Tema("Listas Encadeadas - Recursivas"));
		eda.addTema(new Tema("Árvore Binária de Pesquisa - BST"));
		eda.addTema(new Tema("Heaps"));
		eda.addTema(new Tema("Skip Lists"));
		eda.addTema(new Tema("Tabelas Hash"));
		eda.addTema(new Tema("Árvores AVL"));
		eda.addTema(new Tema("Árvores Splay"));
		eda.addTema(new Tema("Árvores PV"));
		eda.addTema(new Tema("Árvores B"));
		dao.persist(eda);

		Disciplina plp = new Disciplina("Paradigmas de Linguagens de Programação");
		plp.addTema(new Tema("Valores e Tipos"));
		plp.addTema(new Tema("Variáveis e Comandos"));
		plp.addTema(new Tema("Abstração e Mecanismos de Passagens de Parametros"));
		plp.addTema(new Tema("Paradigma Imperativo"));
		plp.addTema(new Tema("Paradigma Funcional"));
		plp.addTema(new Tema("Paradigma Orientado a Objetos"));
		plp.addTema(new Tema("Programação Orientada a Aspectos"));
		plp.addTema(new Tema("Programação Orientado a Modelos"));
		plp.addTema(new Tema("Programação Concorrente"));
		dao.persist(plp);

		dao.flush();
	}

	private void criaUsuarios(){
		User tarcito = new User("tarcito.lua@gmail.com","01020304","tarcito");
		tarcito.setNome("Tárcito Luã");
		dao.persist(tarcito);

		User tarcisio = new User("josetaarcisiof@gmail.com","123456789","fbiswat");
		tarcisio.setNome("José Tarcisio Fernandes");
		dao.persist(tarcisio);

		User paula = new User("paula.maria@gmail.com", "paula2015", "paula");
		paula.setNome("Maria Paula Cavalcante");
		dao.persist(paula);

		User ricardo12 = new User("ricardao009@hotmail.com","rick0241","ricardo12");
		ricardo12.setNome("Ricardo Schnetzer");

		dao.persist(ricardo12);

		User helena = new User("helenasousa@gmail.com","h145ex","helena");
		helena.setNome("maria Helena Sousa");
		dao.persist(helena);

		User mauro15 = new User("diniz.mauro@yahoo.com.br","mauRX7","mauro15");
		mauro15.setNome("Mauro Silva Diniz");
		dao.persist(mauro15);

		User gabis = new User("gabisousa@gmail.com", "gabi987", "gabis");
		gabis.setNome("Gabriela Sousa");
		dao.persist(gabis);

		User emanuel = new User("eman.carvalho@gmail.com","f6l6s3r10","emanuel");
		emanuel.setNome("Emanuel Carvalho");
		dao.persist(emanuel);

		User bichopiruleta = new User("alex.albuquerque@gmail.com","11121314","bichopiruleta");
		bichopiruleta.setNome("Alexandre Albuquerque");
		dao.persist(bichopiruleta);

		User black = new User("pedro.black@gmail.com","blackblackblack","black");
		black.setNome("Pedro Albuquerque");
		dao.persist(black);

		dao.flush();
	}

	//Todo adicionaVotos
	/*
	private void adicionaVotos(){
		List<Dica> dicas = dao.findAllByClassName("Dica");
		for(Dica dica : dicas){
			if(dica.getNome().Equals("")){

			}
		}

	}
	*/

}
