package base;

import javax.persistence.EntityManager;

import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import org.junit.After;
import org.junit.Before;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

public abstract class AbstractTest {
    public EntityManager em;
    GenericDAO dao = new GenericDAOImpl();
    private int numeroDeUsuarios;

    @Before
    public void setUp() {
        FakeApplication app = Helpers.fakeApplication();
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
        em.getTransaction().begin();

        numeroDeUsuarios = dao.findAllByClassName("User").size(); //verifica quantos usuarios foram criados antes dos testes
    }

    @After
    public void tearDown() {
        em.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        em.close();
    }

    /*
        retorna o numero de usuarios anteriores ao inicio dos testes
     */

    protected int numeroInicialDeUsuarios(){
        return numeroDeUsuarios;
    }
}