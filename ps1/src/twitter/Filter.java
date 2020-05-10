/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.Scanner;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        ArrayList<Tweet> userTweets = new ArrayList<Tweet>();
        
        if(username.matches("\\w+")) { //Checks whether username is valid
            for(Tweet tweet : tweets) {
                if(tweet.getAuthor().equals(username)) {
                    userTweets.add(tweet);
                }
            }
        }
        else {
            System.out.println("Invalid Twitter username");
        }
        
        return userTweets;
        
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        ArrayList<Tweet> tweetsInTimespan = new ArrayList<Tweet>();
        
        for(Tweet tweet : tweets) {
            if(tweet.getTimestamp().isAfter(timespan.getStart()) 
               && tweet.getTimestamp().isBefore(timespan.getEnd())){ //if tweet is within the timespan
                tweetsInTimespan.add(tweet);
            }
        }
        
        return tweetsInTimespan;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        ArrayList<Tweet> tweetsContaining = new ArrayList<Tweet>();
        
        for(Tweet tweet : tweets) {
            boolean wordFound = false;
            
            for(String word : words) {
                Scanner sc = new Scanner(tweet.getText());
                
                while(!wordFound && sc.hasNext()) {
                    wordFound = sc.hasNext(word);
                    
                    if(wordFound) {
                        tweetsContaining.add(tweet);
                    }
                    
                    sc.next();
                }
            } 
        }
        
        return tweetsContaining;
    }

}
