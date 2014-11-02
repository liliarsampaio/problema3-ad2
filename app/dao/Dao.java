package dao;

import java.util.List;

import javax.persistence.Query;

import play.db.jpa.JPA;

/**
 * Serviços simples de um Data Access Object (DAO)
 */
public class Dao {

    /**
     * Persiste uma entidade no Banco de Dados.
     */
	public boolean persist(Object e) {
		JPA.em().persist(e);
		return true;
	}

    /**
     * Espelha o estado do DAO com o banco de Dados, deve ser feito após um
     * persist, ou merge.
     */
	public void flush() {
		JPA.em().flush();
	}

    /**
     * Atualiza a informação da entidade do código com a do banco de dados.
     */
	public void merge(Object e) {
		JPA.em().merge(e);
	}

    /**
     * Procura uma certa {@code clazz} pelo seu {@code id}.
     */
	public <T> T findByEntityId(Class<T> clazz, Long id) {
		return JPA.em().find(clazz, id);
	}

    /**
     * Procura todos os objetos de uma certa classe pelo seu {@code className}
     * descrito na Entidade.
     */
	public <T> List<T> findAllByClassName(String className) {
		String hql = "FROM " + className;
		Query hqlQuery = JPA.em().createQuery(hql);
		return hqlQuery.getResultList();
	}

    /**
     * Deleta do banco de dados uma {@code classe} referenciada pelo seu
     * {@code id}.
     */
	public <T> void removeById(Class<T> classe, Long id) {
		JPA.em().remove(findByEntityId(classe, id));
	}

    /**
     * Remove o respectivo {@code objeto} do banco de dados.
     */
	public void remove(Object objeto) {
		JPA.em().remove(objeto);
	}

    /**
     * Procura uma certa {@code className} pelo seu {@code attributeName}.
     */
	public <T> List<T> findByAttributeName(String className,
			String attributeName, String attributeValue) {
		String hql = "FROM " + className + " c" + " WHERE c." + attributeName
				+ " = '" + attributeValue + "'";
		Query hqlQuery = JPA.em().createQuery(hql);
		return hqlQuery.getResultList();
	}

    /**
     * Cria uma Query HQL
     */
	public Query createQuery(String query) {
		return JPA.em().createQuery(query);
	}
}
