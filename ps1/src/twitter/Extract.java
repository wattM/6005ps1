/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.util.regex.*;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant[] tstamps = new Instant[tweets.size()];
        
        for(int i = 0; i < tweets.size(); i++) {
            tstamps[i] = tweets.get(i).getTimestamp();
        }
        
        Instant start = tstamps[0];
        Instant end = tstamps[0];
        
        for(int i = 0; i < tstamps.length; i++) {
            if(tstamps[i].isBefore(start)) {
                start = tstamps[i];
            }
            
            else if(tstamps[i].isAfter(end)) {
                end = tstamps[i];
            }
        }
        
        Timespan tspan = new Timespan(start, end);
        return tspan;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        HashSet<String> mentionedUsers = new HashSet<String>();
        
        //Split text into words and store them in an array to find matches
        for(Tweet tweet : tweets) {
            String[] words = tweet.getText().split("\\s+");
            
            for(String word : words) {
                if(word.matches("(@\\w+)")){
                    word = word.substring(1);
                    mentionedUsers.add(word);
                }   
            }
        
        }
        
        return mentionedUsers;
    }

}
