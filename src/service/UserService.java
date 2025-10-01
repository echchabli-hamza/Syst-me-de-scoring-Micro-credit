package service;

import entity.Person;
import repos.UserDao;
import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();

    public void createClient(Person client) {
        userDao.createClient(client);
    }

//    public void updateClient(Person client) {
//        userDao.updateClient(client);
//    }

    public Person getClientById(int id) {
        return userDao.getClient(id);
    }

    public void deleteClient(int id) {
        userDao.deleteClient(id);
    }

    public List<Person> listAllClients() {
        return userDao.listClients();
    }



}
