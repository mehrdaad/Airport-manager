package cz.fi.muni.pa165.dao.impl;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of the {@link UserDao} interface.
 *
 * @author Robert Duriancik
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }

        if (user.getName() == null ||
                user.getSurname() == null ||
                user.getEmail() == null ||
                user.getRegistered() == null) {
            throw new IllegalArgumentException("Mandatory user's fields are not all set");
        }

        em.persist(user);
    }

    @Override
    public void removeUser(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }

        User userFromDb = getUser(user.getId());

        if (userFromDb != null) {
            em.remove(userFromDb);
        } else {
            throw new IllegalArgumentException("User: " + user + " not in the persistence storage.");
        }
    }

    @Override
    public void updateUser(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }

        User userFromDb = getUser(user.getId());

        if (userFromDb != null) {
            em.merge(user);
        } else {
            throw new IllegalArgumentException("User: " + user + " not in the persistence storage.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(Long id) {
        if (id == null) {
            throw new NullPointerException("Id is null");
        }
        return em.find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            throw new NullPointerException("Email is null");
        }

        try {
            return em.createQuery("select u from User u where email=:email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
