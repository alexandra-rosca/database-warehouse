package dao;

import connection.ConnectionFactory;
import model.Client;
import model.OrderTable;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <T>
 *
 * Clasa in care folosim Reflection Techniques si scriem query-uri generice pentru opertii CRUD
 * Fiecare metoda este folosita pentru a se adapta pe orice tabel din baza de date cu numar diferit de atribute
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;
    public String[][] data;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @param field
     * @return
     * Generic Select Query
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        System.out.println(sb);
        return sb.toString();
    }

    /**
     * @return
     * Generic Insert Query
     */
    private String insertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO ");
        sb.append(type.getSimpleName());
        sb.append(" VALUES (");
        for (Field f : type.getDeclaredFields()) {
            sb.append("?,");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * @param field
     * @return
     * Generic Delete Query dupa ID
     */
    private String deleteQuery(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + field + "=?");
        return stringBuilder.toString();
    }

    /**
     * @param field
     * @return
     * Generic Update Query
     */
    private String updateQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field f : type.getDeclaredFields()) {
            sb.append(f.getName() + "=? , ");
        }
        sb.setLength(sb.length() - 2); // stergem ultima virgula
        sb.append("WHERE " + field + "=?");
        return sb.toString();
    }

    /**
     * @return
     * Query folosit pentru a extrage toate date dintr-un tabel din DB
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * @return
     * Se extrag toate datele din tabel ajutandu-ne de si de metoda createSelectAllQuery()
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSet r = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            r = resultSet;
            data = new String[50][40];
            int j = 0;
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    Object o = resultSet.getObject(i);;
                    if(o!= null) {
                        data[j][i - 1] = o.toString();
                    }
                    else
                        data[j][i-1]="0";
                }
                j++;
            }
            data[j+1]=null;
            return createObjects(r);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO : findall " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * @param id
     * @return
     * Returneaza obiectul gasit in functie de id in baza de date
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        System.out.println(query);
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

    public T findProduct(Object field, String fieldName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        OrderTable orderTable = new OrderTable(0, 0, null, null, 0);
        String query = createSelectQuery(fieldName);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, field);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
           // return orderTable;
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
     * @param t
     * @return
     * Inseram un nou obiect in functie de obiect in baza de date
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = insertQuery();

        try {
            int insertedId = 1;
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            for(Field f : type.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object s = method.invoke(t);
                statement.setObject(insertedId, s);
                insertedId++;
            }

            resultSet = statement.executeUpdate();
            return t;

        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO: insert " + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }

        return null;
    }

    /**
     * @param t
     * @return
     * Editam un obiect in functie de obiect in baza de date
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = updateQuery("id");

        try {
            int index = 1;
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            for(Field f : type.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object s = method.invoke(t);
                statement.setObject(index, s);
                index++;
            }
            for (Field f : type.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object s = method.invoke(t);
                statement.setObject(index, s);
                // System.out.println(f.getName());
                break;
            }
            resultSet = statement.executeUpdate();
            return t;

        } catch (SQLException | IntrospectionException throwables) {
            throwables.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * @param id
     * @return
     * Stergem o intregistrare in baza de date in functie de id
     */
    public int delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = deleteQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return 0;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

}
