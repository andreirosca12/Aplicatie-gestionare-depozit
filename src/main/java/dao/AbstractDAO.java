package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Comanda;
import model.Factura;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creeaza o interogare SELECT pentru un anumit camp.
     *
     * @param field campul pentru care se va crea interogarea.
     * @return interogarea SELECT ca sir de caractere.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creeaza o interogare SELECT pentru toate campurile.
     *
     * @return interogarea SELECT ca sir de caractere.
     */
    private String createSelectQuery2() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Creeaza o interogare INSERT.
     *
     * @return interogarea INSERT ca sir de caractere.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        sb.append(type.getDeclaredFields()[1].getName());
        for (int i = 2; i < type.getDeclaredFields().length; i++) {
            sb.append(",");
            sb.append(type.getDeclaredFields()[i].getName());
        }
        sb.append(") VALUES (");
        sb.append("?");
        for (int i = 2; i < type.getDeclaredFields().length; i++) {
            sb.append(",?");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Gaseste toate obiectele din tabel.
     *
     * @return lista de obiecte gasite.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery2();
        List<T> resultList = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            resultList = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return resultList;
    }

    /**
     * Gaseste un obiect dupa ID.
     *
     * @param id ID-ul obiectului de gasit.
     * @return obiectul gasit sau null daca nu exista.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(type.getDeclaredFields()[0].getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creeaza obiecte dintr-un set de rezultate.
     *
     * @param resultSet setul de rezultate.
     * @return lista de obiecte create.
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor c : ctors) {
            if (c.getGenericParameterTypes().length == 0) {
                ctor = c;
                break;
            }
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException |
                 IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Insereaza un obiect in baza de date.
     *
     * @param t obiectul de inserat.
     * @return ID-ul obiectului inserat.
     */
    public int insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (!type.equals(Comanda.class) && !type.equals(Factura.class) &&
                        (field.getName().equals("clientId") || field.getName().equals("produsId") || field.getName().equals("comandaId")))
                    continue;
                if (field.getName().equals("comandaId") && type.equals(Comanda.class))
                    continue;
                if (field.getName().equals("facturaId"))
                    continue;
                Object value = field.get(t);
                statement.setObject(index, value);
                index++;
            }
            statement.executeUpdate();
            int insertedId = -1;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
            return insertedId;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    /**
     * Creeaza o interogare UPDATE.
     *
     * @return interogarea UPDATE ca sir de caractere.
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        sb.append(type.getDeclaredFields()[1].getName());
        sb.append("=?");
        for (int i = 2; i < type.getDeclaredFields().length; i++) {
            sb.append(",");
            sb.append(type.getDeclaredFields()[i].getName());
            sb.append("=?");
        }
        sb.append(" WHERE ");
        sb.append(type.getDeclaredFields()[0].getName());
        sb.append("=?");
        return sb.toString();
    }

    /**
     * Actualizeaza un obiect in baza de date.
     *
     * @param t obiectul de actualizat.
     */
    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("clientId") || field.getName().equals("produsId") || field.getName().equals("comandaId")) {
                    statement.setObject(type.getDeclaredFields().length, field.get(t));
                    continue;
                }
                Object value = field.get(t);
                statement.setObject(index, value);
                index++;
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Creeaza o interogare DELETE.
     *
     * @return interogarea DELETE ca sir de caractere.
     */
    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(type.getDeclaredFields()[0].getName());
        sb.append("=?");
        return sb.toString();
    }

    /**
     * Sterge un obiect din baza de date dupa ID.
     *
     * @param id ID-ul obiectului de sters.
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, id);
            statement.executeUpdate();
            System.out.println("Deleted");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}