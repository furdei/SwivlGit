package com.swivl.furdey.swivlgit.content.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User model used for parsing and storing user data
 */
public class User implements Serializable {

    public static final int DEFAULT_SIZE = -1;
    public static final int SMALL_SIZE = 100;
    public static final int LARGE_SIZE = 400;

    private static final String AVATARS = "https://avatars.githubusercontent.com";
    private static final String AVATARS2 = "https://avatars2.githubusercontent.com";

    private Long localId;

    @SerializedName("id")
    private Long gitId;

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("html_url")
    private String htmlUrl;

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getGitId() {
        return gitId;
    }

    public void setGitId(Long gitId) {
        this.gitId = gitId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get an URL of a user avatar with specified size
     */
    public String getAvatarUrl(int size) {
        return size <= DEFAULT_SIZE ? avatarUrl : avatarUrl.concat("&s=").concat(Integer.toString(size));
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl.replace(AVATARS, AVATARS2);
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
