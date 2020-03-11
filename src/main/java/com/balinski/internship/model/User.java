package com.balinski.internship.model;

import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "username",
        "email",
        "address",
        "phone",
        "website",
        "company"
})
public class User {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("website")
    private String website;
    @JsonProperty("company")
    private Company company;

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(@NotNull Address address) {
        this.address = address;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(@NotNull String phone) {
        this.phone = phone;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(@NotNull String website) {
        this.website = website;
    }

    @JsonProperty("company")
    public Company getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(@NotNull Company company) {
        this.company = company;
    }

    /**
     * Returns the number of posts assigned to this {@link User}
     *
     * @param  all  {@link List} of {@link Post}s to consider
     * @return      number of posts assigned to this {@link User}
     */
    public long getPostsCount(@NotNull List<Post> posts) {
        return posts.stream().
                filter(Objects::nonNull).
                filter(post -> post.getUserId() == this.id).
                count();
    }

    /**
     * Returns the nearest (non-equal to this) neighbor of this {@link User} in terms of {@link Geo} distance.
     *
     * @param  all  {@link List} of {@link User}s to consider
     * @return      {@link Optional} containing the nearest neighbor or empty if found none
     */
    public Optional<User> getNearestNeighbor(@NotNull List<User> all) {
        final Geo center = this.address.getGeo();

        return all.stream().
                filter(Objects::nonNull).
                filter(user -> user != this).
                reduce((u1, u2) -> center.getDistanceTo(u1.getAddress().getGeo()) <
                        center.getDistanceTo(u2.getAddress().getGeo()) ?
                        u1 : u2);
    }

}