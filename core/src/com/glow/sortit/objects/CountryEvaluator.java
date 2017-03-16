package com.glow.sortit.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Andreas on 15.03.2017.
 */

public class CountryEvaluator {
    private Map<Nationality, Integer> letters;
    private Map<Nationality, Double> letterSpawnRates;

    public CountryEvaluator () {
        letters = new HashMap<Nationality, Integer>();
        letterSpawnRates = new HashMap<Nationality, Double>();
        letters.put(Nationality.norway, 0);
        letters.put(Nationality.denmark, 0);
        letters.put(Nationality.sweden, 0);
        letters.put(Nationality.finland, 0);
        letters.put(Nationality.deutschland, 0);
        letterSpawnRates.put(Nationality.norway, 0.0);
        letterSpawnRates.put(Nationality.denmark, 0.0);
        letterSpawnRates.put(Nationality.sweden, 0.0);
        letterSpawnRates.put(Nationality.finland, 0.0);
        letterSpawnRates.put(Nationality.deutschland, 0.0);
    }

    public void addLetter (Nationality nationality) {
        int amount = letters.get(nationality);
        letters.put(nationality, amount+1);
    }

    public Nationality giveBagNationality () {
        double standardValue = 0.6 / 8;
        Random ran = new Random();
        for (Nationality n : letters.keySet()) {
            double factor = standardValue * letters.get(n);
            switch (n) {
                case norway:
                    letterSpawnRates.put(Nationality.norway, factor * ran.nextInt(10));
                    break;
                case denmark:
                    letterSpawnRates.put(Nationality.denmark, factor * ran.nextInt(10));
                    break;
                case sweden:
                    letterSpawnRates.put(Nationality.sweden, factor * ran.nextInt(10));
                    break;
                case finland:
                    letterSpawnRates.put(Nationality.finland, factor * ran.nextInt(10));
                    break;
                case deutschland:
                    letterSpawnRates.put(Nationality.deutschland, factor * ran.nextInt(10));
                    break;
            }
        }
        Nationality nationality = Nationality.norway;
        double highestValue = 0;
        for (Map.Entry<Nationality, Double> entry : letterSpawnRates.entrySet()) {
            if (entry.getValue() > highestValue) {
                highestValue = entry.getValue();
                nationality = entry.getKey();
            }
        }
        return nationality;
    }

    public int countryCount () {
        return Nationality.values().length;
    }

    public Nationality[] countries () {
        return Nationality.values();
    }

    public int letterAmount () {
        int letterAmount = 0;
        for (Integer amount : letters.values()) {
            letterAmount += amount;
        }
        return letterAmount;
    }

    enum Nationality {
        denmark("denmark", 0),
        finland("finland", 1),
        norway("norway", 2),
        sweden("sweden", 3),
        deutschland("deutschland", 4);

        private final String name;
        private final int index;

        Nationality(String s, int i) {
            name = s;
            index = i;
        }

        public int getIndex () {
            return index;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }
}
