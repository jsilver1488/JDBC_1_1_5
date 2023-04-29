package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tran = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tran = session.beginTransaction();
            session.createSQLQuery(
                            "create table if not exists users ("
                                    + "id bigint primary key auto_increment,"
                                    + "name varchar(30),"
                                    + "lastname varchar(30),"
                                    + "age tinyint)")
                    .executeUpdate();
            tran.commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tran = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tran = session.beginTransaction();
            session.createSQLQuery("drop table if exists users")
                    .executeUpdate();
            tran.commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tran = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tran = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            tran.commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tran = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tran = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            tran.commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User");
            userList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;

    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
