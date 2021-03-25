package com.example.quwiapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectDetailModel {
    @SerializedName("project")
    @Expose
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public class Project {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("logo_url")
        @Expose
        private String logoUrl;
        @SerializedName("position")
        @Expose
        private int position;
        @SerializedName("is_active")
        @Expose
        private int isActive;
        @SerializedName("is_owner_watcher")
        @Expose
        private boolean isOwnerWatcher;
        @SerializedName("dta_user_since")
        @Expose
        private String dtaUserSince;
        @SerializedName("users")
        @Expose
        private List<User> users = null;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public boolean isIsOwnerWatcher() {
            return isOwnerWatcher;
        }

        public void setIsOwnerWatcher(boolean isOwnerWatcher) {
            this.isOwnerWatcher = isOwnerWatcher;
        }

        public String getDtaUserSince() {
            return dtaUserSince;
        }

        public void setDtaUserSince(String dtaUserSince) {
            this.dtaUserSince = dtaUserSince;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

    }


    public class User {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("avatar_url")
        @Expose
        private Object avatarUrl;
        @SerializedName("is_online")
        @Expose
        private boolean isOnline;
        @SerializedName("dta_activity")
        @Expose
        private String dtaActivity;
        @SerializedName("dta_since")
        @Expose
        private String dtaSince;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public boolean isIsOnline() {
            return isOnline;
        }

        public void setIsOnline(boolean isOnline) {
            this.isOnline = isOnline;
        }

        public String getDtaActivity() {
            return dtaActivity;
        }

        public void setDtaActivity(String dtaActivity) {
            this.dtaActivity = dtaActivity;
        }

        public String getDtaSince() {
            return dtaSince;
        }

        public void setDtaSince(String dtaSince) {
            this.dtaSince = dtaSince;
        }

    }
}
