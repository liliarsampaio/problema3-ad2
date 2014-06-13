package unidade;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.running;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.redirectLocation;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Autor;
import models.Livro;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import play.db.jpa.JPA;
import play.libs.F.Callback0;

public class LivroTest {

	GenericDAO dao = new GenericDAOImpl();
	List<Livro> livros;
	List<Autor> autores;
	
	@Before
	public void setUp() throws Exception {
		start(fakeApplication(inMemoryDatabase()));
	}
	
	@Test
	public void salvaLivro() {
		JPA.withTransaction(new Callback0() {
			@Override
			public void invoke() throws Throwable {
				livros = dao.findAllByClassName("Livro"); //consulta o bd
				assertThat(livros.size()).isEqualTo(0);
				
				Livro l1 = new Livro();
				l1.setNome("Biblia Sagrada");
				dao.persist(l1);
				
				livros = dao.findAllByClassName("Livro"); //consulta o bd
				assertThat(livros.size()).isEqualTo(1);
				assertThat(livros.get(0).getNome()).isEqualTo("Biblia Sagrada");
			}
		});
	}
	
	@Test
	public void salvaLivroComAutor() {
		JPA.withTransaction(new Callback0() {
			@Override
			public void invoke() throws Throwable {
				
				Autor a1 = new Autor();
				a1.setNome("George Martin");
				Livro l1 = new Livro(a1); // cria o livro com autor
				l1.setNome("A Game of Thrones");
				a1.getLivros().add(l1); // add o livro ao autor
				
				dao.persist(l1); // salva tudo junto
				
				livros = dao.findAllByClassName("Livro"); // carrega os livros com seu autor
				assertThat(livros.size()).isEqualTo(1);
				assertThat(livros.get(0).getNome()).isEqualTo("A Game of Thrones");
				assertThat(livros.get(0).getAutores().size()).isEqualTo(1);
				
				autores = dao.findAllByClassName("Autor"); // carrega os autores com seus livros
				assertThat(autores.size()).isEqualTo(1);
				assertThat(autores.get(0).getNome()).isEqualTo("George Martin");
				assertThat(autores.get(0).getLivros().size()).isEqualTo(1);
			}
		});
	}
}
