package com.lambda.countries.controllers;


import com.lambda.countries.models.Country;
import com.lambda.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    private List<Country> findCountry(List<Country> countryList, CheckCountry tester){
        List<Country> rtnCountryList = new ArrayList<>();
        for(Country c : countryList){
            if(tester.test(c)){
                rtnCountryList.add(c);
            }
        }
        return rtnCountryList;
    }

    //http://localhost:2019/names/all
    @GetMapping(value="/names/all", produces = "application/json")
    public ResponseEntity<?> ListAllCountry(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/{letter}

    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findByLetter(@PathVariable char letter){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnfilter = findCountry(myList,(c) -> c.getName().charAt(0) == letter );
        return new ResponseEntity<>(rtnfilter, HttpStatus.OK);
    }

    //http://localhost:2019/population/total
    @GetMapping(value ="/population/total", produces = "application/json")
    public ResponseEntity<?> findTotal(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        long total = 0;
        for (Country c : myList){
            total +=  + c.getPopulation();
        }
            System.out.println("Total Population: "+total);
         return new ResponseEntity<>(total, HttpStatus.OK);
    }

    //http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> findMaximumPopulation(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) ->(int) (c2.getPopulation() - c1.getPopulation()));

        Country returnCountry = myList.get(0);
        return new ResponseEntity<>(returnCountry, HttpStatus.OK);
    }

    //http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> findMinimumPopulation(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) ->(int) (c1.getPopulation() - c2.getPopulation()));

        Country returnCountry = myList.get(0);
        return new ResponseEntity<>(returnCountry, HttpStatus.OK);
    }


    //http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = "application/json")
    public ResponseEntity<?> findMedianPopulation(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) ->(int) (c1.getPopulation() - c2.getPopulation()));

        Country returnCountry = myList.get((myList.size() / 2) +1 );
        return new ResponseEntity<>(returnCountry, HttpStatus.OK);
    }

}
