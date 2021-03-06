package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private ArrayList<String> wordList;
    private HashSet<String> wordSet=new HashSet<String>();
    private HashMap<String,ArrayList<String>> letterToWord=new HashMap<String,ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>> sizeOfWord= new HashMap<Integer,ArrayList<String>>();




    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word.toLowerCase());
            letterToWord=addToLetterToWord(letterToWord,word.toLowerCase());
            sizeOfWord=addToSzeOfWord(sizeOfWord,word.toLowerCase());
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(!wordSet.contains(word.toLowerCase()))
        {return false;}

        return !(word.toLowerCase().contains(base.toLowerCase()));
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {

        ArrayList<String> result = new ArrayList<String>();
        String temp;

        for (int i = 97; i <= 122; i++) {
            temp = word.toLowerCase();
            temp += String.valueOf((char) i);
            temp = arrangeDictionaryOder(temp);
            if (letterToWord.containsKey(temp)) {
                result.addAll(letterToWord.get(temp));
            }

        }

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).contains(word)) {
                result.remove(i);
            }
        }


        return result;

    }

    public String pickGoodStarterWord() {

        String goodWord;
        while(true)
        {   goodWord=randomWord();
            if(getAnagramsWithOneMoreLetter(goodWord).size()>=MIN_NUM_ANAGRAMS);
            {return goodWord;}
        }
    }

    private String arrangeDictionaryOder(String word)
    {
        char[] ch=word.toLowerCase().toCharArray();
        Arrays.sort(ch);
        return String.valueOf(ch);
    }

    private HashMap<String,ArrayList<String>> addToLetterToWord(HashMap<String,ArrayList<String>> hm,String word)
    {

        String key = arrangeDictionaryOder(word.toLowerCase());
        if(hm.containsKey(key)==false) {
            wordList = new ArrayList<String>();
            wordList.add(word.toLowerCase());
            hm.put(key, wordList);
            return hm;
        }
        wordList=hm.get(key);
        wordList.add(word.toLowerCase());
        hm.put(key, wordList);
        return hm;
    }
    private HashMap<Integer,ArrayList<String>> addToSzeOfWord(HashMap<Integer,ArrayList<String>> hm,String word)
    {

        int key=word.length();
        if(hm.containsKey(key)==false) {
            wordList = new ArrayList<String>();
            wordList.add(word.toLowerCase());
            hm.put(key, wordList);
            return hm;
        }
        wordList=hm.get(key);
        wordList.add(word.toLowerCase());
        hm.put(key, wordList);
        return hm;
    }

    private String randomWord()
    {
        ArrayList<String> arr = sizeOfWord.get(DEFAULT_WORD_LENGTH);
        int rand = new Random().nextInt(arr.size());
        return arr.get(rand);

    }

}
