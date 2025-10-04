package service;

import entity.Incident;
import entity.Person;
import repos.UserDao;

import java.sql.SQLException;
import java.util.List;
import util.Session;

public class UserService {

    private UserDao userDao = new UserDao();



    public void createClient(Person client) {
        userDao.createClient(client);
    }



    public Person getClientById(int id) {
        return userDao.getClient(id);
    }

    public void deleteClient(int id) {
        userDao.deleteClient(id);
    }

    public List<Person> listAllClients() {
        return userDao.listClients();
    }


    public void updateUserScore(Incident incident ) {

            List<Integer> userId = userDao.getUserIdByIncident(incident.getEcheanceId());


            if (userId == null) {


                System.err.println("No user found for incident id " + incident.getEcheanceId());
                return;
            }




        int uId = userId.get(0);

        double scoreVal = userId.get(1)  + incident.getScore();





        userDao.updateScore( uId , scoreVal);



          System.out.println("User " + userId + " score updated by " + incident.getScore());


    }

}
