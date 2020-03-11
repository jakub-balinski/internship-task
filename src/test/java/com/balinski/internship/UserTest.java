package com.balinski.internship;

import com.balinski.internship.model.Address;
import com.balinski.internship.model.Geo;
import com.balinski.internship.model.Post;
import com.balinski.internship.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {
    List<User> stubUsers;
    List<Post> stubPosts;

    @Before
    public void setup() {
        stubUsers = new ArrayList<>(5) {{
            IntStream.range(0, 5).forEach(i ->
                    add(new User() {{
                        setId(i);
                        setAddress(new Address() {{
                            setGeo(new Geo() {{
                                setLat("0");
                                setLng("0");
                            }});
                        }});
                    }}));
        }};

        stubPosts = new ArrayList<>(10) {{
            IntStream.range(0, 10).forEach(i ->
                    add(new Post() {{
                        setId(i);
                        setUserId(i % 5);
                    }})
            );
        }};
    }

    @Test
    public void postCountReturnsZeroForEmptyList() {
        List<Post> empty = Collections.emptyList();

        assertEquals(stubUsers.get(0).getPostsCount(empty), 0);
    }

    @Test
    public void postCountReturnsCorrectValues() {
        for (var user : stubUsers)
            assertEquals(user.getPostsCount(stubPosts), 2);

        stubPosts.get(0).setUserId(1);
        assertEquals(stubUsers.get(0).getPostsCount(stubPosts), 1);
        assertEquals(stubUsers.get(1).getPostsCount(stubPosts), 3);
    }

    @Test
    public void getNearestNeighborReturnsEmptyForEmptyList() {
        List<User> empty = Collections.emptyList();

        assertTrue(stubUsers.get(0).getNearestNeighbor(empty).isEmpty());
    }

    @Test
    public void getNearestNeighborReturnsEmptyForListContainingOnlyCallerObject() {
        List<User> oneUser = List.of(stubUsers.get(0));

        assertTrue(stubUsers.get(0).getNearestNeighbor(oneUser).isEmpty());
    }

    @Test
    public void getNearestNeighborReturnsEachOtherForListContainingUsersWithSameGeo() {
        List<User> twoUsers = List.of(stubUsers.get(0), stubUsers.get(1));

        assertEquals(stubUsers.get(0).getNearestNeighbor(twoUsers).get(), stubUsers.get(1));
        assertEquals(stubUsers.get(1).getNearestNeighbor(twoUsers).get(), stubUsers.get(0));
    }

    @Test
    public void getNearestNeighborReturnsEachOtherForListContainingTwoUsers() {
        stubUsers.get(0).getAddress().getGeo().setLng("25");
        stubUsers.get(0).getAddress().getGeo().setLat("50");
        stubUsers.get(1).getAddress().getGeo().setLng("-50");
        stubUsers.get(1).getAddress().getGeo().setLat("-25");

        List<User> twoUsers = List.of(stubUsers.get(0), stubUsers.get(1));

        assertEquals(stubUsers.get(0).getNearestNeighbor(twoUsers).get(), stubUsers.get(1));
        assertEquals(stubUsers.get(1).getNearestNeighbor(twoUsers).get(), stubUsers.get(0));
    }

    @Test
    public void getNearestNeighborReturnsSameUserForEqualDistancesFromCenter() {
        final User center = stubUsers.get(0); //Geo == ("0", "0")
        stubUsers.get(1).getAddress().getGeo().setLng("-25");
        stubUsers.get(1).getAddress().getGeo().setLat("-50");
        stubUsers.get(2).getAddress().getGeo().setLng("50");
        stubUsers.get(2).getAddress().getGeo().setLat("25");

        List<User> threeUsers = List.of(center, stubUsers.get(1), stubUsers.get(2));

        assertEquals(stubUsers.get(1).getNearestNeighbor(threeUsers).get(), center);
        assertEquals(stubUsers.get(2).getNearestNeighbor(threeUsers).get(), center);
    }

    @Test
    public void getNearestNeighborReturnsCorrectUsersForListOfMany() {
        final var coord = IntStream.of(-80, -32, 0, 32, 48).iterator();

        stubUsers.forEach(user -> {
            var c = coord.next();
            user.getAddress().getGeo().setLng(c.toString());
            user.getAddress().getGeo().setLat(c.toString());
        });

        IntStream.rangeClosed(0, 3).forEach(i ->
                assertEquals(
                        stubUsers.get(i).getNearestNeighbor(stubUsers).get(),
                        stubUsers.get(i + 1)
                )
        );

        assertEquals(
                stubUsers.get(4).getNearestNeighbor(stubUsers).get(),
                stubUsers.get(3)
        );
    }

}
