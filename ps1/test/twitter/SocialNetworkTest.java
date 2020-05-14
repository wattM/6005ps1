/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T01:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T01:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about @rivest and @buttdoctor so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa @buttdoctor rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "buttdoctor", "@alyssa @bbitdiddle im a butt doctor email me at buttdoctor@gmail.com", d3);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "is it reasonable to talk about @rivest and @bbitdiddle so much?", d4);
    private static final Tweet tweet5 = new Tweet(5, "buttdoctorfan", "man, @buttdoctor really fixed me up good #butttherapy", d5);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsOneAuthorMultipleMentions() {
        ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
        tweetList.add(tweet2);
        Set<String> followerSet = Extract.getMentionedUsers(tweetList);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweetList);
        
        System.out.println("tweet 2 followsGraph: " + followsGraph);
        
        assertTrue("expected single username", followsGraph.containsKey(tweetList.get(0).getAuthor()));
        assertEquals("expected map size 1", 1, followsGraph.size());
        assertTrue("expected follower set", followsGraph.get(tweetList.get(0).getAuthor()).equals(followerSet));
    }

    @Test
    public void testGuessFollowsOneAuthorMultipleTweets() {
        ArrayList<Tweet> testTweetList = new ArrayList<>(List.of(tweet1, tweet4));
        Set<String> testFollowerSet = Extract.getMentionedUsers(testTweetList);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(testTweetList);
        
        System.out.println("tweet 1, tweet 4 followsGraph: " + followsGraph);
        
        assertTrue("expected single username", followsGraph.containsKey(testTweetList.get(0).getAuthor()));
        assertEquals("expected map size 1", 1, followsGraph.size());
        assertTrue("expected follower set", followsGraph.get(tweet1.getAuthor()).equals(testFollowerSet));
    }
    
    @Test
    public void testInfluencersOneTweet() {
        ArrayList<Tweet> testTweetList = new ArrayList<>(List.of(tweet1));
        Set<String> testFollowerSet = Extract.getMentionedUsers(testTweetList);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(testTweetList);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println("tweet 1 influencer list: " + influencers);
        
        assertEquals("expected list size 3", 3, influencers.size());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<String, Set<String>>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
