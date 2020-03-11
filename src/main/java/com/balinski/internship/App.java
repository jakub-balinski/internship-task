package com.balinski.internship;

import com.balinski.internship.model.Post;
import com.balinski.internship.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws IOException {
        final URL postsUrl = new URL("https://jsonplaceholder.typicode.com/posts");
        final URL usersUrl = new URL("https://jsonplaceholder.typicode.com/users");

        final ObjectMapper mapper = new ObjectMapper();
        final List<Post> posts = Arrays.asList(mapper.readValue(postsUrl, Post[].class));
        final List<User> users = Arrays.asList(mapper.readValue(usersUrl, User[].class));

        printUsersWithPostsCount(users, posts);
        printDuplicatedPostTitles(posts);
        printNearestNeighbors(users);
    }

    public static void printUsersWithPostsCount(List<User> users, List<Post> posts) {
        System.out.println("\n------------------------------------------------------------\n");
        System.out.println("Użytkownicy i ich liczba postów:");

        users.forEach(user ->
                System.out.println(
                        String.format(
                                "%s napisał(a) %d postów",
                                user.getUsername(),
                                user.getPostsCount(posts)
                        )
                )
        );
    }

    public static void printDuplicatedPostTitles(List<Post> posts) {
        System.out.println("\n------------------------------------------------------------\n");
        System.out.println("Zduplikowane tytuły postów:");

        Map<String, Long> titleToFreq = posts.stream().
                map(Post::getTitle).
                collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        titleToFreq.forEach((title, frequency) -> {
            if (frequency > 1) System.out.println(title);
        });
    }

    public static void printNearestNeighbors(List<User> users) {
        System.out.println("\n------------------------------------------------------------\n");

        if (users.size() > 1) {
            System.out.println("Najbliżsi sąsiedzi użytkowników:");

            users.forEach(user ->
                    System.out.println(
                            String.format("Najbliżej %s znajduje się %s",
                                    user.getUsername(),
                                    user.findNearestNeighbor(users).get().getUsername() //<-- no check for presence
                            )                                                           //because there are at least
                    )                                                                   //two users at this point - each
            );                                                                          //having at least one neighbor
        } else {
            System.out.println("Wymagana jest obecność przynajmniej dwóch użytkowników, by móc określić sąsiedztwo.");
        }
    }

}
