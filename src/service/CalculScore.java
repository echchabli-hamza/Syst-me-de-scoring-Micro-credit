package service;

import entity.Person;
import entity.Employe;
import entity.Professionnel;
import util.RuleChain;
import util.GetConst;

import java.time.LocalDate;
import java.time.Period;

public class CalculScore {

    private RuleChain rc;

    public CalculScore() {
        GetConst constants = new GetConst();
        this.rc = constants.chain;
    }

    public int employeScore(Employe p){


        int score = 0 ;

        score+=rc.evaluate("EMPLOI" , "ANCIENNETE" , p.getAnciennete());

        score+=rc.evaluate("EMPLOI" , "TYPE" , p.getTypeContrat()+"_"+ p.getSecteur());


        score+=rc.evaluate("FAMILLE" , "ENFANTS" , p.getNombreEnfants());

        score+=rc.evaluate("FAMILLE" , "SITUATION" , p.getSituationFamiliale());

        score+=rc.evaluate("PATRIMOINE" , "INVESTISSEMENTS" , p.getInvestissement());





        score+=rc.evaluate("RELATION_CLIENT_EXISTANT" , "ANCIENNETE" , "ANCIENNETE_>3");

      score+=rc.evaluate(p.getSalaire());

      score+=rc.evaluate(p.getAge());


        System.out.print(score);

        return score;



    }


    public int proScore(Professionnel p){


        int score = 0 ;


        score+=rc.evaluate("EMPLOI" , "TYPE" , p.getSecteurActivite());


        score+=rc.evaluate("FAMILLE" , "ENFANTS" , p.getNombreEnfants());

        score+=rc.evaluate("FAMILLE" , "SITUATION" , p.getSituationFamiliale());

        score+=rc.evaluate("PATRIMOINE" , "INVESTISSEMENTS" , p.getInvestissement());





        score+=rc.evaluate("RELATION_CLIENT_EXISTANT" , "ANCIENNETE" , "ANCIENNETE_>3");

        score+=rc.evaluate(p.getSalaire());

        score+=rc.evaluate(p.getAge());


        System.out.print(score);

        return score;



    }





}
